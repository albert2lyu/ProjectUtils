package com.sunhz.projectutils.fixmemoryleakutils;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 解决 android.sec.clipboard.ClipboardUIManager 内存泄露问题
 * 此问题应该只在三星手机上出现, 属于三星系统的一个 bug
 *
 * 原因 : ClipboardUIManager getInstance 的方式有问题. 源码如下
 * public static ClipboardUIManager getInstance(Context context) {
 *     if (sInstance == null)
 *         sInstance = new ClipboardUIManager(context);
 *     return sInstance;
 * }
 *
 * 获取ClipboardUIManager对象传的Context实际上就是当前的Activity，这样 activity finish 后就一直被引用没法释放了
 *
 * 解决办法 : 在 Application 中先通过反射调用 getInstance , 让其引用 applicationContext.
 * Created by Spencer on 9/23/15.
 */
public class FixClipboardUIManagerLeak {

    public static void fixClipboardUIManagerLeak(Application application) {
        try {
            Class cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
            Method m = cls.getDeclaredMethod("getInstance", Context.class);
            m.setAccessible(true);
            m.invoke(null, application);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
