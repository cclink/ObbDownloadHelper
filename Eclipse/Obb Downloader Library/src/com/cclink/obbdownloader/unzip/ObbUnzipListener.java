package com.cclink.obbdownloader.unzip;

public interface ObbUnzipListener {
    void onUnzipComplete();
    void onUnzipFailed();
}
