package webside.wuqingyuan.TTS;

import java.io.File;
import java.io.IOException;

public class SaveMp3 {
    public SaveMp3(){
        String mp3FilePath = Constant.desktopPath +  File.separator + Constant.getTime() + "-audio.mp3";
        String webmFilePath = Constant.ttsPath;
        System.out.println(".\\ffmpeg -i "+webmFilePath+" -vn -acodec libmp3lame "+mp3FilePath);
        try {
            // 创建ProcessBuilder对象
            ProcessBuilder processBuilder = new ProcessBuilder(".\\ffmpeg", "-i", webmFilePath, "-vn", "-acodec", "libmp3lame", mp3FilePath);

            // 启动进程
            Process process = processBuilder.start();

            // 等待进程结束
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("WebM转换为MP3成功！");
            } else {
                System.out.println("WebM转换为MP3失败！");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
