package com.wulala.blecom.upload;

import android.bluetooth.BluetoothDevice;

import com.wulala.blecom.upload.entity.BaseUploadMsg;

public interface RelayNotifyCallback {

    void onNewMsg(final BaseUploadMsg notifyMsg);

    void printLog(final String log);

}
