package com.cclink.obbdownloader.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cclink.obbdownloader.ObbInfo;
import com.cclink.obbdownloader.common.XAPKFile;
import com.cclink.obbdownloader.util.ResourceUtil;
import com.cclink.obbdownloader.util.XAPKFileUitl;
import com.google.android.vending.expansion.downloader.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;

public class ObbCopyHelper {

    private Context mContext;
    private ObbCopyListener mListener;
    private CopyProgressDialog mCopyProgressDlg;
    private XAPKFile[] xAPKS;

    public ObbCopyHelper(Context context, ObbInfo obbInfo) {
        mContext = context;
        int mainVer = obbInfo.getMainObbVersion();
        long mainSize = obbInfo.getMainObbFileSize();
        int patchVer = obbInfo.getPatchObbVersion();
        long patchSize = obbInfo.getPatchObbFileSize();
        if (mainVer > 0 && patchVer > 0) {
            if (mainSize > 0 && patchSize > 0) {
                xAPKS = new XAPKFile[2];
                xAPKS[0] = new XAPKFile(true, mainVer, mainSize);
                xAPKS[1] = new XAPKFile(false, patchVer, patchSize);
            } else {
                xAPKS = new XAPKFile[1];
                xAPKS[0] = new XAPKFile(true, mainVer, mainSize);
            }
        } else {
            xAPKS = new XAPKFile[0];
        }
    }

    public void copyAllToFolder(String folder, ObbCopyListener listener) {
        copy(xAPKS, folder, listener);
    }

    public void copyMainobbToFolder(String folder, ObbCopyListener listener) {
        copy(XAPKFileUitl.getMainXAPKs(xAPKS), folder, listener);
    }

    public void copyPatchobbToFolder(String folder, ObbCopyListener listener) {
        copy(XAPKFileUitl.getPatchXAPKs(xAPKS), folder, listener);
    }

    private void copy(XAPKFile[] xfs, String folder, ObbCopyListener listener) {
        if (!XAPKFileUitl.checkXAPKs(mContext, xfs)) {
            Log.w("APKExpansionCopy", "Copy failed, obb file check failed");
            if (listener != null) {
                listener.onCopyFailed();
            }
        }
        // run the copy task
        else {
            mListener = listener;
            new CopyTask(xAPKS, folder).execute();
        }
    }

    private class CopyTask extends AsyncTask<Void, Integer, Boolean> {
        private XAPKFile[] mXFiles;
        private String mDestFolder;

        public CopyTask(XAPKFile[] xfs, String folder) {
            mXFiles = xfs;
            mDestFolder = folder;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                for (XAPKFile xFile : mXFiles) {
                    String fileName = Helpers.getExpansionAPKFileName(mContext, xFile.mIsMain, xFile.mFileVersion);
                    String srcFileName = Helpers.generateSaveFileName(mContext, fileName);
                    File srcFile = new File(srcFileName);
                    if (!copy(srcFile, mDestFolder)) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        private boolean copy(File src, String dstFolder) throws IOException {
            if (!src.exists()) {
                Log.w("APKExpansionCopy", "Copy failed, obb file does not exist");
                return false;
            }
            File desDir = new File(dstFolder);
            if (!desDir.exists()) {
                if (!desDir.mkdirs()) {
                    Log.w("APKExpansionCopy", "Copy failed, create dirs failed");
                    return false;
                }
            }

            mCopyProgressDlg.setProgress(0);

            InputStream in = null;
            OutputStream out = null;
            try {
                in = new FileInputStream(src);
                out = new FileOutputStream(dstFolder + File.separator + src.getName());

                long totalSize = in.available();
                long copiedSize = 0;
                int lastPercent = 0;
                // 8k gains more performance than 1k
                byte buffer[] = new byte[1024 * 16];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                    copiedSize += realLength;
                    int percent = (int) (copiedSize * 100 / totalSize);
                    if (percent > 100) {
                        percent = 100;
                    }
                    if (lastPercent != percent) {
                        lastPercent = percent;
                        mCopyProgressDlg.setProgress(percent);
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.i("APKExpansionCopy", "Copy file success");
                if (mListener != null) {
                    mListener.onCopyComplete();
                }
            } else {
                Log.w("APKExpansionCopy", "Copy file failed");
                if (mListener != null) {
                    mListener.onCopyFailed();
                }
            }
            mCopyProgressDlg.dismiss();
        }
    }

    private class CopyProgressDialog extends ProgressDialog {
        public CopyProgressDialog(Context context) {
            super(context);
            setIndeterminate(false);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setTitle(ResourceUtil.getString(mContext, "obb_download_title_coping"));
            setProgress(0);
            setMax(100);
        }
    }
}
