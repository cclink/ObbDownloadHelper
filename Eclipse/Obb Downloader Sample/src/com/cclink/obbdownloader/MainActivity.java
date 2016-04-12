package com.cclink.obbdownloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ObbDownloadHelper mDownloaderHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDownloaderHelper = new ObbDownloadHelper(this);
		if (!mDownloaderHelper.expansionFilesDelivered()) {
			mDownloaderHelper.downloadExpansionFiles(this, new ObbDownloadHelper.onDownloadStateChanged() {
				@Override
				public void onDownloadSuccess() {
					Toast.makeText(MainActivity.this, "Download success.", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onDownloadFailed() {
					Toast.makeText(MainActivity.this, "Download failed.", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onDownloadCanceled() {
					Toast.makeText(MainActivity.this, "Download canceled.", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(MainActivity.this, "Expansion file is already delivered.", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mDownloaderHelper != null) {
			mDownloaderHelper.onResume();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mDownloaderHelper != null) {
			mDownloaderHelper.onStop();
		}
	}
}
