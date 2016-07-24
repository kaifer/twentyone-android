package com.kaifer.fasttwentyone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kaifer.fasttwentyone.R;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class TokenManager {
    public static String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.sp_21), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.sp_token), context.getString(R.string.sp_empty));

        return token;
    }

    public static Long getUserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.sp_21), Context.MODE_PRIVATE);
        Long userId = sharedPref.getLong(context.getString(R.string.sp_user_id), 0L);

        return userId;
    }

    public static String getRegToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.sp_21), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.sp_reg_token), "");

        return token;
    }
}
