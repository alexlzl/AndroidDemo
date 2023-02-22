package com.example.demo9;

import java.lang.reflect.Method;

/**
 * @ Description:
 * @ Author: LiuZhouLiang
 * @ Time：2023/2/7 9:44 上午
 */
//示例code
//在项目中新建一个工具类，FeatureParser，通过反射机制来获取miui.util.FeatureParser
public class FeatureParser {
    public static boolean getBoolean(String name, boolean defaultValue) {
        try {
            Class featureParserClass = Class.forName("miui.util.FeatureParser");
            Method method = featureParserClass.getMethod("getBoolean", String.class, boolean.class);
            return (Boolean) method.invoke(null, name, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}

    //在功能开始之前判断是否支持stepsProvider功能

