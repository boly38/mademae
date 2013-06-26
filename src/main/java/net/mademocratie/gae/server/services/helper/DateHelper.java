package net.mademocratie.gae.server.services.helper;

import org.joda.time.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateHelper
 */
public class DateHelper {
    public static String getDateFormat(Date myDate) {
        if (myDate == null) {
            return "(null)";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // "yyyy-MM-dd HH:mm:ss.SSSZ"
        return dateFormat.format(myDate);
    }

    public static String getDateSerializeFormat(Date myDate) {
        if (myDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(myDate);
    }

    public static String getDateDuration(Date givenDate) {
        Duration duration = new Duration(givenDate.getTime(), new Date().getTime());
        if (duration.getStandardDays() > 1) {
            return duration.getStandardDays() + " days ago";
        }
        if (duration.getStandardDays() == 1) {
            return "1 day ago";
        }
        if (duration.getStandardHours() >= 1) {
            return duration.getStandardHours()+ " hours ago";
        }
        if (duration.getStandardMinutes() >= 1) {
            return duration.getStandardMinutes()+ " minutes ago";
        }
        return "a moment ago";
    }
}
