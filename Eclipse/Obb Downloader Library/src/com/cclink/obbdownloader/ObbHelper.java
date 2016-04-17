package com.cclink.obbdownloader;

import com.cclink.obbdownloader.copy.ObbCopyHelper;
import com.cclink.obbdownloader.copy.ObbCopyListener;
import com.cclink.obbdownloader.download.ObbDownloadHelper;
import com.cclink.obbdownloader.download.ObbDownloadListener;
import com.cclink.obbdownloader.unzip.ObbUnzipHelper;
import com.cclink.obbdownloader.unzip.ObbUnzipListener;

import android.app.Activity;
import android.content.Context;

/*
 * This is a wrapper class of ObbDownloadHelper ObbCopyHelper and ObbUnzipHelper
 * Use this class, you can download, unzip or copy the obb files. 
 */
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
    
    // Check whether the expansion files (obb files) have already delivered
    public boolean expansionFilesDelivered() {
    	return getDownloaderHelper().expansionFilesDelivered();
    }
    
    // Download the expansion files
    public void downloadExpansionFiles(Activity activity, final ObbHelperListener listener) {
    	getDownloaderHelper().downloadExpansionFiles(activity, getDownloadListener(listener));
    }
    
    // If you call downloadExpansionFiles in onCreate() method of activity, call connect() in onResume() method.
    // Otherwise, call connect() immediately after downloadExpansionFiles()
    public void connect() {
    	getDownloaderHelper().connect();
    }
    
    // Call disconnect() in onStop() method of activity
    public void disconnect() {
    	getDownloaderHelper().disconnect();
    }
    
    // Copy all obb files to the target folder
    public void copyAllToFolder(String folder, final ObbHelperListener listener) {
    	getCopyHelper().copyAllToFolder(folder, getCopyListener(listener));
    }
    
    // Copy main obb file to the target folder
    public void copyMainobbToFolder(String folder, final ObbHelperListener listener) {
    	getCopyHelper().copyMainobbToFolder(folder, getCopyListener(listener));
    }
    
    // Copy patch obb file to the target folder
    public void copyPatchobbToFolder(String folder, final ObbHelperListener listener) {
    	getCopyHelper().copyPatchobbToFolder(folder, getCopyListener(listener));
    }
    
    // Unzip all obb files to the target folder
    public void unzipAllToFolder(String folder, final ObbHelperListener listener) {
    	getUnzipHelper().unzipAllToFolder(folder, getUnzipListener(listener));
    }
    
    // Unzip main obb file to the target folder
    public void unzipMainobbToFolder(String folder, final ObbHelperListener listener) {
    	getUnzipHelper().unzipMainobbToFolder(folder, getUnzipListener(listener));
    }
    
    // Unzip patch obb file to the target folder
    public void unzipPatchobbToFolder(String folder, final ObbHelperListener listener) {
    	getUnzipHelper().unzipPatchobbToFolder(folder, getUnzipListener(listener));
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
    
    private ObbDownloadListener getDownloadListener(final ObbHelperListener listener) {
    	if (listener != null) {
    		return new ObbDownloadListener() {
    			@Override
    			public void onDownloadFailed() {
    				listener.onFailed();
    			}
    			@Override
    			public void onDownloadComplete() {
    				listener.onSuccess();
    			}
    		};
		} else {
			return null;
		}
    }
    
    private ObbCopyListener getCopyListener(final ObbHelperListener listener) {
    	if (listener != null) {
    		return new ObbCopyListener() {
    			@Override
    			public void onCopyFailed() {
    				listener.onFailed();
    			}
    			@Override
    			public void onCopyComplete() {
    				listener.onSuccess();
    			}
    		};
		} else {
			return null;
		}
    }
    
    private ObbUnzipListener getUnzipListener(final ObbHelperListener listener) {
    	if (listener != null) {
    		return new ObbUnzipListener() {
    			@Override
    			public void onUnzipFailed() {
    				listener.onFailed();
    			}
    			@Override
    			public void onUnzipComplete() {
    				listener.onSuccess();
    			}
    		};
		} else {
			return null;
		}
    }
}
