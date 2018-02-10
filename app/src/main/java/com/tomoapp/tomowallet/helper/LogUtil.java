package com.tomoapp.tomowallet.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;
import java.util.Set;


/**
 * Created by NIENLM on 1/4/17.
 */

public class LogUtil {
    /*
     * Used to identify the source of a log message. It usually identifies the
     * class or activity where the log call occurs
     */
    public static String LOGTAG = "TOMOLOGTAG";

    /*
     * Is sent a VERBOSE log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGV = true;

    /*
     * Is sent a DEBUG log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LogD = true;

    /*
     * Is sent a ERROR log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LogE = true;

    /*
     * Is sent a INFO log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGI = true;

    /*
     * Is sent a WARN log message. Set true if you want to show log to debug
     * application, else set true
     */
    public static boolean LOGW = true;

    /**
     * Send a VERBOSE log message.
     *
     * @param log : The message you would like logged.
     */
    public static void v(String log) {
        if (LOGV) {
            Log.v(LOGTAG, log);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param log : The message you would like logged.
     * @param tr  : An exception to log
     */
    public static void v(String log, Throwable tr) {
        if (LOGV) {
            Log.v(LOGTAG, log, tr);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param log The message you would like logged.
     */
    public static void d(String log) {
        if (LogD) {
            Log.d(LOGTAG, log);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param log       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void d(String log, Throwable throwable) {
        if (LogD) {
            Log.d(LOGTAG, log, throwable);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param log The message you would like logged.
     */
    public static void e(String log) {
        if (LogE) {
            Log.e(LOGTAG, log);
        }
    }

    public static void e(Throwable e) {
        if (LogE && e != null) {
            Log.e(LOGTAG, Log.getStackTraceString(e));
            e.printStackTrace();
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String log, Throwable tr) {
        if (LogE) {
            Log.e(LOGTAG, log, tr);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param log The message you would like logged.
     */
    public static void i(String log) {
        if (LOGI) {
            Log.i(LOGTAG, log);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String log, Throwable tr) {
        if (LOGI) {
            Log.i(LOGTAG, log, tr);
        }
    }

    /**
     * Send a WARN log message
     *
     * @param log The message you would like logged.
     */
    public static void w(String log) {
        if (LOGW) {
            Log.w(LOGTAG, log);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param log The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String log, Throwable tr) {
        if (LOGW) {
            Log.w(LOGTAG, log, tr);
        }
    }


    /************
     * WTHLOG
     ************/
    public static final String WTHLOG = "ohgodplease";
    public static final boolean WTH = true;

    public static void wth(String log) {
        if (WTH) {
            Log.d(WTHLOG, log);
        }
    }


    public static void dumpSharedPreferences(Context context, String fileName){
        SharedPreferences preferences
                = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Map<String, ?> prefsMap = preferences.getAll();
        i("======== GET SHARED PREF FROM <" + fileName + "> - FOUND: " + prefsMap.size() + " ========");
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            i("===== KEY: " + entry.getKey() + " - VALUE: " +
                    entry.getValue().toString());
        }
        i("===================================================");
    }

    public static void dumpIntent(Intent i){
        dumpIntent(i, "UNKNOWN");
    }

    public static void dumpIntent(Bundle bundle){
        dumpIntent(bundle, "UNKNOWN");
    }

    public static void dumpIntent(Intent i, String className){
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IntentDump: " + className + " \n\r");
            stringBuilder.append("-------------------------------------------------------------\n\r");

            for (String key : keys) {
                stringBuilder.append(key).append("=").append(bundle.get(key)).append("\n\r");
            }

            stringBuilder.append("-------------------------------------------------------------\n\r");
            LogUtil.d(stringBuilder.toString());
        }
    }

    public static void dumpIntent(Bundle bundle, String className){

        if (bundle != null) {
            Set<String> keys = bundle.keySet();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IntentDump: " + className + " \n\r");
            stringBuilder.append("-------------------------------------------------------------\n\r");

            for (String key : keys) {
                stringBuilder.append(key).append("=").append(bundle.get(key)).append("\n\r");
            }

            stringBuilder.append("-------------------------------------------------------------\n\r");
            LogUtil.d(stringBuilder.toString());
        }
    }
}
