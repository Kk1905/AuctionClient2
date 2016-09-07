package com.example.administrator.auctionclient.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */

public class AddKindFragment extends Fragment {
    EditText kindName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_kind, container, false);

        kindName = (EditText) rootView.findViewById(R.id.kindName);
        final EditText kindDesc = (EditText) rootView.findViewById(R.id.kindDesc);
        Button bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        Button bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
        //为取消按钮设置监听器，直接回到home
        bnCancel.setOnClickListener(new HomeListener(getActivity()));
        //为确定按钮设置监听器
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=kindName.getText().toString();
                String desc=kindDesc.getText().toString();
                if (validate()) {
                    try {
                        String result = addKind(name, desc);
                        //使用对话框显示一下
                        DialogUtils.showDialog(getActivity(), result, true);
                    } catch (Exception e) {
                        DialogUtils.showDialog(getActivity(),"服务区响应异常，请稍再试",false);
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    //对输入的种类和描述进行检验
    private boolean validate() {
        String name=kindName.getText().toString().trim();
        if (name.equals("")) {
            DialogUtils.showDialog(getActivity(), "种类名称必填", false);
            return false;
        }
        return true;
    }
    private String addKind(String name, String desc) throws Exception {
        //使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("kindName", name);
        map.put("kindDesc", desc);
        String url = HttpUtil.BASE_URL + "addKind.jsp";
        return HttpUtil.postRequest(url, map);
    }

}
