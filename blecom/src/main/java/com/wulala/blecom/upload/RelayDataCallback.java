package com.wulala.blecom.upload;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wulala.blecom.upload.entity.BaseUploadMsg;
import com.wulala.blecom.utils.UploadMsgUtils;

import java.util.Objects;

import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;


public abstract class RelayDataCallback implements ProfileDataCallback, RelayNotifyCallback {

    private static final String TAG = RelayDataCallback.class.getSimpleName();

    @Override
    public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {

        Log.d(TAG, "onDataReceived: " + data.toString());

        Log.d(TAG, "data len: " + Objects.requireNonNull(data.getValue()).length);

        // 将数据的byte[]转成实体类的工具
        // 统一将原始数据转成BaseUploadMsg的基类
        BaseUploadMsg notifyMsg = NotifyMsgParser.parse(data.getValue());

        onNewMsg(notifyMsg);

        printLog(data.toString());

        // log太快, 无需翻译
        if (data.getValue()[0] != (byte) 0xE1) {

            transLog(UploadMsgUtils.translateLogFoHuman(data.getValue()));
        }

    }
}
