# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\ccei\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
#
# Add any project specific keep options here:
#
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#

-verbose
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-keep class com.google.android.gms.** { *; }
 -dontwarn com.google.android.gms.**

 -keep class com.google.vrtoolkit.cardboard.** { *; }
  -dontwarn com.google.vrtoolkit.cardboard.**

   -keep class com.google.vrtoolkit.cardboard.** { *; }
    -dontwarn com.google.vrtoolkit.cardboard.**

       -keep class com.forroom.suhyemin.kimbogyun.songmin.** { *; }
        -dontwarn com.forroom.suhyemin.kimbogyun.songmin.**
        -keepattributes InnerClasses
        -dontoptimize
        -optimizations optimization_filter