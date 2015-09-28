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

import android.os.CountDownTimer;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * 倒计时Button帮助类
 * link //http://blog.csdn.net/zhaokaiqiang1992
 * Created by Spencer (www.spencer-dev.com) on 15/9/14.
 */
public class CountDownButtonHelper extends CountDownTimer {


    // 计时开始和结束的回调接口
    private CountDownButtonHelperListener listener;

    private WeakReference<Button> buttonWeakReference;

    private String defaultString;

    /**
     * @param button        需要显示倒计时的Button
     * @param defaultString 默认显示的字符串
     * @param max           需要进行倒计时的最大值,单位是秒
     * @param interval      倒计时的间隔，单位是秒
     */
    public CountDownButtonHelper(final Button button, final String defaultString, int max, int interval) {

        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        super(max * 1000, interval * 1000 - 10);

        this.buttonWeakReference = new WeakReference<Button>(button);
        this.defaultString = defaultString;

    }

    @Override
    public void onTick(long time) {
        final Button button = buttonWeakReference.get();
        if (button != null) {
            // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
            button.setText(defaultString + "(" + ((time + 15) / 1000) + "秒)");

        }
    }

    @Override
    public void onFinish() {
        final Button button = buttonWeakReference.get();
        if (button != null) {
            button.setEnabled(true);
            button.setText(defaultString);
            if (listener != null) {
                listener.countDownButtonHelperFinish();
            }
        }
    }

    /**
     * 开始倒计时
     */

    public void startTiming() {
        final Button button = buttonWeakReference.get();
        if (button != null) {
            button.setEnabled(false);
            this.start();
            if (listener != null) {
                listener.countDownButtonHelperStart();
            }
        }
    }

    /**
     * 取消倒计时
     */
    public void cancelTiming() {
        final Button button = buttonWeakReference.get();
        if (button != null) {
            button.setEnabled(true);
            this.cancel();
        }
    }


    /**
     * 设置倒计时开始和结束的监听器
     *
     * @param listener
     */
    public void setOnStartAndFinishListener(CountDownButtonHelperListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     *
     * @author zhaokaiqiang
     */
    public interface CountDownButtonHelperListener {
        void countDownButtonHelperStart();

        void countDownButtonHelperFinish();
    }

}
