package com.document.documentTranslator.util;


import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MONTH;

public class DateUtil {

    private DateUtil() {
    }

    //ms to timestamp
    public static long toDiagMilliseconds(long milliseconds) {
        return new BigDecimal(milliseconds)
                .add(new BigDecimal("315964800000").negate())
                .divide(new BigDecimal("1.25"), RoundingMode.HALF_DOWN)
                .multiply(new BigDecimal(65536))
                .longValue();
    }

    //timestamp to ms
    public static long toStandardMilliseconds(long milliseconds) {
        return new BigDecimal(milliseconds)
                .multiply(new BigDecimal("1.25"))
                .divide(new BigDecimal(65536), RoundingMode.HALF_DOWN)
                .add(new BigDecimal("315964800000"))
                .longValue();
    }

    public static long dateToTimeStamp(Date date) {
        return toDiagMilliseconds(date.getTime());
    }

    public static Date timeStampToDate(long timestamp) {
        return new Date(toStandardMilliseconds(timestamp));
    }

    public static String timeStampToDateFormatted(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date(toStandardMilliseconds(timestamp)));
    }

    public static Long dateStringToTimeStamp(String date) throws DomainException {

        try {
            if (Validator.notNull(date)) {
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                return dateToTimeStamp(df.parse(date));
            }
        } catch (ParseException e) {
            throw new DomainException(ErrorMessage.INVALID_DATE_FORMAT);
        }

        return null;
    }

    public static String format(Date date) {

        if (date == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(MONTH) + 100 + 1).substring(1);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + 100).substring(1);
        String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY) + 100).substring(1);
        String minute = String.valueOf(cal.get(Calendar.MINUTE) + 100).substring(1);
        String second = String.valueOf(cal.get(Calendar.SECOND) + 100).substring(1);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }
}
