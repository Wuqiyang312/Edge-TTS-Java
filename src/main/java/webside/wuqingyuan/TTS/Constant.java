package webside.wuqingyuan.TTS;

import java.io.File;
import java.util.Calendar;

public class Constant {
    public static String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        // 获取年份、月份、日期、小时、分钟、秒等信息
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 注意：月份从0开始，所以需要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "." + month + "." + day + "-" + hour + "." + minute + "." + second;
    }

}
