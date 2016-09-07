package com.example.administrator.auctionclient.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */

public class AddBidFragment extends Fragment {
    TextView itemName, itemDesc, itemRemark, itemKind, initPrice, maxPrice, endTime;
    EditText bidPrice;
    Button bnAdd, bnCancel;
    //定义当前正在拍卖的物品
    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_bid, container, false);
        // 获取界面中编辑框
        itemName = (TextView) rootView.findViewById(R.id.itemName);
        itemDesc = (TextView) rootView.findViewById(R.id.itemDesc);
        itemRemark = (TextView) rootView.findViewById(R.id.itemRemark);
        itemKind = (TextView) rootView.findViewById(R.id.itemKind);
        initPrice = (TextView) rootView.findViewById(R.id.initPrice);
        maxPrice = (TextView) rootView.findViewById(R.id.maxPrice);
        endTime = (TextView) rootView.findViewById(R.id.endTime);
        bidPrice = (EditText) rootView.findViewById(R.id.bidPrice);
        // 获取界面中的两个按钮
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
        bnCancel.setOnClickListener(new HomeListener(getActivity()));
        String url = HttpUtil.BASE_URL + "getItem.jsp?itemId=" + getArguments().getInt("itemId");
        try {
            jsonObject = new JSONObject(HttpUtil.getRequest(url));
            //使用文本框来显示拍卖物品的详情
            itemName.setText(jsonObject.getString("name"));
            itemDesc.setText(jsonObject.getString("desc"));
            itemRemark.setText(jsonObject.getString("remark"));
            itemKind.setText(jsonObject.getString("kind"));
            initPrice.setText(jsonObject.getString("initPrice"));
            maxPrice.setText(jsonObject.getString("maxPrice"));
            endTime.setText(jsonObject.getString("endTime"));
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应出现异常！", false);
            e.printStackTrace();
        }
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //执行类型转换
                    double curPrice = Double.parseDouble(bidPrice.getText().toString());
                    //进行校验
                    if (curPrice < jsonObject.getDouble("maxPrice")) {
                        DialogUtils.showDialog(getActivity(), "您的竞标价必须要高于当前的竞价", false);
                    } else {
                        //添加竞价
                        String result = addBid(jsonObject.getString("id"), bidPrice.getText().toString());
                        DialogUtils.showDialog(getActivity(), result, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e1) {
                    DialogUtils.showDialog(getActivity()
                            , "您输入的竞价必须是数值", false);
                    e1.printStackTrace();
                } catch (Exception e) {
                    DialogUtils.showDialog(getActivity(), "服务器响应出现异常！", false);
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    private String addBid(String itemId, String bidPrice) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("bidPrice", bidPrice);
        String url = HttpUtil.BASE_URL + "addBid.jsp";
        return HttpUtil.postRequest(url, map);
    }
}
