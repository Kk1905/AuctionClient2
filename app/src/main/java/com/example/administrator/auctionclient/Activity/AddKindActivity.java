package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;

import com.example.administrator.auctionclient.Fragment.AddKindFragment;
import com.example.administrator.auctionclient.Base.FragmentActivity;

/**
 * Created by Administrator on 2016/8/19.
 */

public class AddKindActivity extends FragmentActivity {
    @Override
    public Fragment getFragment() {
        return new AddKindFragment();
    }
}
