package com.cclink.obbdownloader;

import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Messenger;
import android.util.Log;
import android.view.WindowManager;

public class ObbDownloadHelper implements IDownloaderClient {

	/**
     * This is a little helper class that demonstrates simple testing of an
     * Expansion APK file delivered by Market. You may not wish to hard-code
     * things such as file lengths into your executable... and you may wish to
     * turn this code off during application development.
     */
    private static class XAPKFile {
        public final boolean mIsMain;
        public final int mFileVersion;
        public final long mFileSize;

        XAPKFile(boolean isMain, int fileVersion, long fileSize) {
            mIsMain = isMain;
            mFileVersion = fileVersion;
            mFileSize = fileSize;
        }
    }

    private Context mContext;
    private onDownloadStateChanged mListener;
    private IStub mDownloaderClientStub;
    private IDownloaderService mRemoteService;
    private MyProgressDialog mDownloadProgressDlg;
    private XAPKFile[] xAPKS;

    @SuppressWarnings("unused")
	public ObbDownloadHelper(Context context) {
		mContext = context;
        if (ObbInfo.MAIN_EXPANSION_FILE_VERSION > 0 && ObbInfo.MAIN_EXPANSION_FILE_SIZE > 0) {
            if (ObbInfo.PATCH_EXPANSION_FILE_VERSION > 0 && ObbInfo.PATCH_EXPANSION_FILE_SIZE > 0) {
                xAPKS = new XAPKFile[2];
                xAPKS[0] = new XAPKFile(true, ObbInfo.MAIN_EXPANSION_FILE_VERSION, ObbInfo.MAIN_EXPANSION_FILE_SIZE);
                xAPKS[1] = new XAPKFile(true, ObbInfo.PATCH_EXPANSION_FILE_VERSION, ObbInfo.PATCH_EXPANSION_FILE_SIZE);
            } else {
                xAPKS = new XAPKFile[1];
                xAPKS[0] = new XAPKFile(true, ObbInfo.MAIN_EXPANSION_FILE_VERSION, ObbInfo.MAIN_EXPANSION_FILE_SIZE);
            }
        } else {
            xAPKS = new XAPKFile[0];
        }
	}

    /**
     * Go through each of the Expansion APK files defined in the project and
     * determine if the files are present and match the required size. Free
     * applications should definitely consider doing this, as this allows the
     * application to be launched for the first time without having a network
     * connection present. Paid applications that use LVL should probably do at
     * least one LVL check that requires the network to be present, so this is
     * not as necessary.
     * 
     * @return true if they are present.
     */
    public boolean expansionFilesDelivered() {
        // 判断所有的apk扩展文件是否存在，且文件大小一致
        for (XAPKFile xf : xAPKS) {
            String fileName = Helpers.getExpansionAPKFileName(mContext, xf.mIsMain, xf.mFileVersion);
            if (!Helpers.doesFileExist(mContext, fileName, xf.mFileSize, false)) {
                if (xf.mIsMain) {
                    Log.i("APKExpansionDownloader", "Expansion files is not delivered: main obb does not exist");
                } else {
                    Log.i("APKExpansionDownloader", "Expansion files is not delivered: patch obb does not exist");
                }
                return false;
            }
        }
        Log.i("APKExpansionDownloader", "Expansion files is already delivered");
        return true;
    }
    
