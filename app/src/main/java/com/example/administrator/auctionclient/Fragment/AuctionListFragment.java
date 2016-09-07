package com.example.administrator.auctionclient.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.R;

/**
 * Created by Administrator on 2016/8/18.
 */

public class AuctionListFragment extends Fragment {
    private Callbacks mcallbacks;
    ListView listView;

    @Override
    //重写该方法，该方法返回的view组件将作为fragment显示的组件
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载layout下的auction_list布局文件,该布局文件主要就是提供listView
        View rootView = inflater.inflate(R.layout.auction_list, container, false);
        //为listView的子项设置点击事件监听器
        listView = (ListView) rootView.findViewById(R.id.auction_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //具体的逻辑执行的就是Callbacks接口的逻辑
                mcallbacks.onItemSelected(i, null);
            }
        });
        return rootView;
    }

    //当该Fragment被添加和显示到Activity上时，回调该方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //如果该Activity没有实现Callbacks接口，则要报异常
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "AuctionListFragment所在的Activity必须实现Callbacks接口"
            );
        }
        //把该Activity当成Callbacks对象
        mcallbacks= (Callbacks) activity;
    }


    //当该Fragment从他所属的Activity中删除时回调该方法
    @Override
    public void onDetach() {
        super.onDetach();
        mcallbacks=null;
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        listView.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }
}
