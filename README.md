# cordova-PgyUpdatePlugin
cordova android升级 -- 蒲公英分发平台升级

先到蒲公英分发平台申请应用的 APPID

##  安装
```java
cordova plugin add https://github.com/zhangjianying/cordova-PgyUpdatePlugin.git --variable PGYER_APPID=XXXXXXXXX
```


## 注意

android 6.0以上请在应用进入的时候动态申请权限

```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <!-- 获取网络状态 -->
    <uses-permission android:name="android.permission.INTERNET" /> <!-- 网络通信-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />  <!-- 获取设备信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> <!-- 获取MAC地址-->
    <uses-permission android:name="android.permission.GET_TASKS" /> <!-- 获取MAC地址-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- 读写sdcard，storage等等 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" /> <!-- 允许程序录制音频 -->
```