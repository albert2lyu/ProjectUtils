# ProjectUtils

这个工程是项目中可能会用到的各种Utils集合

是为了方便Android开发者,在使用一些日常utils的时候,方便寻找和使用.

其中使用了一些其他的开源项目:

[com.android.support-v4](https://developer.android.com/tools/support-library/features.html "com.android.support:support-v4:21.0.0")

[Gson](https://code.google.com/p/google-gson/ "com.google.code.gson:gson:2.3.1")

[Volley](https://developer.android.com/training/volley/index.html "com.mcxiaoke.volley:library:1.0.15")

[Picasso](http://square.github.io/picasso/ "com.squareup.picasso:picasso:2.5.2")

[logger](https://github.com/orhanobut/logger "com.orhanobut:logger:1.8")
	


所以若用到以上功能,上述引用便可不用再次添加.

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

```java
    // 在引用ProjectUtils的项目中的BaseApplication的onCreate方法中加入如下代码
    if (!BuildConfig.DEBUG) { // 正式环境
        CrashHandler.getInstance(mContext).init();
        Logger.init().setLogLevel(LogLevel.NONE);
    } else { // 开发环境
        Logger.init().setLogLevel(LogLevel.FULL);
    }
```