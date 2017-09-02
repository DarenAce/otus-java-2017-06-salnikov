package ru.otus.homework_08;

import org.json.simple.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public final class JSON {
    private JSON() {
    }

    public static String toJSON(Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof Number || o instanceof Boolean || o instanceof String) {
            return JSONValue.toJSONString(o);
        }
        if (o instanceof Character) {
            return JSONValue.toJSONString(String.valueOf(o));
        }
        if (o instanceof List) {
            return buildArray(((List) o).toArray()).toJSONString();
        }
        if (o instanceof Map) {
            return JSONObject.toJSONString(buildMap((Map<Object, Object>) o));
        }
        if (o.getClass().isArray()) {
            return buildArray(o).toJSONString();
        }
        return buildObject(o).toJSONString();
    }

    private static JSONObject buildObject(Object o) {
        JSONObject jsonObject = new JSONObject();
        for (Field field : ReflectionHelper.getAllClassFields(o.getClass())) {
            int fieldMods = field.getModifiers();
            if (Modifier.isStatic(fieldMods) || Modifier.isTransient(fieldMods)) {
                continue;
            }
            String name = field.getName();
            Object value = ReflectionHelper.getFIeldValue(o, field);

            if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                jsonObject.put(name, value);
            } else if (value instanceof Character) {
                jsonObject.put(name, String.valueOf(value));
            } else if (value instanceof List) {
                jsonObject.put(name, buildArray(((List) value).toArray()));
            } else if (value instanceof Map) {
                jsonObject.put(name, new JSONObject(buildMap((Map<Object, Object>) value)));
            } else if (value.getClass().isArray()) {
                jsonObject.put(name, buildArray(value));
            } else {
                jsonObject.put(name, buildObject(value));
            }
        }
        return jsonObject;
    }

    private static JSONArray buildArray(Object o) {
        JSONArray array = new JSONArray();
        int length = Array.getLength(o);
        Object value = Array.get(o, 0);
        for (int i = 0; i < length; i++) {
            if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                array.add(Array.get(o, i));
            } else if (value instanceof Character) {
                array.add(String.valueOf(Array.get(o, i)));
            } else if (value instanceof List) {
                array.add(new JSONArray().addAll((List) value));
            } else if (value instanceof Map) {
                array.add(new JSONObject(buildMap((Map<Object, Object>) o)));
            } else if (value.getClass().isArray()) {
                array.add(buildArray(Array.get(o, i)));
            } else {
                array.add(buildObject(Array.get(o, i)));
            }
        }
        return array;
    }

    private static Map<String, Object> buildMap(Map<Object, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>();
        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Object value = iterator.next().getValue();
            if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(), entry.getValue());
                }
            } else if (value instanceof Character) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(), String.valueOf(entry.getValue()));
                }
            } else if (value instanceof List) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(),
                            buildArray(((List) entry.getValue()).toArray()));
                }
            } else if (value instanceof Map) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(),
                            buildMap((Map<Object, Object>) entry.getValue()));
                }
            } else if (value.getClass().isArray()) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(),
                            buildArray(entry.getValue()));
                }
            } else {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    result.put(entry.getKey().toString(),
                            buildObject(entry.getValue()));
                }
            }
        }
        return result;
    }
}