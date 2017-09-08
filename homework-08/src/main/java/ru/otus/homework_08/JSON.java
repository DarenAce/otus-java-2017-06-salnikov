package ru.otus.homework_08;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JSON {
    private JSON() {
    }

    public static String toJSON(Object o) {
        return JSONValue.toJSONString(convertInputObject(o));
    }

    private static Object convertInputObject(Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof Number || o instanceof Boolean || o instanceof String) {
            return o;
        }
        if (o instanceof Character) {
            return o.toString();
        }
        if (o.getClass().isArray()) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < Array.getLength(o); i++) {
                array.add(convertInputObject(Array.get(o, i)));
            }
            return array;
        }
        if (o instanceof List) {
            JSONArray list = new JSONArray();
            ((List) o).forEach(element -> list.add(convertInputObject(element)));
            return list;
        }
        if (o instanceof Map) {
            Map<Object, Object> map = new LinkedHashMap<>();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) o).entrySet()) {
                map.put(entry.getKey().toString(), convertInputObject(entry.getValue()));
            }
            return map;
        }
        return buildObject(o);
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

            jsonObject.put(name, convertInputObject(value));
        }
        return jsonObject;
    }
}
