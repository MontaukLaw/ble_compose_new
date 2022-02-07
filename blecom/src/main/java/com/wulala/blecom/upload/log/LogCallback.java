package com.wulala.blecom.upload.log;

import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;

import com.wulala.blecom.upload.log.entity.BaseLog;

public interface LogCallback {
    void onNewLogRead(@NonNull final BluetoothDevice device, @NonNull final BaseLog powerLog);

}
