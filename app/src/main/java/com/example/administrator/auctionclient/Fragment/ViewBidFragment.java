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

import com.example.administrator.auctionclient.Adapter.JSONArrayAdapter;
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

public class ViewBidFragment extends Fragment {
    Button bnHome;
    ListView bidList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_bid, container, false);
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        bidList = (ListView) rootView.findViewById(R.id.bidList);
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        String url = HttpUtil.BASE_URL + "viewBid.jsp";
        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "item", true);
            bidList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试！", false);
            e.printStackTrace();
        }
        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //查看竞价详情
                viewBidDetail(i);
            }
        });
        return rootView;
    }

    private void viewBidDetail(int position) {
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.bid_detail, null);
        // 获取bid_detail界面中的文本框
        TextView itemName = (TextView) detailView
                .findViewById(R.id.itemName);
        TextView bidPrice = (TextView) detailView
                .findViewById(R.id.bidPrice);
        TextView bidTime = (TextView) detailView
                .findViewById(R.id.bidTime);
        TextView bidUser = (TextView) detailView
                .findViewById(R.id.bidUser);
        //获取被点击列表项所包装的jsonobject信息
        JSONObject jsonObject = (JSONObject) bidList.getAdapter().getItem(position);
        //使用文本框显示竞价详情
        try {
            itemName.setText(jsonObject.getString("item"));
            bidPrice.setText(jsonObject.getString("price"));
            bidTime.setText(jsonObject.getString("bidDate"));
            bidUser.setText(jsonObject.getString("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DialogUtils.showDialog(getActivity(), detailView);
    }
}
