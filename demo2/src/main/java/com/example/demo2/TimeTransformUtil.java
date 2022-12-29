package com.example.demo2;

import java.text.DecimalFormat;

/**
 * @ Description:训练详情页面暂停时间转换规则
 * @ Author: LiuZhouLiang
 * @ Time：2022/12/29 3:06 下午
 */
public class TimeTransformUtil {
    /**
     * @ description
     * @ param 暂停时间单位为毫秒
     * @ return
     * @ author LiuZhouLiang
     * @ time 2022/12/29 3:08 下午
     */
    public static String transform(int pauseSecond) {
        if (pauseSecond > 60 * 60 * 1000) {
            return transformToHour(transformSecond(pauseSecond));
        } else {
            return transformToMinute(pauseSecond, 60 * 1000);
        }
    }

    private static int transformSecond(int millisecond) {
        if (millisecond % 1000 > 0) {
            return (millisecond / 1000) + 1;
        }
        return millisecond / 1000;
    }

    /**
     * @ description 转换为分钟单位
     * @ param
     * @ return
     * @ author LiuZhouLiang
     * @ time 2022/12/29 3:12 下午
     */
    private static String transformToMinute(int pauseTime, int minute) {
        String result = "";
        float num = (float) pauseTime / minute;

        DecimalFormat df = new DecimalFormat("0.00");

        result = df.format(num);
        /**
         * 分钟为整数
         */
        if (result.endsWith("00")) {
            result = result.substring(0, result.length() - 3) + "分钟";
        }
        /**
         * 包含小数点分钟情况
         */
        if (result.contains(".")) {

            result = result.substring(0, result.length() - 3);

            Long longTime = Long.parseLong(result);
            longTime++;
            return longTime + "分钟";
        }

        return result;

    }

    /**
     * @ description 转换为小时分钟单位
     * @ param
     * @ return
     * @ author LiuZhouLiang
     * @ time 2022/12/29 3:13 下午
     */
    private static String transformToHour(int time) {
        int second = time % 60;
        int minute = time / 60;
        int hour = 0;
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String timeString = "";
        String secondString = "";
        String minuteString = "";
        String hourString = "";
        secondString = second + "";
        if (second > 0) {
            minute++;
        }
        minuteString = minute + "";
        hourString = hour + "";
        if ("0".equals(minuteString) && "0".equals(secondString)) {
            timeString = hourString + "小时";
        } else {
            timeString = hourString + "小时" + minuteString + "分钟";
        }
        return timeString;
    }
}
