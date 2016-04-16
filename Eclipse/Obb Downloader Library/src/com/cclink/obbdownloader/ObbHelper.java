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
    private ObbDownloadHelper mDownloadHelper;
    private ObbCopyHelper mCopyHelper;
    private ObbUnzipHelper mUnzipHelper;
    
    public ObbHelper(Context context, ObbInfo obbInfo) {
        mContext = context;
        mDownloadHelper = new ObbDownloadHelper(context, obbInfo);
        mCopyHelper = new ObbCopyHelper(context, obbInfo);
        mUnzipHelper = new ObbUnzipHelper(context, obbInfo);
    }
    
    public boolean expansionFilesDelivered() {
    	return mDownloadHelper.expansionFilesDelivered();
    }
    
    public void downloadExpansionFiles(Activity activity, ObbDownloadListener listener) {
    	mDownloadHelper.downloadExpansionFiles(activity, listener);;
    }
    
    public void connect() {
    	mDownloadHelper.connect();
    }
    
    public void disconnect() {
    	mDownloadHelper.disconnect();
    }
    
    public void copyAllToFolder(Context context, String folder, ObbCopyListener listener) {
    	mCopyHelper.copyAllToFolder(mContext, folder, listener);
    }
    
    public void copyMainobbToFolder(Context context, String folder, ObbCopyListener listener) {
    	mCopyHelper.copyMainobbToFolder(mContext, folder, listener);
    }
    
    public void copyPatchobbToFolder(Context context, String folder, ObbCopyListener listener) {
    	mCopyHelper.copyPatchobbToFolder(mContext, folder, listener);
    }
    
    public void unzipAllToFolder(Context context, String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipAllToFolder(mContext, folder, listener);
    }
    
    public void unzipMainobbToFolder(Context context, String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipMainobbToFolder(mContext, folder, listener);
    }
    
    public void unzipPatchobbToFolder(Context context, String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipPatchobbToFolder(mContext, folder, listener);
    }
}
