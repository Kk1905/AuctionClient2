package com.example.administrator.auctionclient.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.auctionclient.Adapter.JSONArrayAdapter;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ManageItemFragment extends Fragment {
    public static final int ADD_ITEM = 0x1006;
    private ListView itemList;
    private Callbacks mcallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_item, container, false);
        Button bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        Button bnHome = (Button) rootView.findViewById(R.id.bn_home);
        itemList = (ListView) rootView.findViewById(R.id.itemList);
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallbacks.onItemSelected(ADD_ITEM, null);
            }
        });
        //定义发送请求的URL
        String url = HttpUtil.BASE_URL + "viewOwnerItem.jsp";
        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name", true);
            itemList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "ManageItemFragment所在的Activity必须实现Callbacks接口"
            );
        }
        mcallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mcallbacks = null;
    }
    private void viewItemInBid(int position) {
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.detail_in_bid, null);
        TextView itemName = (TextView) detailView.findViewById(R.id.itemName);
        TextView itemKind = (TextView) detailView.findViewById(R.id.kindName);
        TextView maxPrice = (TextView) detailView.findViewById(R.id.winPrice);
        TextView initPrice = (TextView) detailView.findViewById(R.id.initPrice);
        TextView endTime = (TextView) detailView.findViewById(R.id.endTime);
        TextView itemMark = (TextView) detailView.findViewById(R.id.remark);
        //获取被点击的列表项所访问的服务器返回的JSONObject
        JSONObject jsonObject = (JSONObject) itemList.getAdapter().getItem(position);

        try {
            itemName.setText(jsonObject.getString("name"));
            itemKind.setText(jsonObject.getString("kind"));
            maxPrice.setText(jsonObject.getString("maxPrice"));
            initPrice.setText(jsonObject.getString("initPrice"));
            endTime.setText(jsonObject.getString("endTime"));
            itemMark.setText(jsonObject.getString("desc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DialogUtils.showDialog(getActivity(), detailView);
    }
}
