package com.example.administrator.auctionclient.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.administrator.auctionclient.Adapter.JSONArrayAdapter;
import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */

public class AddItemFragment extends Fragment {
    //定义界面中的控件
    EditText itemName, itemDesc, itemRemark, initPrice;
    Spinner availTime, itemKind;
    Button bnAdd, bnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_item, container, false);
        itemName = (EditText) rootView.findViewById(R.id.itemName);
        itemDesc = (EditText) rootView.findViewById(R.id.itemDesc);
        itemRemark = (EditText) rootView.findViewById(R.id.itemMark);
        initPrice = (EditText) rootView.findViewById(R.id.initPrice);
        availTime = (Spinner) rootView.findViewById(R.id.availTime);
        itemKind = (Spinner) rootView.findViewById(R.id.itemKind);
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(HttpUtil.getRequest(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //访问查看物品种类的网页，返回响应，物品种类，为spinner提供内容
        JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "kindName", false);
        itemKind.setAdapter(adapter);
        bnCancel.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对用户的输入进行检验
                if (validate()) {
                    //获取用户输入的各种信息
                    String name = itemName.getText().toString();
                    String desc = itemDesc.getText().toString();
                    String remark = itemRemark.getText().toString();
                    String price = initPrice.getText().toString();
                    JSONObject kind = (JSONObject) itemKind.getSelectedItem();
                    int avail = availTime.getSelectedItemPosition();
                    //根据用户选择的有效时间选项，设置实际的有效时间
                    switch (avail) {
                        case 5:
                            avail = 7;
                            break;
                        case 6:
                            avail = 30;
                            break;
                        default:
                            avail += 1;
                            break;
                    }
                    try {
                        String result = addItem(name, desc, remark, price, avail, kind.getInt("id"));
                        DialogUtils.showDialog(getActivity(), result, true);
                    } catch (Exception e) {
                        DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    // 对用户输入的物品名、起拍价格进行校验
    private boolean validate() {
        String name = itemName.getText().toString().trim();
        if (name.equals("")) {
            DialogUtils.showDialog(getActivity(), "物品名称是必填项！", false);
            return false;
        }
        String price = initPrice.getText().toString().trim();
        if (price.equals("")) {
            DialogUtils.showDialog(getActivity(), "起拍价格是必填项！", false);
            return false;
        }
        try {
            // 尝试把起拍价格转换为浮点数
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            DialogUtils.showDialog(getActivity(), "起拍价格必须是数值！", false);
            return false;
        }
        return true;
    }

    //实际访问网页的方法
    private String addItem(String name, String desc, String remark, String price, int avail, int kindId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("itemName", name);
        map.put("itemDesc", desc);
        map.put("itemRemark", remark);
        map.put("initPrice", price);
        map.put("availTime", avail + "");
        map.put("kindId", kindId + "");
        String url = HttpUtil.BASE_URL + "addItem.jsp";
        return HttpUtil.postRequest(url, map);
    }
}
