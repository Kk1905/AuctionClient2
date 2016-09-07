package com.example.administrator.auctionclient;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.administrator.auctionclient.Activity.AuctionClientActivity;

/**
 * Created by Administrator on 2016/8/18.
 */

public class HomeListener implements View.OnClickListener {
    private Activity activity;
    public HomeListener(Activity activity) {
        this.activity=activity;
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, AuctionClientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
