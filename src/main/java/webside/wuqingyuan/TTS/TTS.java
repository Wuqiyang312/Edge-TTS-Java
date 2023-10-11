package webside.wuqingyuan.TTS;

import okhttp3.*;
import okio.ByteString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class TTS implements Runnable{
    final String REQUEST_ID_PREFIX = "X-RequestId:";
    final String LINE_BREAK = "\r\n";
    /**
     * 从文本中提取请求ID。
     * @param text 包含请求ID的文本
     * @return 提取的请求ID，如果未找到则返回null
     */
    private String extractRequestId(String text) {
        // 查找请求ID前缀的索引
        int prefixIndex = text.indexOf(REQUEST_ID_PREFIX);
        if (prefixIndex != -1) {
            // 计算请求ID的起始索引
            int startIndex = prefixIndex + REQUEST_ID_PREFIX.length();
            // 查找请求ID后换行符的索引
            int endIndex = text.indexOf(LINE_BREAK, startIndex);
            if (endIndex != -1) {
                // 提取请求ID子字符串
                return text.substring(startIndex, endIndex);
            }
        }
        // 未找到请求ID
        return null;
    }
    //转换为音频格式Web套接字字符串
    private static String ConvertToAudioFormatWebSocketString(String outputformat) {
        return "Content-Type:application/json; charset=utf-8\r\n" +
                "Path:speech.config\r\n" +
                "\r\n{\"context\":{\"synthesis\":{\"audio\":{\"metadataoptions\":{\"sentenceBoundaryEnabled\":\"false\"" +
                ",\"wordBoundaryEnabled\":\"false\"},\"outputFormat\":\"" + outputformat + "\"}}}}";
    }
    //转换为ssml文本
    static String ConvertToSsmlText(String lang, String voice, String text, String rate)
    {
        return "<speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xmlns:mstts='https://www.w3.org/2001/mstts' xml:lang='"
                +lang+"'><voice name='"+voice+"'><prosody pitch='+0Hz' rate='"+rate+"'>"+ text+"</prosody></voice></speak>";
    }
    //转换为ssml Web套接字字符串
    static String ConvertToSsmlWebSocketString(String requestId, String lang, String voice, String msg, String rate)
    {
        return "X-RequestId:"+requestId+"\r\nContent-Type:application/ssml+xml\r\nPath:ssml\r\n\r\n"
                +ConvertToSsmlText(lang, voice, msg, rate);
    }
    //生成一个唯一的请求标识符(Request ID)
    static String generateRequestId() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    private static final OkHttpClient client = new OkHttpClient();
    Headers headers = new Headers.Builder()
            .add("Authority", "speech.platform.bing.com")
            .add("Sec-CH-UA", "\" Not;A Brand\";v=\"99\", \"Microsoft Edge\";v=\"91\", \"Chromium\";v=\"91\"")
            .add("Sec-CH-UA-Mobile", "?0")
            .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.864.41")
            .add("Accept", "*/*")
            .add("Sec-Fetch-Site", "none")
            .add("Sec-Fetch-Mode", "cors")
            .add("Sec-Fetch-Dest", "empty")
            .add("Accept-Encoding", "gzip, deflate, br")
            .add("Accept-Language", "en-US,en;q=0.9")
            .build();
    public void TTSRun(String language, String voice, String msg, String rate) throws IOException {
        String url = "wss://speech.platform.bing.com/consumer/speech/synthesize/readaloud/edge/v1?trustedclienttoken=6A5AA1D4EAFF4E9FB37E23D68491D6F4";
        String audioOutputFormat = "webm-24khz-16bit-mono-opus";
        String sendRequestId = generateRequestId();

        String audioConfig = ConvertToAudioFormatWebSocketString(audioOutputFormat);
        String ssml = ConvertToSsmlWebSocketString(sendRequestId, language, voice, msg, rate);

        WebSocket webSocket = client.newWebSocket(new Request.Builder().url(url).headers(headers).build(), new WebSocketListener() {
            final Map<String, List<byte[]>> dataBuffers = new HashMap<>();
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                System.out.println("WebSocket: Open");
                webSocket.send(audioConfig);
                webSocket.send(ssml);
            }
            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                byte[] data = bytes.toByteArray();
                String text = new String(data);
                String requestId = extractRequestId(text);

                if (requestId != null) {
                    System.out.println(data[0] + " " + data[1] + " " + data[2]);

                    if (!dataBuffers.containsKey(requestId)) {
                        dataBuffers.put(requestId, new ArrayList<>());
                    }

                    if (data[0] == 0x00 && data[1] == 0x67 && data[2] == 0x58) {
                        // Last (empty) audio fragment. 空音频片段，代表音频发送结束
                        System.out.println("空音频片段-发送结束");
                        // WebSocket关闭
                        webSocket.close(1000, null);
                        List<byte[]> audioData = dataBuffers.get(sendRequestId);
                        if (audioData != null) {
                            System.out.println("接收到的音频字节长度：" + audioData.size());
                            // 接收到的音频字节
                            File f = new File("audio.webm");
                            try (FileOutputStream fos = new FileOutputStream(f)) {
                                for (byte[] audioByte : audioData) {
                                    fos.write(audioByte);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        int index = 142;
                        byte[] audioData = Arrays.copyOfRange(data, index, data.length);
                        dataBuffers.get(requestId).add(audioData);
                    }
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                // WebSocket即将关闭
                System.out.println("code:" + code);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                if (response != null) {
                    System.out.println("error message: " + response.message());
                }
                super.onFailure(webSocket, t, response);
            }
        });
    }
    String language;
    String voice;
    String msg;
    String rate;
    public TTS(String language, String voice, String msg, String rate) {
        this.language = language;
        this.voice = voice;
        this.msg = msg;
        this.rate = rate;
    }

    @Override
    public void run() {
        try {
            TTSRun(language, voice, msg, rate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
