package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Fragment.AddBidFragment;

/**
 * Created by Administrator on 2016/8/19.
 */

public class AddBidActivity extends FragmentActivity {
    @Override
    public Fragment getFragment() {
        AddBidFragment fragment = new AddBidFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("itemId", getIntent().getIntExtra("itemId", -1));
        return fragment;
    }

}
