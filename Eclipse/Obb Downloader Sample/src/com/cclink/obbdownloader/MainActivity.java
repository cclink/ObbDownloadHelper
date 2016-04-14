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
		mDownloaderHelper = new ObbDownloadHelper(this, new ObbInfo() {
			
			@Override
			public String getPublicKey() {
				return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp2hAfnmkYIs11L9w0GFOQjEyj38Ce+59L3ECDn2AuWenQiangXu3CzpOkZP8VlMe1ZwyEtsqI4VH8L1wIS100DDhzQKByy6YzADFiG0eU4o5uMDLBwlpG7KZXBTf2r881AyPfUYASwrdrNR+EzaXNYmYNsDTYmSAGtpyIqSZMc0gXKjTGF9DPcdVgnGQO1fAMgczeOQI2+gMDT1ynfMF5U72iqmf8D6SA4O6U8f4r1MfGvsujSOeGvitxjL05n50iq0J0CGMkZOZofTBT68kFiUv1oD6kvQODuBM3juYWKnnq4wBM/oBDYYBi+X3zuoScCw+kizmn1XXfNNDXkBVLwIDAQAB";
			}
			
			@Override
			public int getMainObbVersion() {
				return 3;
			}
			
			@Override
			public long getMainObbFileSize() {
				return 1546530L;
			}
		});
		if (!mDownloaderHelper.expansionFilesDelivered()) {
			mDownloaderHelper.downloadExpansionFiles(this, new ObbDownloadListener() {
				@Override
				public void onDownloadSuccess() {
					Toast.makeText(MainActivity.this, "Download success.", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onDownloadFailed() {
					Toast.makeText(MainActivity.this, "Download failed.", Toast.LENGTH_SHORT).show();
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
			mDownloaderHelper.connect();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mDownloaderHelper != null) {
			mDownloaderHelper.disconnect();
		}
	}
}
