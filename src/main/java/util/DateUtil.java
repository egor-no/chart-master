package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDateForSQL(Date date) {
        return sqlDateFormat.format(date);
    }

    public static String formatDateForSQL(String sDate) {
        return formatDateForSQL(parseStringToSqlDate(sDate));
    }

    public static Date parseStringToDate(String sDate) {
        Date date = null;
        try {
            date = dateFormat.parse(sDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return date;
    }

    public static Date parseStringToSqlDate(String sDate) {
        Date date = null;
        try {
            date = sqlDateFormat.parse(sDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return date;
    }

}
