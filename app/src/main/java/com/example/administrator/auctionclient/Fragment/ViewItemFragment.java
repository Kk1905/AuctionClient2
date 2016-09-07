package com.example.administrator.auctionclient.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.Adapter.JSONArrayAdapter;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/18.
 */

public class ViewItemFragment extends Fragment {
    private Button bnHome;
    private ListView succList;
    private TextView viewTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_item, container, false);
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        succList = (ListView) rootView.findViewById(R.id.succList);
        viewTitle = (TextView) rootView.findViewById(R.id.view_title);
        bnHome.setOnClickListener(new HomeListener(getActivity()));

        String action = getArguments().getString("action");
        //定义发送请求的URL
        String url = HttpUtil.BASE_URL + action;
        //如果是查看流拍物品，修改标题
        if (action.equals("viewFail.jsp")) {
            viewTitle.setText(R.string.view_fail);
        }
        try {
            String result = HttpUtil.getRequest(url);
            //将服务器的响应转化成JSONArray对象
            JSONArray jsonArray = new JSONArray(result);
            //将JSONArray对象包装成Adapter对象
            JSONArrayAdapter adapter=new JSONArrayAdapter(getActivity(), jsonArray, "name", true);
            succList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        succList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //查看物品详情
                viewItemDetail(i);
            }
        });
        return rootView;
    }

    //查看物品详情的方法
    private void viewItemDetail(int position) {
        //加载detail.xml布局文件
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.detail, null);
        //获取detail.xml布局文件中各个组件
        TextView itemName = (TextView) detailView.findViewById(R.id.itemName);
        TextView itemKind = (TextView) detailView.findViewById(R.id.itemKind);
        TextView maxPrice = (TextView) detailView.findViewById(R.id.winPrice);
        TextView remark = (TextView) detailView.findViewById(R.id.remark);
        //获取被单击的列表项
        JSONObject jsonObject = (JSONObject) succList.getAdapter().getItem(position);

        try {
            itemName.setText(jsonObject.getString("name"));
            itemKind.setText(jsonObject.getString("kind"));
            maxPrice.setText(jsonObject.getString("maxPrice"));
            remark.setText(jsonObject.getString("desc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //最后通过Dialog来显示组件
        DialogUtils.showDialog(getActivity(), detailView);
    }
}
