package com.example.administrator.auctionclient.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.auctionclient.Fragment.AddBidFragment;
import com.example.administrator.auctionclient.Fragment.AddItemFragment;
import com.example.administrator.auctionclient.Fragment.AddKindFragment;
import com.example.administrator.auctionclient.Fragment.AuctionListFragment;
import com.example.administrator.auctionclient.Callbacks;
import com.example.administrator.auctionclient.Fragment.ChooseItemFragment;
import com.example.administrator.auctionclient.Fragment.ChooseKindFragment;
import com.example.administrator.auctionclient.Fragment.ManageItemFragment;
import com.example.administrator.auctionclient.Fragment.ManageKindFragment;
import com.example.administrator.auctionclient.Fragment.ViewBidFragment;
import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.Fragment.ViewItemFragment;

/**
 * Created by Administrator on 2016/8/18.
 */

public class AuctionClientActivity extends Activity implements Callbacks {
    //定义一个旗标，用于标识该应用是否支持大屏幕
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//会自动根据实际屏幕尺寸加载不同的activity_main布局文件
        if (findViewById(R.id.auction_detail_container) != null) {
            //如果此布局文中可以取出FrameLayout组件，则表示我们是在用大屏幕
            twoPane = true;
            //取出列表项的fragment
            ((AuctionListFragment) getFragmentManager().findFragmentById(R.id.auction_list)).setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        if (twoPane) {
            Fragment fragment = null;
            switch ((int) id) {
                case 0:
                    //查看竞得物品
                    fragment = new ViewItemFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("action", "viewSucc.jsp");
                    fragment.setArguments(arguments);
                    break;
                case 1:
                    //浏览流拍物品
                    fragment = new ViewItemFragment();
                    Bundle arguments2 = new Bundle();
                    arguments2.putString("action", "viewFail.jsp");
                    fragment.setArguments(arguments2);
                    break;
                case 2:
                    //管理物品种类
                    fragment = new ManageKindFragment();
                    break;
                case 3:
                    //管理物品
                    fragment = new ManageItemFragment();
                    break;
                case 4:
                    //浏览拍卖物品(选择物品种类）
                    fragment = new ChooseKindFragment();
                    break;
                case 5:
                    //查看自己的竞标
                    fragment = new ViewBidFragment();
                    break;
                case ManageItemFragment.ADD_ITEM:
                    //添加物品
                    fragment = new AddItemFragment();
                    break;
                case ManageKindFragment.ADD_KIND:
                    //添加物品种类
                    fragment = new AddKindFragment();
                    break;
                case ChooseKindFragment.CHOOSE_ITEM:
                    fragment = new ChooseItemFragment();
                    Bundle arg1 = new Bundle();
                    arg1.putLong("kindId", bundle.getLong("kindId"));
                    fragment.setArguments(arg1);
                    break;
                case ChooseItemFragment.ADD_BID:
                    fragment = new AddBidFragment();
                    Bundle arg2 = new Bundle();
                    arg2.putInt("itemId", bundle.getInt("itemId"));
                    fragment.setArguments(arg2);
                    break;
            }
            //使用事务将framelayout替换成对应的fragment
            getFragmentManager().beginTransaction().replace(R.id.auction_detail_container, fragment)
                    .addToBackStack(null).commit();
        } else {
            Intent intent = null;
            switch ((int) id) {
                case 0:
                    //查看竞得物品
                    intent = new Intent(this, ViewItemActivity.class);
                    intent.putExtra("action", "viewSucc.jsp");
                    startActivity(intent);
                    break;
                case 1:
                    //浏览流拍物品
                    intent = new Intent(this, ViewItemActivity.class);
                    intent.putExtra("action", "viewFail.jsp");
                    startActivity(intent);
                    break;
                case 2:
                    //管理物品种类
                    intent = new Intent(this, ManageKindActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    //管理物品
                    intent = new Intent(this, ManageItemActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    //浏览拍卖物品（选择物品种类）
                    intent = new Intent(this, ChooseKindActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    //查看自己的竞标
                    intent = new Intent(this, ViewBidActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
