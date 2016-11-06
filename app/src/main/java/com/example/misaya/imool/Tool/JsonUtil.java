package com.example.misaya.imool.Tool;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {
    public static String ObjectToJson(Object cls) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(cls);
        return jsonStr;
    }

    public static <T> T JsonToObject(String jsonStr,Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }
}
