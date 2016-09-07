package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.Fragment.ManageItemFragment;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ManageItemActivity extends FragmentActivity implements Callbacks{
    @Override
    public Fragment getFragment() {
        return new ManageItemFragment();
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}
