package com.example.administrator.auctionclient.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.example.administrator.auctionclient.Activity.AuctionClientActivity;

/**
 * Created by Administrator on 2016/8/18.
 */

public class DialogUtils {

    //定义一个现实消息的对话框
    public static void showDialog(final Context context, String str, boolean goHome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(str).setCancelable(false);
        if (goHome) {
            //如果是需要回到home,相当于系统的主界面
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(context, AuctionClientActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });
        } else {
            builder.setPositiveButton("确定", null);
        }
        builder.create().show();
    }

    //定义一个现实指定组件的对话框
    public static void showDialog(Context context, View view) {
        new AlertDialog.Builder(context).setView(view)
                .setCancelable(false)
                    .setPositiveButton("确定",null)
                .create().show();
    }
}
