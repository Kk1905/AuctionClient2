package com.example.administrator.auctionclient.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.auctionclient.R;
import com.example.administrator.auctionclient.utils.DialogUtils;
import com.example.administrator.auctionclient.utils.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/17.
 */

public class Login extends Activity {
    //定义界面中的两个文本框
    EditText name, pass;
    //定义界面中的两个按钮
    Button bnLogin, bnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name = (EditText) findViewById(R.id.userEditText);
        pass = (EditText) findViewById(R.id.pwdEditText);
        bnLogin = (Button) findViewById(R.id.buLogin);
        bnCancel = (Button) findViewById(R.id.bnCancel);
        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行输入校验
//                if (validate()) {
//                    //如果登录成功
//                    if (loginPro()) {
                        //启动MainActivity
                        Intent intent = new Intent(Login.this, AuctionClientActivity.class);
                        startActivity(intent);
                        finish();
//                    } else {
//                        DialogUtils.showDialog(Login.this, "用户名密码输入错误，请重新输入", false);
//                    }
//                }
            }
        });
    }

    //对用户输入的用户名，密码进行校验的方法
    private boolean validate() {
        String userName = name.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (userName.equals("")) {
            DialogUtils.showDialog(this, "用户名必须填写", false);
            return false;
        }
        if (password.equals("")) {
            DialogUtils.showDialog(this, "密码必须填写", false);
            return false;
        }
        return false;
    }

    //发送请求的方法
    private JSONObject query(String userName, String password) throws Exception {
        Map<String, String> params = new HashMap<>();
        //设置请求参数
        params.put("user", userName);
        params.put("pass", password);
        //设置请求URL
        String url = HttpUtil.BASE_URL + "login.jsp";
        String result = HttpUtil.postRequest(url, params);
        return new JSONObject(result);
    }

    //验证是否登录成功
    private boolean loginPro() {
        String userName = name.getText().toString();
        String pwd = pass.getText().toString();
        JSONObject jsonObject;
        try {
            jsonObject = query(userName, pwd);
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        } catch (Exception e) {
            DialogUtils.showDialog(this, "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        return false;
    }
}
