package com.kaifer.fasttwentyone;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.kaifer.fasttwentyone.utils.CrashReporter;

import timber.log.Timber;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class FastTwentyOneApp extends Application {

    private static volatile FastTwentyOneApp instance = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;

        initTimber();
        initFacebook();
    }

    private void initTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        else{
            Timber.plant(new CrashReportingTree());
        }
    }

    private void initFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        FastTwentyOneApp.currentActivity = currentActivity;
    }
    private static class CrashReportingTree extends Timber.Tree{
        @Override
        protected void log(int priority, String tag, String message, Throwable t){
            if(priority == Log.VERBOSE || priority == Log.DEBUG){
                return;
            }
            CrashReporter.log(priority, tag, message);
            if(t != null){
                if(priority == Log.ERROR){
                    CrashReporter.logError(t);
                }
                else if(priority == Log.WARN){
                    CrashReporter.logWarning(t);
                }
            }
        }
    }

    // detach singleton
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
