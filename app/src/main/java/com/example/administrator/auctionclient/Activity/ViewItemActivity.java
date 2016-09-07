package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Fragment.ViewItemFragment;

/**
 * Created by Administrator on 2016/8/18.
 */

public class ViewItemActivity extends FragmentActivity {
    @Override
    public Fragment getFragment() {
        ViewItemFragment fragment = new ViewItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString("action", getIntent().getStringExtra("action"));
        fragment.setArguments(arguments);
        return fragment;
    }
}