    public void downloadExpansionFiles(Activity activity, onDownloadStateChanged listener) {
    	try {
            mListener = listener;
    		// Build an Intent to start this activity from the Notification
            Intent notifierIntent = new Intent(activity, activity.getClass());
            notifierIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, notifierIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Start the download service (if required)
            int startResult = DownloaderClientMarshaller.startDownloadServiceIfRequired(activity, pendingIntent, ObbDownloadService.class);
            // If download has started, initialize this activity to show
            // download progress
            if (startResult != DownloaderClientMarshaller.NO_DOWNLOAD_REQUIRED) {
                // This is where you do set up to display the download
                // progress (next step)
            	// Instantiate a member instance of IStub
                Log.i("APKExpansionDownloader", "Try to download obb file");
                mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, ObbDownloadService.class);
                // 创建并显示进度对话框
                mDownloadProgressDlg = new MyProgressDialog(activity);
                mDownloadProgressDlg.show();
            }
            // 不需要下载
            else {
                Log.i("APKExpansionDownloader", "No need to download obb file");
                if (mListener != null) {
                    mListener.onDownloadSuccess();
                }
            }
    	} catch (NameNotFoundException e) {
    		e.printStackTrace();
    	}
    }

    public void onResume() {
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.connect(mContext);
        }
    }

    public void onStop() {
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.disconnect(mContext);
        }
    }
    
	@Override
	public void onServiceConnected(Messenger m) {
		mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
	    mRemoteService.onClientUpdated(mDownloaderClientStub.getMessenger());
	    Log.i("APKExpansionDownloader", "ServiceConnected");
	}

	@Override
	public void onDownloadStateChanged(int newState) {
		Log.i("APKExpansionDownloader", "DownloadStateChanged: " + newState);
        switch (newState) {
        	// 下载成功
	        case IDownloaderClient.STATE_COMPLETED:
	        	mDownloadProgressDlg.success();
	        	break;
	        // 下载失败
	        case IDownloaderClient.STATE_FAILED_CANCELED:
	        case IDownloaderClient.STATE_FAILED_FETCHING_URL:
	        case IDownloaderClient.STATE_FAILED_UNLICENSED:
	        case IDownloaderClient.STATE_FAILED:
	        	mDownloadProgressDlg.failed();
	        	break;
	        // 下载暂停
	        case IDownloaderClient.STATE_PAUSED_NEED_CELLULAR_PERMISSION:
            case IDownloaderClient.STATE_PAUSED_WIFI_DISABLED_NEED_CELLULAR_PERMISSION:
            case IDownloaderClient.STATE_PAUSED_BY_REQUEST:
            case IDownloaderClient.STATE_PAUSED_ROAMING:
            case IDownloaderClient.STATE_PAUSED_SDCARD_UNAVAILABLE:
            	mDownloadProgressDlg.pause();
                break;
            // 恢复下载
            case IDownloaderClient.STATE_IDLE:
            case IDownloaderClient.STATE_CONNECTING:
            case IDownloaderClient.STATE_FETCHING_URL:
            case IDownloaderClient.STATE_DOWNLOADING:
                break;
            // 未知状态
            default:
            	break;
        }
	}

	@Override
	public void onDownloadProgress(DownloadProgressInfo progress) {
		int progressInPercent = (int)(progress.mOverallProgress * 100 / progress.mOverallTotal);
		Log.i("APKExpansionDownloader", "DownloadProgress: " + progressInPercent);
		mDownloadProgressDlg.setProgress(progressInPercent);
	}

	private int getResourceId(String resName, String resType) {
		return mContext.getResources().getIdentifier(resName, resType, mContext.getPackageName());
	}

    private String getString(String resName) {
        return mContext.getString(getResourceId(resName, "string"));
    }

    // 下载进度对话框
	private class MyProgressDialog extends ProgressDialog {
		private boolean mIsPaused;
        private boolean mIsComplete;
		private String mDlgTitleDownloading;
        private String mDlgTitleFailed;
        private String mDlgTitlePaused;
        private String mDlgTitleComplete;
	    private String mDlgCancelBtnText;
	    private String mDlgResumeBtnText;
	    private String mDlgPauseBtnText;
        private String mDlgRetryBtnText;
		
		public MyProgressDialog(Context context) {
			super(context);
			mIsPaused = false;
            mIsComplete = false;
            mDlgTitleDownloading = getString("obb_download_title_downloading");
            mDlgTitleFailed = getString("obb_download_title_failed");
            mDlgTitlePaused = getString("obb_download_title_paused");
            mDlgTitleComplete = getString("obb_download_title_complete");
			mDlgCancelBtnText = getString("obb_download_btn_cancel");
			mDlgPauseBtnText = getString("obb_download_btn_pause");
			mDlgResumeBtnText = getString("obb_download_btn_resume");
            mDlgRetryBtnText = getString("obb_download_btn_retry");
			setIndeterminate(false);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            setIcon(getResourceId("obb_downloader_indicator", "drawable"));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setTitle(mDlgTitleDownloading);
            setProgress(0);
            setMax(100);
            
            setButton(DialogInterface.BUTTON_POSITIVE, mDlgPauseBtnText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
                    if (mIsComplete) {
                        return;
                    }
                	// 只有执行到onServiceConnected，mRemoteService才会被创建，所以需要判断其是否为null
					if (mRemoteService != null) {
						if (mIsPaused) {
							mRemoteService.requestContinueDownload();
                            setTitle(mDlgTitleDownloading);
							getButton(DialogInterface.BUTTON_POSITIVE).setText(mDlgPauseBtnText);
                            Log.i("APKExpansionDownloader", "download continued by user");
						} else {
							mRemoteService.requestPauseDownload();
                            setTitle(mDlgTitlePaused);
							getButton(DialogInterface.BUTTON_POSITIVE).setText(mDlgResumeBtnText);
                            Log.i("APKExpansionDownloader", "download paused by user");
						}
						mIsPaused = !mIsPaused;
					}
				}
			});
            
            setButton(DialogInterface.BUTTON_NEGATIVE, mDlgCancelBtnText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
                    if (mIsComplete) {
                        return;
                    }
                    // 点击取消后，停止下载
                    if (mRemoteService != null) {
                        mRemoteService.requestAbortDownload();
                        Log.i("APKExpansionDownloader", "download canceled by user");
                    }
                    // 关闭对话框
					trueDismiss();
					// 通知下载被取消
					if (mListener != null) {
						mListener.onDownloadCanceled();
					}
				}
			});
		}
		
		@Override
		public void dismiss() {
			// do nothing
		}
		
		private void trueDismiss() {
			super.dismiss();
		}
		
		public void success() {
            if (!mIsComplete) {
                setProgress(100);
                setTitle(mDlgTitleComplete);
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trueDismiss();
                        if (mListener != null) {
							mListener.onDownloadSuccess();
						}
                    }
                }, 2000);
                mIsComplete = true;
                Log.i("APKExpansionDownloader", "Download success");
            }
		}
		
		public void failed() {
			setTitle(mDlgTitleFailed);
            getButton(DialogInterface.BUTTON_POSITIVE).setText(mDlgRetryBtnText);
            mIsPaused = true;
		}
		
		public void pause() {
            setTitle(mDlgTitlePaused);
            getButton(DialogInterface.BUTTON_POSITIVE).setText(mDlgResumeBtnText);
            mIsPaused = true;
		}
	}
	
	public interface onDownloadStateChanged {
		void onDownloadSuccess();
		void onDownloadFailed();
		void onDownloadCanceled();
	}
}
