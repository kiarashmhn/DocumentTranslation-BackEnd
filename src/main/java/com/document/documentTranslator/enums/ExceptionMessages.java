package com.document.documentTranslator.enums;

public enum ExceptionMessages {

    EXCEPTION_MESSAGE(0, "The application has encountered an unknown error", "خطای داخلی رخ داده است", "Unknown Internal Error"),
    DATA_INTEGRITY_VIOLATION_EXCEPTION(1, "Data Integrity Violation Exception", "امکان ثبت ایتم تکراری وجود ندارد.", "Unknown Internal Error"),

    ;

    private int code;
    private String englishMessage;
    private String persianMessage;
    private String logMessage;

    private ExceptionMessages(int inputCode, String inputEngMsg, String inputPerMsg, String inputLogMsg) {

        code = inputCode;
        englishMessage = inputEngMsg;
        persianMessage = inputPerMsg;
        logMessage = inputLogMsg;
    }

    public int getCode() {

        return code;
    }

    public String getEnglishMessage() {

        return englishMessage;
    }

    public String getPersianMessage() {

        return persianMessage;
    }

    public String getLogMessage() {

        return logMessage;
    }

}
