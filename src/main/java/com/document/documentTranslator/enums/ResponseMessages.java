package com.document.documentTranslator.enums;

public enum ResponseMessages {

    SUCCESSFUL(0,"successful","با موفقیت انجام شد","successful"),
            ;

    private int code;
    private String englishMessage;
    private String persianMessage;
    private String logMessage;

    private ResponseMessages(int inputCode, String inputEngMsg, String inputPerMsg, String inputLogMsg) {

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
