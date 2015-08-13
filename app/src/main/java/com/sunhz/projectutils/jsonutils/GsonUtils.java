/**
 * GSON (https://code.google.com/p/google-gson/)
 *
 * @link https://code.google.com/p/google-gson/
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @package com.google.gson
 */
package com.sunhz.projectutils.jsonutils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

/**
 * 使用Gson,解析
 * Created by Spencer on 15/2/3.
 */
public class GsonUtils {
    private static GsonUtils gsonUtils;
    private Gson gson;

    private GsonUtils() {
        gson = new Gson();
    }

    public synchronized static GsonUtils getInstance() {
        if (gsonUtils == null) {
            gsonUtils = new GsonUtils();

        }
        return gsonUtils;
    }

    /**
     * 对象 转换成 string
     *
     * @param t 待转换对象
     * @return json
     */
    public <T> String objectToJson(T t) {
        return gson.toJson(t);
    }


    /**
     * string 转换成 对象
     *
     * @param json  json内容
     * @param clazz 需要转换成的类型
     * @return object
     */
    public <T> T jsonToObject(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    /**
     * 将json转位list
     *
     * @param json   json
     * @param classT 需要的List的泛型类型 如:List<T> , 就在classT参数处,填写Object.class
     * @param <T>
     * @return List<T>
     */
    public <T> List<T> jsonToObjectList(String json, Class<T> classT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, new ListOfSomething<T>(classT));
    }
}