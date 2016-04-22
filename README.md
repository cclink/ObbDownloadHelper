# Description
Google Play currently requires our APK file is less than 100MB. If we want publish an app which is more than 100MB, we can use the expansion file mechanism provided by Google Play.
Even Google gives a detailed guidance at http://developer.android.com/intl/zh-cn/google/play/expansion-files.html, it's hard to follow these instructions to get a correct implement of the download process.
This library simplify the download of apk extension files from Google Play.

# Choose appropriate folder
As you see, there are two subfolders in this library.

If you use Eclipse to develope your application, Use the source code in Eclipse folder.

If you use Android Studio to develope your application, Use the source code in Android Studio folder.

# Usage
1. Choose folder and download the souce code.
2. Reference to the Obb Downloader Library in Eclipse or Android Studio.
3. Use ObbHelper class in the Obb Downloader Library. You can find examples in Obb Downloader Sample Library. 

# Bug Fix
There also some bugs in Google Play Licensing Library and Google Play APK Expansion Library. We have fixed these bugs in the repository. So the downloaded code of Google Play Licensing Library and Google Play APK Expansion Library here may be different than the original code in the Android SDK.

## Fix for Google Play Licensing Library

### Crash in Android 5.0
Class: com.google.android.vending.licensing.LicenseChecker

Method: public synchronized void checkAccess(LicenseCheckerCallback callback)

Changes: See the comments in this class.

## Fix for Google Play APK Expansion Library

### Service is not connectted
Class: com.google.android.vending.expansion.downloader.DownloaderClientMarshaller

Method: public void connect(Context c)

Changes: Change Context.BIND_DEBUG_UNBIND to Context.BIND_AUTO_CREATE when bindService