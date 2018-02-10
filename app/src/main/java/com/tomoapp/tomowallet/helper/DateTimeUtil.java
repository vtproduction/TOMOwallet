package com.tomoapp.tomowallet.helper;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
/**
 * Created by cityme on 1/4/18.
 */

public class DateTimeUtil {
    public static int TIME_DIFF = TimeZone.getDefault().getRawOffset();
    public static DateTime stringToDatetime(String dateTimeString){
        DateTime dateTime;
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            dateTime = df.parseDateTime(dateTimeString.replace('T',' ').substring(0, dateTimeString.length() - 1));
            dateTime = dateTime.plusHours(TIME_DIFF);
        }catch (Exception e){
            LogUtil.e(e.getMessage());
            dateTime = DateTime.now();
        }
        return dateTime;
    }

    public static DateTime stringToDate(String dateString){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
                    .withLocale(Locale.ROOT)
                    .withChronology(ISOChronology.getInstanceUTC());

            return formatter.parseDateTime(dateString);
        }catch (Exception e){
            LogUtil.e(e);
            return DateTime.now();
        }
    }

    public static String displayShortDate(String dateString){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy")
                    .withLocale(Locale.ROOT)
                    .withChronology(ISOChronology.getInstanceUTC());
            DateTime dateTime = stringToDate(dateString);
            dateTime = dateTime.plusMillis(TIME_DIFF);
            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public static String displayLongDate(String dateString){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
                    .withLocale(Locale.ROOT)
                    .withChronology(ISOChronology.getInstanceUTC());
            DateTime dateTime = stringToDate(dateString);
            dateTime = dateTime.plusMillis(TIME_DIFF);
            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public static String displayTime(String dateString){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("HH:mm:ss")
                    .withLocale(Locale.ROOT)
                    .withChronology(ISOChronology.getInstanceUTC());
            DateTime dateTime = stringToDate(dateString);
            dateTime = dateTime.plusMillis(TIME_DIFF);
            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }
    public static String displayShortDate(DateTime dateTime){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy")
                    .withLocale(Locale.ROOT)
                    .withChronology(ISOChronology.getInstanceUTC());

            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public static String displayLongDate(DateTime dateTime){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public static String formatDateToUpdate(DateTime dateTime){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
            return dtfOut.print(dateTime);
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }

    public static String getTimeNowToString(){
        try {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
            return dtfOut.print(DateTime.now());
        }catch (Exception e){
            LogUtil.e(e);
            return "";
        }
    }
}
