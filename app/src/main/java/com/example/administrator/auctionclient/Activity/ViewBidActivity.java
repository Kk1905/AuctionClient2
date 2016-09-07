package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Fragment.ViewBidFragment;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ViewBidActivity extends FragmentActivity {
    @Override
    public Fragment getFragment() {
        return new ViewBidFragment();
    }

}
