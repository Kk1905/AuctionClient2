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

import com.example.administrator.auctionclient.Adapter.KindArrayAdapter;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.HomeListener;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ChooseKindFragment extends Fragment {
    public static final int CHOOSE_ITEM = 0X1008;
    ListView kindList;
    Callbacks mcallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_kind, container, false);
        Button bnHome = (Button) rootView.findViewById(R.id.bn_home);
        kindList = (ListView) rootView.findViewById(R.id.kindList);
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            KindArrayAdapter adapter = new KindArrayAdapter(getActivity(), jsonArray);
            kindList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        kindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("kindId", l);
                mcallbacks.onItemSelected(CHOOSE_ITEM, bundle);
            }
        });
        return rootView;
    }

    // 当该Fragment被添加、显示到Activity时，回调该方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 如果Activity没有实现Callbacks接口，抛出异常
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "ChooseKindFragment所在的Activity必须实现Callbacks接口!");
        }
        // 把该Activity当成Callbacks对象
        mcallbacks = (Callbacks) activity;
    }

    // 当该Fragment从它所属的Activity中被删除时回调该方法
    @Override
    public void onDetach() {
        super.onDetach();
        // 将mCallbacks赋为null。
        mcallbacks = null;
    }
}
