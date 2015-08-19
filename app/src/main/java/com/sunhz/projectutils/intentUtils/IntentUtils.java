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
package com.sunhz.projectutils.intentUtils;

import android.content.Context;
import android.content.Intent;

/**
 * intentUtils 跳转 activity 的 utils
 * Created by Spencer (www.spencer-dev.com) on 15/2/20.
 */
public class IntentUtils {

    private IntentUtils() {

    }

    /**
     * 快捷跳转 activity
     *
     * @param mContext Context
     * @param clazz    要跳转到的 activity class对象
     */
    public static void startActivity(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity 并携带参数
     *
     * @param mContext    Context
     * @param clazz       要跳转到的 activity class对象
     * @param getArgument 传参 call back
     */
    public static void startActivityCarryArgments(Context mContext, Class clazz, GetArgument getArgument) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getArgument.getArgument(intent);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity,清空 activity 栈
     *
     * @param mContext Context
     * @param clazz    要跳转到的 activity class对象
     */
    public static void startActivityClearTask(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity 并携带参数,清空 activity 栈
     *
     * @param mContext    Context
     * @param clazz       要跳转到的 activity class对象
     * @param getArgument 传参 call back
     */
    public static void startActivityClearTaskCarryArgments(Context mContext, Class clazz, GetArgument getArgument) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getArgument.getArgument(intent);
        mContext.startActivity(intent);
    }

    public interface GetArgument {
        void getArgument(Intent intent);
    }

}
