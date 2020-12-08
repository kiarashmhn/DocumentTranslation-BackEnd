package com.document.documentTranslator.enums;

public enum CommonMessages {
    ID(1, "Id", "شناسه", "id"),
    ROW_ID(2, "rowId", "شماره ردیف", "row id"),
    BEGIN(3, "begin", "شروع بازه", ""),
    LENGTH(4, "length", "طول بازه", ""),
    FARSI_NAME(5,"farsiName","نام فارسی", "farsi name"),
    QUOTE(6, "'", "'", "'"),
    ;

    private int code;
    private String name;
    private String persianName;
    private String englishName;

    private CommonMessages(int code, String name, String messageFarsi, String logMessage) {

        this.code = code;
        this.name = name;
        persianName = messageFarsi;
        englishName = logMessage;
    }

    public String getEnglishName() {

        return englishName;
    }

    public int getCode() {

        return code;
    }

    public String getPersianName() {

        return persianName;
    }

    public String getName() {

        return name;
    }

    public String getPluralName() {

        String singular = getName();
        String result = singular;
        if (singular.endsWith("s"))
            result += "es";
        else if (singular.endsWith("y"))
            result = singular.substring(0, singular.length() - 1) + "ies";
        else
            result += "s";

        return result;
    }
}
