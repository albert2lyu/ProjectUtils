package com.sunhz.projectutils.httputils;


import com.sunhz.projectutils.Constance;
import com.sunhz.projectutils.fileutils.FileUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装了HttpClient对网路的操作
 * Created by Spencer on 15/2/19.
 */
public class HttpClient {

    /**
     * get方式访问网络,需要在子线程中使用
     *
     * @param url
     * @return 响应数据流
     * @throws IOException 在状态码返回非200的情况,便会抛出异常
     */
    public static InputStream getInputStreamInUIThread(String url) throws IOException {
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constance.TimeInApplication.NET_TIMEOUT);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constance.TimeInApplication.NET_TIMEOUT);
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return response.getEntity().getContent();
        } else {
            throw new IllegalArgumentException("getInputStreamInUIThread 返回状态码为" + response.getStatusLine().getStatusCode());
        }
    }


    /**
     * get方式访问网络,需要在子线程中使用
     *
     * @param url
     * @return 响应字符串
     * @throws IOException 在状态码返回非200的情况,或返回数据流为空,便会抛出异常
     */
    public static String getStringInUIThread(String url) throws IOException {
        InputStream is = getInputStreamInUIThread(url);
        if (is != null) {
            return FileUtils.InputStream2String(is);
        } else {
            throw new IllegalArgumentException("getStringInUIThread 返回结果为null");
        }
    }

    /**
     * post方式访问网络,需要在子线程中使用
     *
     * @param url
     * @param params
     * @return 响应数据流
     * @throws IOException 在状态码返回非200的情况,便会抛出异常
     */
    public static InputStream postInputStreamInUIThread(String url, Map<String, String> params) throws IOException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // 解析Map传递的参数，使用一个键值对对象BasicNameValuePair保存。
                list.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constance.TimeInApplication.NET_TIMEOUT);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constance.TimeInApplication.NET_TIMEOUT);
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            return response.getEntity().getContent();
        } else {
            throw new IllegalArgumentException("postInputStreamInUIThread 返回状态码为" + response.getStatusLine().getStatusCode());
        }

    }

    /**
     * post方式访问网络,需要在子线程中使用
     *
     * @param params
     * @param url
     * @return 响应字符串
     * @throws IOException 在状态码返回非200的情况,或返回数据流为空,便会抛出异常
     */
    public static String postStringInUIThread(String url, Map<String, String> params) throws IOException {
        InputStream is = postInputStreamInUIThread(url, params);
        if (is != null) {
            return FileUtils.InputStream2String(is);
        } else {
            throw new IllegalArgumentException("postStringInUIThread 返回结果为null");
        }
    }

}