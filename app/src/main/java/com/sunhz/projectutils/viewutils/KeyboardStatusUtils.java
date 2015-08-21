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
package com.sunhz.projectutils.viewutils;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * listener Soft keyboard display hidden
 * Reference code address : http://stackoverflow.com/a/26428753/3859747
 * Listening to the monitor and constructors have been modified since their original core logic code.
 * Created by Spencer (www.spencer-dev.com) on 7/19/15.
 */
public class KeyboardStatusUtils {

    KeyboardVisibilityListener visibilityListener;

    boolean keyboardVisible = false;

    /**
     * Create a soft keyboard listener objects
     * @param f need listener fragment
     * @param visibilityListener listener
     */
    public KeyboardStatusUtils(Fragment f, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(f.getView());
    }

    /**
     * Create a soft keyboard listener objects
     * @param a need listener activity object
     * @param visibilityListener listener
     */
    public KeyboardStatusUtils(Activity a, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    /**
     * Create a soft keyboard listener objects
     * @param v need listener view object
     * @param visibilityListener listener
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