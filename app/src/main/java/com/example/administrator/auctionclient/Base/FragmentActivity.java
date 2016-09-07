package com.example.administrator.auctionclient.Base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/8/18.
 */

public abstract class FragmentActivity extends Activity {
    private static final int ROOT_CONTAINER_ID = 0x90001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setId(ROOT_CONTAINER_ID);
        getFragmentManager().beginTransaction()
                .replace(ROOT_CONTAINER_ID, getFragment())
                .commit();
    }

    public abstract Fragment getFragment();
}
