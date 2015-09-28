# ProjectUtils

这个工程是项目中可能会用到的各种 Utils 集合

是为了方便 Android 开发者,在使用一些日常 utils 的时候,方便寻找和使用.


## 使用方法
* 关于项目中的 application
    * 如果项目中并未自己实现 Application ，则将 AndroidManifest.xml 中的 application 节点中的 android:name="" 写为 android:name="com.sunhz.projectutils.base.BaseApplication"
    * 如果项目中已经创建了自己的 application 但未继承其他 SDK 中 application，则直接将 com.sunhz.projectutils.base.BaseApplication 写为自己的 application 的父类。
    * 在 com.sunhz.projectutils.base.BaseApplication 中的 onCreate 方法中，调用了用来初始化 Constant 的方法。具体说明请看下面。

* 关于 com.sunhz.projectutils.Constant 的说明
    * Constant 类中有三个值。
        * Encryption.AES_UTILS_CLIENT_KEY 这个值是使用 AES 加解密工具时的 key 。如果需要使用该功能，则需要填写十六位 key，若不需要使用，则不需要填写，默认为 ""。
        * TimeInApplication.NET_TIMEOUT 这个值是网络请求超时时间，网络请求超时分为两种，一个是请求超时，一个是响应超时，两种请求超时的时间在这里设置成同样的。默认为 10s。
        * TimeInApplication.CACHE_FAIL_TIME 这个值是缓存到本地的数据失效时间，默认为一天。
    * 设置方式有两种。1，继承com.sunhz.projectutils.base.BaseApplication。2，手动在自己的 application 中调用 Constant.initConstant(aes key, net time out, cache fail time) 或者 Constant.initNetTimeOutAndCacheFailTime（net time out, cache fail time) 方法。
    * 若不继承 com.sunhz.projectutils.base.BaseApplication 或不在自己的 application 中调用 Constant.initConstant(aes key, net time out, cache fail time) 或者 Constant.initNetTimeOutAndCacheFailTime（net time out, cache fail time) 方法，则以默认值为准。

* 关于项目中的 cache 功能
    * 如果需要使用缓存功能，则需要在 application 中的 onCreate 方法中，调用 CacheUtils.getInstance(Context).init()。

* 关于判断第一次启动 app 的工具类
    * 如果需要是用判断 app 是否是第一次启动，则需要在 application 中的 onCreate 方法中，调用  FirstStartUtils.init(Context)。

* 关于开启记录软件崩溃日志功能
    * 如果需要使用记录崩溃日志功能，则需要在 application 中的 onCreate 方法中，调用 CrashHandler.getInstance(Context).init()。

* 关于使用 logUtils 的工具类
    * 如果项目需要使用 logUtils，则需要在 application 中的 onCreate 方法中，调用 Logger.init().setMethodCount(3).setLogLevel(LogLevel.FULL)。
    * 如果暂时需要关闭，则覆盖上面的代码，在 application 中的 onCreate 方法中，调用 Logger.init().setLogLevel(LogLevel.NONE)。

### 使用方法整合版
```java

    // 开启缓存功能
    CacheUtils.getInstance(mContext).init();

    // 开启记录 app 第一次打开功能
    FirstStartUtils.init(Context);

    // 设置项目的 AES 加密方式的 key，设置网络超时时间（请求超时，响应超时为同一时间），设置缓存失效时间
    Constant.initConstant("xxxxxxxxxxxxxxxx", 10000, BaseApplication.A_DAY);

    // 如果项目不需要使用 AES 加密工具，就不需要设置 AES 加密方式的 key，可以忽略上面方法， 直接调用下面方法。
    Constant.initNetTimeOutAndCacheFailTime(1000,BaseApplication.A_DAY);

    // Fix ClipboardUIManager memory leak
    FixClipboardUIManagerLeak.fixClipboardUIManagerLeak(this);

    if (!BuildConfig.DEBUG) { // 正式环境
        // 开启记录崩溃日志功能
        CrashHandler.getInstance(mContext).init();

        // 关闭 logUtils
        Logger.init().setLogLevel(LogLevel.NONE);
    } else { // 开发环境
        // 开启 logUtils
        Logger.init().setMethodCount(3).setLogLevel(LogLevel.FULL);
    }


```

## 目录结构说明
### 缓存目录结构说明
```
// 有SD卡, 并可以使用的情况
- /sdcard
    - android
        - package name (程序包名)
            - files
                - cache (缓存目录)
                    - cacheData (缓存数据)
                    - cacheImage (缓存图片)
                    - cacheOther (缓存其他东西)
                        - log (程序异常崩溃日志)

// 无SD卡,或SD卡不可使用的情况
- /data/data
    - package name (程序包名)
        - cache (缓存目录)
            - cacheData (缓存数据)
            - cacheImage (缓存图片)
            - cacheOther (缓存其他东西)
                - log (程序异常崩溃日志)
```

## 问题汇总

>    项目中的 LogUtils 类是封装使用了 orhanobut 的 logger 工具库。
>    logger 这个项目会将 Log 从新排版，并将 log 用表格的方式展示出来。

>    但在使用过程中需要注意，如果使用 ProjectUtils 中的 LogUtils.e(exception)，这是打印出来的，并没有表格。
>    现象原因：ProjectUtils 中的 LogUtils.e(exception) 里面只是调用了系统的 exception.printStackTrace() 方法，由系统来打印错误信息。

>    背景原因：因为用 logger 库中的 logger.e(Throwable throwable, String message, Object... args) 方法打印出来的异常信息，并不会把当前出现异常代码的行号告诉我们，只能告诉我们打印这个异常信息的位置。
>    因此对我们解决问题并没有特别大的帮助，所以将错误一场打印换为 JAVA JDK 中的方式。

>    不过用这个 logger 的好处是，我们在打印普通信息 log 的时候也能够方便的找到打印log的代码位置，并且用漂亮的表格将我们需要的信息打印到控制台上。
>    并且 ProjectUtils 使用 LogUtils 类封装了 logger 后已经对 LogUtils 进行了统一管理。方便我们在 release 和 debug 版本间任意切换。避免了出现忘记了删除 log 语句的问题。


## 引用到的开源项目说明

其中使用了一些其他的开源项目：

[com.android.support-v4_21](https://developer.android.com/tools/support-library/features.html "com.android.support:support-v4:21.0.0")

[Gson_2.3.1](https://code.google.com/p/google-gson/ "com.google.code.gson:gson:2.3.1")

[Volley_1.0.15](https://developer.android.com/training/volley/index.html "com.mcxiaoke.volley:library:1.0.15")

[logger_1.10](https://github.com/orhanobut/logger "com.orhanobut:logger:1.10")

所以若用到以上功能，上述引用便可不用再次添加。




###License

```
Copyright (c) 2015 , 给立乐 Spencer ChinaSunHZ (www.spencer-dev.com).

Licensed under the Apache License, Version 2.0 (the "License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
