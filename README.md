# Description
Google Play currently requires our APK file is less than 100MB. If we want publish an app which is more than 100MB, we can use the expansion file mechanism provided by Google Play.
Even Google gives a detailed guidance at http://developer.android.com/intl/zh-cn/google/play/expansion-files.html, it's hard to follow these instructions to get a correct implement of the download process.
There also some bugs in the provided libraries and sample codes.
This library simplifies the download of apk extension files from Google Play, and provides a dialog to show the download process and handle with user interactions.

# Choose appropriate folder
As you see, there are three subfolders in this library.

If you use Eclipse to develope your application, use the source code in Eclipse folder. There are four libraries and one sample project. You should import all four libraries into your own project.

The EclipseAllInOne folder is a more convenient version. The code are identical with the code in Eclipse folder. We just merged all four libraries in Eclipse folder into a single library, so that you can import only one library rather than four.

If you use Android Studio to develope your application, use the source code in AndroidStudio folder. There also have four libraries (usually called modules in Android Studio) and one sample project. You should import obbDownloaderLibrary module. Android Studio would automatically import other three modules for you, so we don't provide AndroidStudioAllInOne folder.

# Features
1. Check whether the obb files are already downloaded.
2. Download obb files if necessary
3. Copy obb files to another directory
4. Unzip obb files to specified directory

# Usage
1. Choose folder and download the souce code.
2. Import the libraries.
2. Reference to the Obb Downloader Library.
3. Use ObbHelper class in the Obb Downloader Library. Also you can find some examples in Obb Downloader Sample. 

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