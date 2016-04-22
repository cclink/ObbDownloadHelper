package com.cclink.obbdownloader.download;

public interface ObbDownloadListener {
    void onDownloadComplete();

    void onDownloadFailed();
}
