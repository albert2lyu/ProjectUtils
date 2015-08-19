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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.sunhz.projectutils.jsonutils.GsonUtils;

import java.io.UnsupportedEncodingException;


/**
 * 对volley的request进行了针对gson的封装
 * 可自动根据json结果区分是否返回对象或集合
 * GsonRequest ficusk
 *
 * @author ficusk  Last active on 30 Apr 2013 https://gist.github.com/ficusk/5474673
 * Created by Spencer (www.spencer-dev.com) on 15/2/10.
 */
public class GsonRequest<T> extends Request<T> {

    private final Class<T> clazz;
    private final Response.Listener<T> listener;

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }


    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (json.startsWith("[")) { // 是个集合
                return Response.success((T) GsonUtils.jsonToObjectList(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            } else if (json.startsWith("{")) { // 是个对象
                return Response.success(GsonUtils.jsonToObject(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new VolleyError("json解析出错"));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
}
