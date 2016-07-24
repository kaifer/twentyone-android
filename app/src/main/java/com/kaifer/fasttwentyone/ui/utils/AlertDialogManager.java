package com.kaifer.fasttwentyone.ui.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class AlertDialogManager {
    public static AlertDialog simpleAlertDialog(Context context, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.setMessage(message);

        return alert.create();
    }
}
