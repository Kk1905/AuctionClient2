package com.example.administrator.auctionclient.Activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.Fragment.ManageKindFragment;

/**
 * Created by Administrator on 2016/8/18.
 */

public class ManageKindActivity extends FragmentActivity implements Callbacks {
    @Override
    public Fragment getFragment() {
        ManageKindFragment fragment = new ManageKindFragment();
        return fragment;
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this, AddKindActivity.class);
        startActivity(intent);
    }
}
