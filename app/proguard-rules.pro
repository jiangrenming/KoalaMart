# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#指定代码的压缩（proguard对代码进行迭代优化的次数0-7）等级Android中一般设置为5
-optimizationpasses 5
#包名不使用大小写混合
-dontusemixedcaseclassnames
# 不混淆第三方引用的库
-dontskipnonpubliclibraryclasses
# 不做预校验
-dontpreverify
# 忽略警告
-ignorewarning
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
# 保持Activity类及其子类不被混淆
-keep public class * extends android.app.Activity
# 保持Application类及其不被混淆
-keep public class * extends android.app.Application
# 保持Service类及其子类不被混淆
-keep public class * extends android.app.Service
# 保持BroadcastReceiver类及其不被混淆
-keep public class * extends android.content.BroadcastReceiver
# 保持ContentProvider类及其子类不被混淆
-keep public class * extends android.content.ContentProvider
# 保持BackupAgentHelper类及其子类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper
# 保持Preference类及其子类不被混淆
-keep public class * extends android.preference.Preference
# 保持Fragment类及其子类不被混淆
-keep public class * extends android.app.Fragment
#保持引用v4包下Fragment及其子类不被混淆
-keep public class * extends android.support.v4.app.Fragment
#保持 Serializable类及其实现类不被混淆
-keepnames class * implements java.io.Serializable
# 如果引用了v4或者v7包
-dontwarn android.support.**
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保护注解
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}
# 不混淆内部类
-keepattributes InnerClasses
#不混淆资源类即R文件
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature
# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
   public static **[] values();
   public static ** valueOf(java.lang.String);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持Activity中参数类型为View的所有方法
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持所有bean类（即bean包下所有类）都不被混淆
-keep class android.launcher.bean.**{
    *;
}
#所有View的子类及其子类的get、set方法都不进行混淆
-keep public class * extends android.view.View {
          public <init>(android.content.Context);
          public <init>(android.content.Context, android.util.AttributeSet);
          public <init>(android.content.Context, android.util.AttributeSet, int);
          public void set*(...);
  }
# 保持包含org.json.JSONObject参数的构造方法的类
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
# 有用到WEBView的JS调用接口不被混淆
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
  public *;
}

#基础类和类不被混淆
-keep class com.jrm.retrofitlibrary.retrofit.BaseResponseBean {*;}
-keep class com.koalafield.cmart.base.**{
    *;
}
-keep class com.koalafield.cmart.bean.**{
    *;
}
#网络请求回调不被回调
-keep class com.jrm.retrofitlibrary.callback.**{
    *;
}
-keep class com.jrm.retrofitlibrary.retrofit.RetrofitBuilder {*;}
#保持数据库不被混淆
-keep class com.koalafield.cmart.db.**{
    *;
}

#glide的混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
#-OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
#-Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* { long producerIndex; long consumerIndex; }
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef { rx.internal.util.atomic.LinkedQueueNode producerNode; }
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef { rx.internal.util.atomic.LinkedQueueNode consumerNode; }
 # Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod -keep class org.xz_sale.entity.**{*;}
#eventBus3.0
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#butterKnife8.0
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }
-keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }
 #微信登录
-keep class com.tencent.** { *;}
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

#银联支付
-keep class com.unionpay.**{*;}
-keep class com.UCMobile.PayPlugin.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
