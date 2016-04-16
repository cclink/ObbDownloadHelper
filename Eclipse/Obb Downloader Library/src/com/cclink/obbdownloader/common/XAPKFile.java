package com.cclink.obbdownloader.common;

/**
 * This is a little helper class that demonstrates simple testing of an
 * Expansion APK file delivered by Market. You may not wish to hard-code
 * things such as file lengths into your executable... and you may wish to
 * turn this code off during application development.
 */
public class XAPKFile {
	public final boolean mIsMain;
    public final int mFileVersion;
    public final long mFileSize;

    public XAPKFile(boolean isMain, int fileVersion, long fileSize) {
        mIsMain = isMain;
        mFileVersion = fileVersion;
        mFileSize = fileSize;
    }
}
