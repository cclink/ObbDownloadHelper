package com.cclink.obbdownloader.copy;

public interface ObbCopyListener {
	void onCopyComplete();
    void onCopyFailed();
}
