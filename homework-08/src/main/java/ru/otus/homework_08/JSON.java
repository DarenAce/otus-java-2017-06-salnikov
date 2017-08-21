package ru.otus.homework_08;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public final class JSON {
    private JSON() {
    }

    public static String toJSON(Object o) {
        if (o == null) {
            return "";
        }
        return build(o).toJSONString();
    }

    private static JSONObject build(Object o) {
        JSONObject jsonObject = new JSONObject();
        for (Field field : o.getClass().getDeclaredFields()) {
            int fieldMods = field.getModifiers();
            if (Modifier.isStatic(fieldMods) || Modifier.isTransient(fieldMods)) {
                continue;
            }
            String name = field.getName();
            Object value = ReflectionHelper.getFieldValue(o, name);

            if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                jsonObject.put(name, value);
            } else if (value instanceof Character) {
                jsonObject.put(name, value.toString());
            } else if (value instanceof List) {
                JSONArray array = new JSONArray();
                array.addAll((List) value);
            } else if (value.getClass().isArray()) {
                JSONArray array = new JSONArray();
                for (int i = 0; i < Array.getLength(value); i++) {
                    array.add(Array.get(value, i));
                }
                jsonObject.put(name, array);
            } else {
                jsonObject.put(name, build(value));
            }
        }
        return jsonObject;
    }
}