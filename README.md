# ProjectUtils

这个工程是项目中可能会用到的各种Utils集合

是为了方便Android开发者,在使用一些日常utils的时候,方便寻找和使用.

## 使用说明
```java
    // 在引用ProjectUtils的项目中的BaseApplication的onCreate方法中加入如下代码
    if (!BuildConfig.DEBUG) { // 正式环境
        CrashHandler.getInstance(mContext).init();
        Logger.init().setLogLevel(LogLevel.NONE);
    } else { // 开发环境
        Logger.init().setMethodCount(3).setLogLevel(LogLevel.FULL);
    }

```

## 问题汇总

>    项目中的LogUtils类是封装使用了orhanobut的logger工具库。
>    logger这个项目会将Log从新排版，并将log用表格的方式展示出来。

>    但在使用过程中需要注意，如果使用ProjectUtils中的LogUtils.e(exception)，这是打印出来的，并没有表格。
>    原因：ProjectUtils中的LogUtils.e(exception)里面只是调用了系统的exception.printStackTrace()方法，由系统来打印错误信息。

>    这样做的原因：因为用logger库中的logger.e(Throwable throwable, String message, Object... args)方法打印出来的异常信息，并不会把当前出现异常代码的行号告诉我们，只能告诉我们打印这个异常信息的位置。
>    因此对我们解决问题并没有特别大的帮助，所以将错误一场打印换为JAVA JDK中的方式。

>    不过用这个logger的好处是，我们在打印普通信息log的时候也能够方便的找到打印log的代码位置，并且用漂亮的表格将我们需要的信息打印到控制台上。
>    并且ProjectUtils使用LogUtils类封装了logger后已经对LogUtils进行了统一管理。方便我们在release和debug版本间任意切换。避免了出现忘记了删除log语句的问题。

## 目录结构说明
```
/sdcard
  - package name (程序包名)
      - cache (缓存目录)
          - cacheData (缓存数据)
          - cacheImage (缓存图片)
          - cacheOther (缓存其他东西)
              - log (程序异常崩溃日志)

      - download (下载目录)
          - downloadData (下载数据)
          - downloadImage (下载图片)
          - downloadOther (下载其他东西)
```

## 引用到的开源项目说明

其中使用了一些其他的开源项目:

[com.android.support-v4_21](https://developer.android.com/tools/support-library/features.html "com.android.support:support-v4:21.0.0")

[Gson_2.3.1](https://code.google.com/p/google-gson/ "com.google.code.gson:gson:2.3.1")

[Volley_1.0.15](https://developer.android.com/training/volley/index.html "com.mcxiaoke.volley:library:1.0.15")

[Picasso_2.5.2](http://square.github.io/picasso/ "com.squareup.picasso:picasso:2.5.2")

[logger_1.10](https://github.com/orhanobut/logger "com.orhanobut:logger:1.10")

所以若用到以上功能,上述引用便可不用再次添加.
