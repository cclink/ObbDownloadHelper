package com.cclink.obbdownloader;

import com.google.android.vending.expansion.downloader.Helpers;

import android.content.Context;

public class XAPKsHelper {

	public static XAPKFile[] getMainXAPKs(XAPKFile[] xAPKS) {
		for (XAPKFile xf : xAPKS) {
			if (xf.mIsMain) {
				XAPKFile[] xfs = new XAPKFile[1];
				xfs[0] = xf;
				return xfs;
			}
		}
		XAPKFile[] xfs = new XAPKFile[0];
		return xfs;
	}
	
	public static XAPKFile[] getPatchXAPKs(XAPKFile[] xAPKS) {
		for (XAPKFile xf : xAPKS) {
			if (!xf.mIsMain) {
				XAPKFile[] xfs = new XAPKFile[1];
				xfs[0] = xf;
				return xfs;
			}
		}
		XAPKFile[] xfs = new XAPKFile[0];
		return xfs;
	}
	
	public static boolean checkXAPKs(Context context, XAPKFile[] xfs) {
		if (xfs.length == 0) {
    		return false;
		}
    	for (XAPKFile xf : xfs) {
            String fileName = Helpers.getExpansionAPKFileName(context, xf.mIsMain, xf.mFileVersion);
            if (!Helpers.doesFileExist(context, fileName, xf.mFileSize, false)) {
                return false;
            }
        }
    	return true;
	}
	
}
