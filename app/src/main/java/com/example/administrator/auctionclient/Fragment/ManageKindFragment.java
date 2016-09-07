package com.example.administrator.auctionclient.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.Adapter.KindArrayAdapter;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ManageKindFragment extends Fragment {
    public static final int ADD_KIND = 0X1007;
    ListView kindList;
    Callbacks mcallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_kind, container, false);
        kindList = (ListView) rootView.findViewById(R.id.kindList);
        Button bnHome = (Button) rootView.findViewById(R.id.bn_home);
        Button bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallbacks.onItemSelected(ADD_KIND, null);
            }
        });
        //定义发送请求的URL
        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        try {
            String result = HttpUtil.getRequest(url);
            JSONArray jsonArray = new JSONArray(result);
            //把JSONArray包装成Adapter(使用KindArrayAdapter）
            KindArrayAdapter adapter = new KindArrayAdapter(getActivity(), jsonArray);
            kindList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "ManageKindFragment所在的Activity必须实现Callbacks接口");
        }
        mcallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mcallbacks = null;
    }
}
