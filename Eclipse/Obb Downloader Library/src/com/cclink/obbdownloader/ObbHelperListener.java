package com.cclink.obbdownloader;

public abstract class ObbHelperListener {
    // used by the preference obfuscater
    private static final byte[] SALT = new byte[] { 1, 43, -122, -11, 4, 8, -33, -12, 43, 12, -2, -4, 9, 5, -52, -108, -33, 45, -1, 84 };
    
    // Different application has different Public Key.
    // It is used by Google Play Licensing Library to verify the identity of the client.
    public abstract String getPublicKey();
    
    public byte[] getSalt() {
    	return SALT;
    }
    
    // you must have main obb in your application 
    public abstract int getMainObbVersion();
    public abstract long getMainObbFileSize();
    
    public int getPatchObbVersion() {
    	return 0;
    }
    public long getPatchObbFileSize() {
    	return 0;
    }
}
