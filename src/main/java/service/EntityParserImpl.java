package service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

class EntityParserImpl implements EntityParser {

    public Object parse (String body, Class<?> clazz) {
        try {
            HashMap<String, String> params = getParams (body);
            Object object = clazz.getConstructor ().newInstance ();

            for (Field field : object.getClass ().getDeclaredFields ()) {
                String key = field.getName ();
                if (params.containsKey (key)) {
                    setValue (object, field, params.get (key));
                }
            }

            return object;
        } catch (Exception e) {
            throw new IllegalStateException (e);
        }
    }

    private void setValue (Object object, Field field, String value) {
        try {

            field.setAccessible (true);

            final Class<?> type = field.getType ();

            if (String.class.equals (type)) {
                field.set (object, value);
            }

            if (int.class.equals (type) || Integer.class.equals (type)) {
                field.set (object, Integer.parseInt (value));
            }

            if (long.class.equals (type) || Long.class.equals (type)) {
                field.set (object, Long.parseLong (value));
            }

            field.setAccessible (false);

        } catch (IllegalAccessException e) {
            throw new IllegalStateException (e);
        }
    }


    private HashMap<String, String> getParams (String body) {
        HashMap<String, String> params = new HashMap<> ();

        Arrays.stream (body.split ("&")).forEach (line -> {
            String[] param = line.split ("=");
            params.put (param[0], decode (param[1]));
        });

        return params;
    }

    private String decode (String url) {
        try {
            return URLDecoder.decode (url, StandardCharsets.UTF_8.name ());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException (e);
        }
    }
}
