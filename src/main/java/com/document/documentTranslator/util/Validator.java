package com.document.documentTranslator.util;

import java.util.List;

public class Validator {

    private Validator() {
    }

    public static boolean notNull(Object str) {

        return !Validator.isNull(str);
    }

    public static boolean isNull(Object obj) {

        if (obj instanceof String) {
            String str = (String) obj;
            return str.equals("") || str.equalsIgnoreCase("null") || str.isEmpty();
        } else {
            return obj == null;
        }
    }

    public static boolean listNotNull(List<? extends Object> list) {

        boolean result = false;

        if (Validator.notNull(list) && !list.isEmpty() && !list.contains("") && !list.contains("null"))
            result = true;

        return result;

    }

    public static boolean validateRequiredString(String str) {

        return (str != null && !str.isEmpty());
    }

}

