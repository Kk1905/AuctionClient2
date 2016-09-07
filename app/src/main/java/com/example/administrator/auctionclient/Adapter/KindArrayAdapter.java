package com.example.administrator.auctionclient.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.auctionclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/19.
 */

public class KindArrayAdapter extends BaseAdapter {
    private JSONArray jsonArray;
    private Context context;

    public KindArrayAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return jsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        try {
            return ((JSONObject) getItem(i)).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View container = ((Activity) context).getLayoutInflater().inflate(R.layout.kind_adapter, null, false);
        TextView kindName = (TextView) container.findViewById(R.id.kindName);
        TextView kindDesc = (TextView) container.findViewById(R.id.kindDesc);
        try {
            String name = ((JSONObject) getItem(i)).getString("kindName");
            String desc = ((JSONObject) getItem(i)).getString("kindDesc");
            kindDesc.setText(desc);
            kindName.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return container;
    }
}
