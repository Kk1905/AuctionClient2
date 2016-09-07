package com.example.administrator.auctionclient.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.auctionclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/18.
 */

public class JSONArrayAdapter extends BaseAdapter {
    private Context context;
    private JSONArray jsonArray;
    //定义列表显示JSONObject对象的哪个属性
    private String property;
    private boolean hasIcon;

    public JSONArrayAdapter(Context context, JSONArray jsonArray, String property, boolean hasIcon) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.property = property;
        this.hasIcon = hasIcon;
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
        //返回物品的id
        try {
            return ((JSONObject) getItem(i)).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //定义一个布局管理器
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.item);
        imageView.setPadding(10, 0, 30, 0);
        linearLayout.addView(imageView);
        TextView textView = new TextView(context);
        //获取JSONArray数组元素的property属性
        try {
            String itemName=((JSONObject)getItem(i)).getString(property);
            textView.setText(itemName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textView.setTextSize(20);
        if (hasIcon) {
            //如果有图片的话，就返回装有imageview和textview组件的linerlayout容器
            linearLayout.addView(textView);
            return linearLayout;
        } else {
            //如果没有图片，只返回textview
            return textView;
        }
    }
}
