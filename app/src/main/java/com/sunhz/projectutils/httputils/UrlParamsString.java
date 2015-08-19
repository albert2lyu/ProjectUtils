/**
 * Copyright (c) 2015, Spencer 给立乐 (www.spencer-dev.com).
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


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * http get request 时的url参数
 * Created by Spencer (www.spencer-dev.com) on 15/3/10.
 */
public class UrlParamsString {


    /**
     * Demo
     * UrlParamsString urlParamsString = new UrlParamsString("keyTest1","valueTest1");
     * urlParamsString.toString(); //result = "keyTest1=valueTest1"
     * urlParamsString.add("keyTest2","valueTest2");
     * urlParamsString.toString(); //result = "keyTest1=valueTest1&keyTest2=valueTest2"
     */


    private StringBuffer query = new StringBuffer();

    public UrlParamsString() {

    }

    public UrlParamsString(String name, String value) {
        encode(name, value);
    }

    public synchronized void add(String name, String value) {
        query.append('&');
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public String getQuery() {
        return query.toString();
    }

    public String toString() {
        return getQuery();
    }
}
