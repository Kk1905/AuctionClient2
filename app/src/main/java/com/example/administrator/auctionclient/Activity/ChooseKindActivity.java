package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.Fragment.ChooseKindFragment;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ChooseKindActivity extends FragmentActivity implements Callbacks {
    @Override
    public Fragment getFragment() {
        return new ChooseKindFragment();
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this, ChooseItemActivity.class);
        intent.putExtra("kindId", bundle.getLong("kindId"));
        startActivity(intent);
    }
}
