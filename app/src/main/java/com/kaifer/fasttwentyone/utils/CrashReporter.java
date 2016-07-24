package com.kaifer.fasttwentyone.utils;

import android.util.Log;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class CrashReporter {
    private static final String TAG = "CrashReporter";
    public static void log(int priority, String tag, String message){
        Log.i(TAG, "log: "+message );
    }

    //TODO: control error, warning
    public static void logWarning(Throwable t){
        Log.i(TAG, "logWarning: "+t.toString());
    }

    public static void logError(Throwable t){
        Log.i(TAG, "logError: "+t.toString() );
    }

    private CrashReporter(){
        throw new AssertionError("no instance");
    }
}
