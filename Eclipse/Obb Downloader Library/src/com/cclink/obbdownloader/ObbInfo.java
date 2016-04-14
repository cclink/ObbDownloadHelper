package com.cclink.obbdownloader;

public abstract class ObbInfo {
    private static final byte[] SALT = new byte[] { 1, 43, -122, -11, 4, 8, -33, -12, 43, 12, -2, -4, 9, 5, -52, -108, -33, 45, -1, 84 };

    public abstract String getPublicKey();
    
    public byte[] getSalt() {
    	return SALT;
    }
    
    public abstract int getMainObbVersion();
    public abstract long getMainObbFileSize();
    
    public int getPatchObbVersion() {
    	return 0;
    }
    public long getPatchObbFileSize() {
    	return 0;
    }
}
