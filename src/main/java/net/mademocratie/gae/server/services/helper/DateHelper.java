package net.mademocratie.gae.server.services.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateHelper
 */
public class DateHelper {
    public static String getDateFormat(Date myDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // "yyyy-MM-dd HH:mm:ss.SSSZ"
        return dateFormat.format(myDate);
    }
}
