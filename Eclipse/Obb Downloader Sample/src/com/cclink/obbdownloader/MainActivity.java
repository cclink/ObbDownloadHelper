package com.cclink.obbdownloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ObbHelper mObbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Create ObbHelper object
		mObbHelper = new ObbHelper(this, new ObbInfo() {
			
			// This function must be override to return your app's public key
			@Override
			public String getPublicKey() {
				return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp2hAfnmkYIs11L9w0GFOQjEyj38Ce+59L3ECDn2AuWenQiangXu3CzpOkZP8VlMe1ZwyEtsqI4VH8L1wIS100DDhzQKByy6YzADFiG0eU4o5uMDLBwlpG7KZXBTf2r881AyPfUYASwrdrNR+EzaXNYmYNsDTYmSAGtpyIqSZMc0gXKjTGF9DPcdVgnGQO1fAMgczeOQI2+gMDT1ynfMF5U72iqmf8D6SA4O6U8f4r1MfGvsujSOeGvitxjL05n50iq0J0CGMkZOZofTBT68kFiUv1oD6kvQODuBM3juYWKnnq4wBM/oBDYYBi+X3zuoScCw+kizmn1XXfNNDXkBVLwIDAQAB";
			}
			
			// This function must be override to return the main obb version
			// The returned version must be greater than 0
			@Override
			public int getMainObbVersion() {
				return 3;
			}
			
			// This function must be override to return the main obb file size
			// The returned size must be greater than 0
			@Override
			public long getMainObbFileSize() {
				return 1546530L;
			}
			
			// If you donn't have patch obb file, you don't need override this function
			@Override
			public int getPatchObbVersion() {
				return 3;
			}
			// If you donn't have patch obb file, you don't need override this function
			@Override
			public long getPatchObbFileSize() {
				return 5570L;
			}
		});
		
		// Check whether the obb files are delivered.
		if (!mObbHelper.expansionFilesDelivered()) {
			// The obb files haven't delivered, so we should download the obb files.
			mObbHelper.downloadExpansionFiles(this, new ObbHelperListener() {
				@Override
				public void onSuccess() {
					Toast.makeText(MainActivity.this, "Download success.", Toast.LENGTH_SHORT).show();
					// The obb files have been download, you can use them directly.
					// Also you can unzip or copy them to a target folder.
					String folder = getExternalFilesDir(null).toString();
					mObbHelper.unzipMainobbToFolder(folder, new ObbHelperListener() {
						@Override
						void onSuccess() {
							Toast.makeText(MainActivity.this, "Unzip main obb file success.", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						void onFailed() {
							Toast.makeText(MainActivity.this, "Unzip main obb file failed.", Toast.LENGTH_SHORT).show();
						}
					});
					mObbHelper.copyPatchobbToFolder(folder, new ObbHelperListener() {
						@Override
						void onSuccess() {
							Toast.makeText(MainActivity.this, "Copy patch obb file success.", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						void onFailed() {
							Toast.makeText(MainActivity.this, "Copy patch obb file failed.", Toast.LENGTH_SHORT).show();
						}
					});
				}
				
				@Override
				public void onFailed() {
					Toast.makeText(MainActivity.this, "Download failed.", Toast.LENGTH_SHORT).show();
				}
			});
		}
		// The obb files have already delivered.
		else {
			Toast.makeText(MainActivity.this, "Expansion files are already delivered.", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mObbHelper.connect();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mObbHelper.disconnect();
	}
}
