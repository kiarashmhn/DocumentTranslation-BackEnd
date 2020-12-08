package com.document.documentTranslator.util;

import com.document.documentTranslator.entity.AbstractEntity;
import com.document.documentTranslator.enums.CommonMessages;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return Arrays.stream(mapAsString.substring(1, mapAsString.length() - 1).split(", "))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

}
