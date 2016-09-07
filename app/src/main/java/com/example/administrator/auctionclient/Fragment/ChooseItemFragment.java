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

public class ChooseItemFragment extends Fragment {
    public static final int ADD_BID = 0x1009;
    Button bnHome;
    ListView succList;
    Callbacks mcallbacks;
    TextView viewTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_item, container, false);
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        succList = (ListView) rootView.findViewById(R.id.succList);
        viewTitle = (TextView) rootView.findViewById(R.id.view_title);
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        long kindId = getArguments().getLong("kindId");
        String url = HttpUtil.BASE_URL + "itemList.jsp?kindId=" + kindId;
        try {
            JSONArray jsonArray=new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name", true);
            succList.setAdapter(adapter);
        } catch (Exception e) {
            DialogUtils.showDialog(getActivity(), "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        //修改标题
        viewTitle.setText(R.string.item_list);
        succList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                JSONObject jsonObject = (JSONObject) succList.getAdapter().getItem(i);
                try {
                    bundle.putInt("itemId",jsonObject.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mcallbacks.onItemSelected(ADD_BID, bundle);
            }
        });
        return rootView;
    }
    // 当该Fragment被添加、显示到Activity时，回调该方法
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // 如果Activity没有实现Callbacks接口，抛出异常
        if (!(activity instanceof Callbacks))
        {
            throw new IllegalStateException(
                    "ChooseItemFragment所在的Activity必须实现Callbacks接口!");
        }
        // 把该Activity当成Callbacks对象
        mcallbacks = (Callbacks) activity;
    }
    // 当该Fragment从它所属的Activity中被删除时回调该方法
    @Override
    public void onDetach()
    {
        super.onDetach();
        // 将mCallbacks赋为null。
        mcallbacks = null;
    }
}
