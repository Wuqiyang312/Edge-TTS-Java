package webside.wuqingyuan.TTS;


import java.io.IOException;

public class WebMAudioPlayer {
    public WebMAudioPlayer() {
        String webmFilePath = Constant.ttsPath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 创建ProcessBuilder对象
                ProcessBuilder processBuilder = new ProcessBuilder(".\\ffplay","-autoexit","-nodisp", webmFilePath);

                // 启动进程
                Process process = null;
                try {
                    process = processBuilder.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 等待进程结束
                try {
                    int exitCode = process.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}
