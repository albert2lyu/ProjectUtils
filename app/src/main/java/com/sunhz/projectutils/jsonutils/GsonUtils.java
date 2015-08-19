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
package com.sunhz.projectutils.jsonutils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

/**
 * 使用Gson,进行基本解析
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public class GsonUtils {

    private GsonUtils() {

    }

    /**
     * 对象 转换成 string
     *
     * @param t   待转换对象
     * @param <T> 泛型
     * @return json
     */
    public static <T> String objectToJson(T t) {
        if (t == null) {
            throw new IllegalArgumentException("t 不能为空");
        }
        Gson gson = new Gson();
        return gson.toJson(t);
    }


    /**
     * string 转换成 对象
     *
     * @param json   json内容
     * @param clazzT 需要转换成的类型
     * @param <T>    泛型
     * @return object
     */
    public static <T> T jsonToObject(String json, Class<T> clazzT) {
        if (TextUtils.isEmpty(json) || clazzT == null) {
            throw new IllegalArgumentException("json 不能为空");
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clazzT);
    }

    /**
     * 将json转为list
     *
     * @param json   json
     * @param classT 需要的List的泛型类型 如:List T , 就在classT参数处,填写Object.class
     * @param <T>    泛型
     * @return List T 泛型集合
     */
    public static <T> List<T> jsonToObjectList(String json, Class<T> classT) {
        if (TextUtils.isEmpty(json) || classT == null) {
            throw new IllegalArgumentException("json 不能为空");
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(classT));
    }
}