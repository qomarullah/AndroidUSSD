package com.tech.ussdcall;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mfstech on 07/02/19.
 */

public class UssdService extends AccessibilityService {
    public static String TAG = "USSD";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent");
        String text = event.getText().toString();
        if (event.getClassName().equals("android.app.AlertDialog")) {
            Log.d(TAG, text);
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onInterrupt() {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
        /*AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        */
        //AccessibilityNodeInfo nodeInfo = new AccessibilityNodeInfo();
        Toast.makeText(this,"connected", Toast.LENGTH_LONG).show();

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Toast.makeText(this,"error", Toast.LENGTH_LONG).show();
            return;
        }


        AccessibilityNodeInfo nodeInput = nodeInfo.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,"123456");
        nodeInput.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,bundle);
        //nodeInput.refresh();

        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("Send");
        for (AccessibilityNodeInfo node : list) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }


    }
}