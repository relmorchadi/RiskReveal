package com.scor.rr.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Gsons{
    public static <T> List<T> asList(String json, String path, Class<T> clazz) {
        Gson gson = new Gson();
        String[] paths = path.split("\\.");
        JsonObject o = gson.fromJson(json, JsonObject.class);
        for (int i = 0; i < paths.length - 1; i++) {
            o = o.getAsJsonObject(paths[i]);
        }
        JsonArray jsonArray = o.getAsJsonArray(paths[paths.length - 1]);
        Class<T[]> clazzArray = (Class<T[]>) ((T[]) Array.newInstance(clazz, 0)).getClass();
        T[] objectArray = gson.fromJson(jsonArray, clazzArray);
        return Arrays.asList(objectArray);
    }
}
