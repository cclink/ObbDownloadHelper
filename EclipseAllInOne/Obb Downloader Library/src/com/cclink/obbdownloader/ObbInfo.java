package com.cclink.obbdownloader;

public abstract class ObbInfo {
    // used by the preference obfuscater
    private static final byte[] SALT = new byte[] { 1, 43, -122, -11, 4, 8, -33, -12, 43, 12, -2, -4, 9, 5, -52, -108,
            -33, 45, -1, 84 };

    // Different application has different Public Key.
    // It is used by Google Play Licensing Library to verify the identity of the
    // client.
    public abstract String getPublicKey();

    // The salt is used by PreferenceObfuscator class to obfuscate the value
    // stored in SharedPreferences
    // It only makes sense in local storage, and would not be sent to the
    // network
    public byte[] getSalt() {
        return SALT;
    }

    // You use this library, we assume that you must have main obb file in your
    // application
    // So you should override getMainObbVersion() and getMainObbFileSize() to
    // give a correct obb file version and size.
    public abstract int getMainObbVersion();

    public abstract long getMainObbFileSize();

    // Override getPatchObbVersion() and getPatchObbFileSize() only when you
    // have patch obb file.
    public int getPatchObbVersion() {
        return 0;
    }

    public long getPatchObbFileSize() {
        return 0;
    }
}
