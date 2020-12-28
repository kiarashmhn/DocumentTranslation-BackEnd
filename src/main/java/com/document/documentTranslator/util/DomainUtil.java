package com.document.documentTranslator.util;

import com.document.documentTranslator.dto.BaseDto;
import com.document.documentTranslator.entity.AbstractEntity;
import com.document.documentTranslator.enums.CommonMessages;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainUtil {

    private static Logger logger = Logger.getLogger(DomainUtil.class);

    private DomainUtil() {
    }

    public static Long objectToLong(Object obj) {

        Long result = null;
        try {
            String objString = DomainUtil.objectToString(obj);
            if (objString != null && !objString.isEmpty()) {
                result = Long.parseLong(objString);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String objectToString(Object obj) {

        return escape(objectToStringWithoutEscape(obj));
    }

    public static String objectToStringWithoutEscape(Object obj) {

        String result = null;
        if (obj != null) {
            String stringValue = String.valueOf(obj);
            if (Validator.validateRequiredString(stringValue)) {
                stringValue = stringValue.trim();
                result = String.valueOf(obj);
            }
        }
        return result;
    }

    public static String escape(String input) {

        input = escapeCsvInjection(input);
        return escapeTemplateInjection(input);
    }

    private static String escapeCsvInjection(String input) {

        if (Validator.validateRequiredString(input) && input.matches("^[+=@-].*") && !input.matches("-?[0-9]+")) {
            return CommonMessages.QUOTE.getName() + input.charAt(0) + CommonMessages.QUOTE.getName() + (input.length() > 1 ? input.substring(1) : "");
        }
        return input;
    }

    private static String escapeTemplateInjection(String input) {

        if (Validator.validateRequiredString(input)) {
            input = input.replace("{{", "(").replace("}}", ")");
        }
        return input;
    }

    public static List<Map<String, Object>> toMapList(List<? extends AbstractEntity> list, int firstIndex) {

        List<Map<String, Object>> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            result = new ArrayList<>();
            for (AbstractEntity abstractEntity : list) {
                Map<String, Object> map = abstractEntity.map();
                map.put(CommonMessages.ROW_ID.getName(), ++firstIndex);
                result.add(map);
            }
        }

        return result;

    }

    public static List<?> objectToList(Object obj) {

        List<?> result = null;
        if (obj instanceof List<?>) {

            result = (List<?>) obj;
        }
        return result;
    }

    public static List<Long> objectToLongList(Object obj) {

        List<Long> result = new ArrayList<>();

        List<?> list = DomainUtil.objectToList(obj);
        if (!Validator.isNull(list) && !list.isEmpty()) {
            for (Object o : list) {
                String itemString = String.valueOf(o);
                try {
                    Long itemLong = Long.parseLong(itemString);
                    result.add(itemLong);
                } catch (Exception e) {
                    logger.error("error in converting anonymous list item to long, ", e);
                }
            }
        }
        return result;
    }

    public static String listToString(List<?> list, boolean addQuotation) {

        StringBuilder query = new StringBuilder("(");
        for (int i = 0; i < list.size() - 1; i++)
            if (addQuotation)
                query.append("'").append(list.get(i)).append("'").append(",");
            else query.append(list.get(i)).append(",");
        if (addQuotation)
            query.append("'").append(list.get(list.size() - 1)).append("'").append(")");
        else query.append(list.get(list.size() - 1)).append(")");
        return query.toString();
    }

    public static Map<String, Object> stringToMap(String mapAsString) {
        String[] trimmed = mapAsString.substring(1, mapAsString.length() - 1).split(", ");
        Map<String, Object> result = new HashMap<>();
        for (String str: trimmed)
        {
            List<String> split = Arrays.asList(str.split("="));
            if (split.size() > 1) {
                List<Object> objects = (List<Object>) DomainUtil.objectToList(split.get(1));
                if (Validator.listNotNull(objects))
                    result.put(split.get(0), objects);
                result.put(split.get(0), split.get(1));
            }
            else result.put(split.get(0), "");
        }
        return result;
    }

    public static int getBegin(BaseDto dto) {
        return Validator.notNull(dto.getBegin()) ? dto.getBegin() : 0;
    }

    public static int getLength(BaseDto dto) {
        return Validator.notNull(dto.getLength()) ? dto.getLength() : 10;
    }

    public static String mapToTrimmedString(Map<String, Object> map) {
        String mapStr = DomainUtil.objectToString(map);
        return mapStr.substring(1, mapStr.length() - 1);
    }

    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof HashMap) {
            JSONObject json = new JSONObject();
            HashMap map = (HashMap) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object)) {
                json.add(toJSON(value));
            }
            return json;
        }
        else {
            return object;
        }
    }

    public static String mapToString(HashMap<String, Object> map) {
        Object object = toJSON(map);
        return objectToString(object);
    }

    public static JSONObject stringToJson(String s) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(s);
        }
        catch (ParseException e) {
            return null;
        }
    }
}
