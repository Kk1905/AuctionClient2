package com.example.administrator.auctionclient.utils;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * Created by Administrator on 2016/8/16.
 */

public class HttpUtil {
    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://169.254.2.48:8080/auction/android/";

    public static String getRequest(final String url) throws Exception {
        FutureTask<String> task=new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //创建HttpGet对象
                HttpGet httpGet = new HttpGet(url);
                //发送get请求，并返回response对象
                HttpResponse response = httpClient.execute(httpGet);
                //如果服务器成功返回结果
                if (response.getStatusLine().getStatusCode() == 200) {
                    String result = EntityUtils.toString(response.getEntity());
                    return result;
                }
                return null;
            }
        });
        new Thread(task).start();
        return task.get();
    }

    public static String postRequest(final String url, final Map<String ,String> rawParams) throws Exception {
        FutureTask<String > task=new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //创建HttpPost对象
                HttpPost httpPost = new HttpPost(url);
                //设置过多参数，可对参数进行封装
                List<NameValuePair> params = new ArrayList<>();
                for (String key : rawParams.keySet()) {
                    params.add(new BasicNameValuePair(key, rawParams.get(key)));

                }
                //对post请求设置参数
                httpPost.setEntity(new UrlEncodedFormEntity(params, "gbk"));
                //发送post请求，并返回response对象
                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String resullt = EntityUtils.toString(response.getEntity());
                    return resullt;
                }
                return null;
            }

        });
        new Thread(task).start();
        return task.get();
    }
}

