# ProjectUtils

这个工程是项目中可能会用到的各种 Utils 集合

是为了方便 Android 开发者,在使用一些日常 utils 的时候,方便寻找和使用.

## 使用说明
```java
    // 在引用 ProjectUtils 的项目中的 BaseApplication 的 onCreate 方法中加入如下代码
    // 这样做的原因是方便开发者在测试 release 版本时
    if (!BuildConfig.DEBUG) { // 正式环境
        CrashHandler.getInstance(mContext).init();
        Logger.init().setLogLevel(LogLevel.NONE);
    } else { // 开发环境
        Logger.init().setMethodCount(3).setLogLevel(LogLevel.FULL);
    }

```

## 问题汇总

>    项目中的 LogUtils 类是封装使用了 orhanobut 的 logger 工具库。
>    logger 这个项目会将 Log 从新排版，并将 log 用表格的方式展示出来。

>    但在使用过程中需要注意，如果使用 ProjectUtils 中的 LogUtils.e(exception)，这是打印出来的，并没有表格。
>    原因：ProjectUtils 中的 LogUtils.e(exception) 里面只是调用了系统的 exception.printStackTrace() 方法，由系统来打印错误信息。

>    这样做的原因：因为用 logger 库中的 logger.e(Throwable throwable, String message, Object... args) 方法打印出来的异常信息，并不会把当前出现异常代码的行号告诉我们，只能告诉我们打印这个异常信息的位置。
>    因此对我们解决问题并没有特别大的帮助，所以将错误一场打印换为 JAVA JDK 中的方式。

>    不过用这个 logger 的好处是，我们在打印普通信息 log 的时候也能够方便的找到打印log的代码位置，并且用漂亮的表格将我们需要的信息打印到控制台上。
>    并且 ProjectUtils 使用 LogUtils 类封装了 logger 后已经对 LogUtils 进行了统一管理。方便我们在 release 和 debug 版本间任意切换。避免了出现忘记了删除 log 语句的问题。

## 目录结构说明
```
- /sdcard
    - android
        - package name (程序包名)
            - files
                - cache (缓存目录)
                    - cacheData (缓存数据)
                    - cacheImage (缓存图片)
                    - cacheOther (缓存其他东西)
                        - log (程序异常崩溃日志)

- /data/data
    - package name (程序包名)
        - cache (缓存目录)
            - cacheData (缓存数据)
            - cacheImage (缓存图片)
            - cacheOther (缓存其他东西)
                - log (程序异常崩溃日志)
```

## 引用到的开源项目说明

其中使用了一些其他的开源项目:

[com.android.support-v4_21](https://developer.android.com/tools/support-library/features.html "com.android.support:support-v4:21.0.0")

[Gson_2.3.1](https://code.google.com/p/google-gson/ "com.google.code.gson:gson:2.3.1")

[Volley_1.0.15](https://developer.android.com/training/volley/index.html "com.mcxiaoke.volley:library:1.0.15")

[logger_1.10](https://github.com/orhanobut/logger "com.orhanobut:logger:1.10")

所以若用到以上功能,上述引用便可不用再次添加.




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