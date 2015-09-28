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
package com.sunhz.projectutils.softinpututils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

/**
 * 软键盘的工具类
 * Created by Spencer on 9/27/15.
 */
public class SoftInputUtils {

    /**
     * 隐藏软键盘
     *
     * @param editText
     * @return
     */
    public static boolean hidSoftInput(EditText editText) {
        InputMethodManager mInputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean result = mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        return result;
    }

    /**
     * 显示软键盘
     *
     * @param editText
     */
    public static void showSoftInput(EditText editText) {
        // EdText获得焦点
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.requestFocusFromTouch();
        editText.postDelayed(new showSoftInputRunnable(editText), 300);
    }

    private static class showSoftInputRunnable implements Runnable {
        private WeakReference<EditText> editTextWeakReference;

        public showSoftInputRunnable(EditText editText) {
            this.editTextWeakReference = new WeakReference<EditText>(editText);
        }

        public void run() {
            final EditText editText = editTextWeakReference.get();
            if (editText != null) {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }
    }

    /**
     * 键盘切换，显示 or 隐藏
     *
     * @param editText EditTextId
     * @param mContext 上下文
     * @return EditText
     */
    public static EditText toggleSoftInput(EditText editText, Context mContext) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.requestFocusFromTouch();
        // 判断键盘切换
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        else
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); // 强制隐藏键盘
        return editText;
    }
}
