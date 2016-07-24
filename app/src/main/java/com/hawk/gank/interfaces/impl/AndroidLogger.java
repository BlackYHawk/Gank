package com.hawk.gank.interfaces.impl;

import android.util.Log;

import com.hawk.gank.interfaces.Logger;


public class AndroidLogger implements Logger {
	private final boolean D = true;

	public final void d(String tag, String debug) {
		if (D) {
			Log.d(tag, debug);
		}
	}

	public final void i(String tag, String debug) {
		if (D) {
			Log.i(tag, debug);
		}
	}

	public final void w(String tag, String debug) {
		if (D) {
			Log.w(tag, debug);
		}
	}

    public final void e(String tag, String debug) {
        if (D) {
            Log.e(tag, debug);
        }
    }

	public final void exec(Exception e) {
		if (D) {
			e.printStackTrace();
		}
	}

}
