package com.wulala.blecom.upload.log;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wulala.blecom.upload.log.entity.BaseLog;
import com.wulala.blecom.utils.Tools;

import java.util.Objects;

import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;

public abstract class LogDataCallback implements ProfileDataCallback, DataSentCallback, LogCallback {

    private static final String TAG = LogDataCallback.class.getSimpleName();

    @Override
    public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
        parse(device, data);
    }

    @Override
    public void onDataSent(@NonNull final BluetoothDevice device, @NonNull final Data data) {
        parse(device, data);
    }

    private void parse(@NonNull final BluetoothDevice device, @NonNull final Data data) {
        Log.d(TAG, "1: ");
        Log.d(TAG, "parse: " + Tools.bytesToHex(Objects.requireNonNull(data.getValue())));
        Log.d(TAG, "2: ");
        final BaseLog log = PowerWaveLogBuilder.parsePowerLog(Objects.requireNonNull(data.getValue()));

        if (log != null) {
            onNewLogRead(device, log);
        }
    }
}