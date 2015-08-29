/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunhz.projectutils.httputils;


import com.sunhz.projectutils.Constant;
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
 * Encapsulates the operation of the network HttpClientTool
 * Created by Spencer (www.spencer-dev.com) on 15/2/19.
 */
public class HttpClientUtils {

    /**
     * get access to the network, you need to use in the sub-thread
     *
     * @param url url
     * @return response inputStream
     * @throws IOException In the case of non-200 status code is returned, it would have thrown
     */
    public static InputStream getInputStream(String url) throws IOException {
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constant.TimeInApplication.NET_TIMEOUT);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constant.TimeInApplication.NET_TIMEOUT);
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return response.getEntity().getContent();
        } else {
            throw new IllegalArgumentException("getInputStreamInSubThread response status code is " + response.getStatusLine().getStatusCode());
        }
    }


    /**
     * get access to the network, you need to use in the sub-thread
     *
     * @param url url
     * @return response string
     * @throws IOException In the case of non-200 status code is returned, it would have thrown
     */
    public static String getString(String url) throws IOException {
        InputStream is = getInputStream(url);
        if (is != null) {
            return FileUtils.inputStream2String(is);
        } else {
            throw new IllegalArgumentException("getStringInSubThread response status code is null");
        }
    }

    /**
     * post access to the network, you need to use in the sub-thread
     *
     * @param url    url
     * @param params params
     * @return response inputStream
     * @throws IOException In the case of non-200 status code is returned, it would have thrown
     */
    public static InputStream postInputStream(String url, Map<String, String> params) throws IOException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constant.TimeInApplication.NET_TIMEOUT);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constant.TimeInApplication.NET_TIMEOUT);
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            return response.getEntity().getContent();
        } else {
            throw new IllegalArgumentException("postInputStreamInSubThread response status code is " + response.getStatusLine().getStatusCode());
        }

    }

    /**
     * post access to the network, you need to use in the sub-thread
     *
     * @param url    url
     * @param params params
     * @return response String
     * @throws IOException In the case of non-200 status code is returned, it would have thrown
     */
    public static String postString(String url, Map<String, String> params) throws IOException {
        InputStream is = postInputStream(url, params);
        if (is != null) {
            return FileUtils.inputStream2String(is);
        } else {
            throw new IllegalArgumentException("postStringInSubThread response status code is null");
        }
    }

}