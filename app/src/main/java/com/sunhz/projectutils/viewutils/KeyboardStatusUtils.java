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
package com.sunhz.projectutils.viewutils;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘监听显示隐藏
 * 参考代码地址:http://stackoverflow.com/a/26428753/3859747
 * 对监听监听和构造函数上做了修改,核心逻辑既然使用其原始代码.
 * Created by Spencer (www.spencer-dev.com) on 7/19/15.
 */
public class KeyboardStatusUtils {

    KeyboardVisibilityListener visibilityListener;

    boolean keyboardVisible = false;

    /**
     * 创建软键盘监听对象
     * @param f 需要被监听的 fragment
     * @param visibilityListener 监听器
     */
    public KeyboardStatusUtils(Fragment f, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(f.getView());
    }

    /**
     * 创建软键盘监听对象
     * @param a 需要被监听的 activity
     * @param visibilityListener 监听器
     */
    public KeyboardStatusUtils(Activity a, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    /**
     * 创建软键盘监听对象
     * @param v 需要被监听的 view 对象
     * @param visibilityListener 监听器
     */
    public KeyboardStatusUtils(View v, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(v);
    }


    private KeyboardStatusUtils registerView(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    /** Check this variable to debounce layout events */
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (visibilityListener != null)
                            visibilityListener.keyBoardShow();
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (visibilityListener != null)
                            visibilityListener.keyBoardHide();
                    }
                }
            }
        });

        return this;
    }


    public interface KeyboardVisibilityListener {
        void keyBoardHide();

        void keyBoardShow();
    }
}