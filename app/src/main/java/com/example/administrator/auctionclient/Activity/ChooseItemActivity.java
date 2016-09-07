package com.example.administrator.auctionclient.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.auctionclient.Base.FragmentActivity;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.Fragment.ChooseItemFragment;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ChooseItemActivity extends FragmentActivity implements Callbacks{
    @Override
    public Fragment getFragment() {
        ChooseItemFragment fragment = new ChooseItemFragment();
        Bundle arguments = new Bundle();
        arguments.putLong("kindId", getIntent().getLongExtra("kindId", -1));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this, AddBidActivity.class);
        intent.putExtra("itemId", bundle.getInt("itemId"));
        startActivity(intent);
    }
}
