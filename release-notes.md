#ProjectUtils

## 最新版本 : version 0.1


## release 0.1 log
* Base：提供 BaseActivity，BaseFragment，BaseApplication，Base。 以方便管理整个项目的 activity 和 fragment。

* AsyncTask：提供 AsyncTaskExpand 。以简化对 AsyncTask 的串行并行管理操作。

* Cache：提供 CacheType 、CacheUtils。以简化对 Cache 类型的管理，以及对 Cache 的读取写入操作。

* Device：提供 DeviceUtils。以简化对 Device Info  的获取。

* Encryption：提供 AESUtils、Base64Utils、MD5EncryptionUtils。以简化对 AES、Base64、MD5 的加解密操作。

* File：提供 FileUtils、SDCardUtils、SharePreferenceUtils。
	* FileUtils 用来简化对文件的读取写入和获取文件信息时的操作。
	* SDCardUtils 用来简化对 SDCard 相关操作，如判断是否存在，是否可写等。
	* SharePreferenceUtils 用来简化对 SharePreference 的日常操作需求。

* FirstStart：提供 FirstStartUtils 。以简化对应用的第一次启动的各种判断。

* Http：提供 DownloadImageFromUrl、GsonRequest、HttpClientUtils、UrlParamsString。
	* DownloadImageFromUrl 用来简化对单张图片的下载操作。
	* GsonRequest 对请求的结果进行了封装，将结果以泛型对象形式返回，以方便调用者使用。
	* HttpClientUtils 将常用的正常请求封装起来，以简化我们对正常请求使用的需求，此工具类的方法，需要在子线程中使用。
	* UrlParamsString 是对 Http 请求中的 get 请求的参数进行封装，以此来简化平时使用时对参数的字符串拼装。

* Image：提供 ImageUtils。用来简化对图片的操作，如压缩图片，bitmap 转 drawable，drawable 转 bitmap 等常用操作。

* Intent：提供 IntentUtils。用来简化平时对 activity 时所需要操作，并提供了传参以及不传参的跳转方式。

* Json：提供了 GsonUtils。用来简化对 json 转 obj 的操作。并且提供了方便的 json 转 obj 、json 转 list、obj 转 json 的操作。

* Log：提供了 LogUtils。以方便对 log 的管理，异常本地保存，以及对 log 的展示美化等。

* Media：提供了 MediaUtils。以方便将图片、视频、音频插入以及删除系统媒体库。

* NetWork：提供了 CheckInternetUtils、NetAuthorityEnum。以方便对网络的检查和网络类型的判断。

* Package：提供了 PackageUtils。以方便对软件的安装以及卸载等其他操作的调用。

* Toast：提供了 ToastUtils。提供了在子线程，主线程中显示 Toast 的便捷操作。

* View：提供了 DensityUtils、KeyboardStatusUtils、StatusBarUtils。
	* DensityUtils：用来获取屏幕信息，如分辨率，长宽，px 转 dp，dp 转 px，sp 转 px，px 转 dp 等操作。
	* KeyboardStatusUtils：用来监听软键盘是否弹出、收回，并提供相应 callBack 以方便使用。
	* StatusBarUtils：用来获取状态栏高度，选择是否隐藏以及显示状态栏的便捷操作。
	* CountDownButtonHelper：快速完成 button 的倒计时操作。

* ActivityManager：提供管理 activity 以及 closeAll activity 的便捷方法。

* Constant：存放常量，使用方法请查看 [项目说明](https://github.com/ChinaSunHZ/ProjectUtils/blob/master/README.md)

* CrashHandler：提供了将报错信息保存下来的方法。

* SoftInputUtils：提供了软键盘的开关方法。

* FixInputMethodManagerLeak：修复某些手机的 InputMethodManager 造成的内存泄露问题。

* FixClipboardUIManagerLeak：修复某些手机的 ClipboardUIManager 造成的内存泄漏问题。

* BrightnessUtils：快速设置、还原屏幕亮度。