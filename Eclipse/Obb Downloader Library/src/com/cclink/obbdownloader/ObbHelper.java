package com.cclink.obbdownloader;

import com.cclink.obbdownloader.copy.ObbCopyHelper;
import com.cclink.obbdownloader.copy.ObbCopyListener;
import com.cclink.obbdownloader.download.ObbDownloadHelper;
import com.cclink.obbdownloader.download.ObbDownloadListener;
import com.cclink.obbdownloader.unzip.ObbUnzipHelper;
import com.cclink.obbdownloader.unzip.ObbUnzipListener;

import android.app.Activity;
import android.content.Context;

public class ObbHelper {
	
	private Context mContext;
	private ObbInfo mObbInfo;
    private ObbDownloadHelper mDownloadHelper;
    private ObbCopyHelper mCopyHelper;
    private ObbUnzipHelper mUnzipHelper;
    
    public ObbHelper(Context context, ObbInfo obbInfo) {
        mContext = context;
        mObbInfo = obbInfo;
    }
    
    public boolean expansionFilesDelivered() {
    	return getDownloaderHelper().expansionFilesDelivered();
    }
    
    public void downloadExpansionFiles(Activity activity, ObbDownloadListener listener) {
    	getDownloaderHelper().downloadExpansionFiles(activity, listener);;
    }
    
    public void connect() {
    	getDownloaderHelper().connect();
    }
    
    public void disconnect() {
    	getDownloaderHelper().disconnect();
    }
    
    public void copyAllToFolder(String folder, ObbCopyListener listener) {
    	getCopyHelper().copyAllToFolder(folder, listener);
    }
    
    public void copyMainobbToFolder(String folder, ObbCopyListener listener) {
    	getCopyHelper().copyMainobbToFolder(folder, listener);
    }
    
    public void copyPatchobbToFolder(String folder, ObbCopyListener listener) {
    	getCopyHelper().copyPatchobbToFolder(folder, listener);
    }
    
    public void unzipAllToFolder(String folder, ObbUnzipListener listener) {
    	getUnzipHelper().unzipAllToFolder(folder, listener);
    }
    
    public void unzipMainobbToFolder(String folder, ObbUnzipListener listener) {
    	getUnzipHelper().unzipMainobbToFolder(folder, listener);
    }
    
    public void unzipPatchobbToFolder(String folder, ObbUnzipListener listener) {
    	getUnzipHelper().unzipPatchobbToFolder(folder, listener);
    }
    
    private ObbDownloadHelper getDownloaderHelper() {
    	if (mDownloadHelper == null) {
    		mDownloadHelper = new ObbDownloadHelper(mContext, mObbInfo);
		}
    	return mDownloadHelper;
    }
    
    private ObbCopyHelper getCopyHelper() {
    	if (mCopyHelper == null) {
    		mCopyHelper = new ObbCopyHelper(mContext, mObbInfo);
		}
    	return mCopyHelper;
    }
    
    private ObbUnzipHelper getUnzipHelper() {
    	if (mUnzipHelper == null) {
    		mUnzipHelper = new ObbUnzipHelper(mContext, mObbInfo);
		}
    	return mUnzipHelper;
    }
}
