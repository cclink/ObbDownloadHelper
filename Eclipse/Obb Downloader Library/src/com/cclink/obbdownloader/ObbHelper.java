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
    
    public void copyAllToFolder(String folder, ObbCopyListener listener) {
    	mCopyHelper.copyAllToFolder(folder, listener);
    }
    
    public void copyMainobbToFolder(String folder, ObbCopyListener listener) {
    	mCopyHelper.copyMainobbToFolder(folder, listener);
    }
    
    public void copyPatchobbToFolder(String folder, ObbCopyListener listener) {
    	mCopyHelper.copyPatchobbToFolder(folder, listener);
    }
    
    public void unzipAllToFolder(String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipAllToFolder(folder, listener);
    }
    
    public void unzipMainobbToFolder(String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipMainobbToFolder(folder, listener);
    }
    
    public void unzipPatchobbToFolder(String folder, ObbUnzipListener listener) {
    	mUnzipHelper.unzipPatchobbToFolder(folder, listener);
    }
}
