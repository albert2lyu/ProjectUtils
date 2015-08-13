package com.sunhz.projectutils.viewutils;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘监听显示隐藏
 * <p/>
 * 参考代码地址:http://stackoverflow.com/a/26428753/3859747
 * 对监听监听和构造函数上做了修改,核心逻辑既然使用其原始代码.
 * Created by Spencer on 7/19/15.
 */
public class KeyboardStatusUtils {

    KeyboardVisibilityListener visibilityListener;

    boolean keyboardVisible = false;

    public KeyboardStatusUtils(Fragment f, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(f.getView());
    }

    public KeyboardStatusUtils(Activity a, KeyboardVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

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