package com.wulala.blecom.core;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.wulala.blecom.livedata.SimpleTextLogLiveData;
import com.wulala.blecom.livedata.SubDeviceStateLiveData;
import com.wulala.blecom.upload.NotifyMsgParser;
import com.wulala.blecom.upload.RelayDataCallback;
import com.wulala.blecom.upload.entity.BaseUploadMsg;
import com.wulala.blecom.upload.entity.GameState;
import com.wulala.blecom.upload.entity.PowerLiveData;
import com.wulala.blecom.upload.entity.SubDevice;
import com.wulala.blecom.upload.entity.TriggerEvent;
import com.wulala.blecom.upload.entity.UnknownMsg;

import java.util.Arrays;
import java.util.UUID;

import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.livedata.ObservableBleManager;

public class NewSuMManager extends ObservableBleManager {

    private static final String TAG = NewSuMManager.class.getSimpleName();

    public final static String SM_BLE_NAME = "DG-LAB_1000";

    public final static UUID SM_UUID_SERVICE = UUID.fromString("0000180c-0000-1000-8000-00805f9b34fb");
    private final static UUID CMD_UUID_BUTTON_CHAR = UUID.fromString("0000150a-0000-1000-8000-00805f9b34fb");
    private final static UUID NOTIFY_UUID_BUTTON_CHAR = UUID.fromString("0000150b-0000-1000-8000-00805f9b34fb");

    // public final static String SM_BLE_NAME = "DG-LAB_2000";
    // public final static UUID SM_UUID_SERVICE = UUID.fromString("955a180c-0fe2-f5aa-a094-84b8d4f3e8ad");
    // public final static UUID CMD_UUID_BUTTON_CHAR = UUID.fromString("955a150a-0fe2-f5aa-a094-84b8d4f3e8ad");
    // public final static UUID NOTIFY_UUID_BUTTON_CHAR = UUID.fromString("955a150b-0fe2-f5aa-a094-84b8d4f3e8ad");

    // private final SubDeviceStateLiveData subDeviceStateLiveData;
    private BluetoothGattCharacteristic cmdCharacteristic, notifyCharacteristic;

    private final MutableLiveData<String> logForHuman = new MutableLiveData<>();

    public MutableLiveData<String> getLogForHuman() {
        return logForHuman;
    }

    private boolean supported;

    // 构造
    public NewSuMManager(@NonNull final Context context) {
        super(context);

        // subDeviceStateLiveData = new SubDeviceStateLiveData();
    }

    // 这个内部类必须是内部类
    private class SuMBleManagerGattCallback extends BleManagerGattCallback {
        // 初始化过程
        @Override
        protected void initialize() {
            // 设置chara的通知回调(notifyCallback)
            // 具体的回调设置在下面
            setNotificationCallback(notifyCharacteristic).with(notifyCallback);

            // 打开中继的上传消息用chara的notify功能
            enableNotifications(notifyCharacteristic).enqueue();

        }

        @Override
        public boolean isRequiredServiceSupported(@NonNull final BluetoothGatt gatt) {

            final BluetoothGattService service = gatt.getService(SM_UUID_SERVICE);

            Log.d(TAG, "isRequiredServiceSupported: ");

            if (service != null) {
                Log.d(TAG, "service: " + service);

                cmdCharacteristic = service.getCharacteristic(CMD_UUID_BUTTON_CHAR);
                notifyCharacteristic = service.getCharacteristic(NOTIFY_UUID_BUTTON_CHAR);
            }

            boolean writeRequest = false;

            if (cmdCharacteristic != null) {
                final int rxProperties = cmdCharacteristic.getProperties();
                Log.d(TAG, "isRequiredServiceSupported: " + rxProperties);

                writeRequest = (rxProperties & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0;
            }

            supported = cmdCharacteristic != null && notifyCharacteristic != null && writeRequest;

            Log.d(TAG, "supported: " + supported);

            return supported;
        }

        @Override
        protected void onDeviceDisconnected() {
            cmdCharacteristic = null;
            notifyCharacteristic = null;
        }
    }


    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new NewSuMManager.SuMBleManagerGattCallback();
    }

    // 核心的notify callback
    private final RelayDataCallback notifyCallback = new RelayDataCallback() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void printLog(String log) {
            Log.d(TAG, "printLog" + log);
        }

        @Override
        public void transLog(String mHumanLog) {
            Log.d(TAG, "transLog" + mHumanLog);
            logForHuman.postValue(mHumanLog);
        }

        @Override
        public void onNewMsg(BaseUploadMsg notifyMsg) {
            Log.d(TAG, "onNewMsg");
        }

        // 兜底的
        @Override
        public void onInvalidDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
            log(Log.WARN, "Invalid data received: " + data);
        }
    };

    public void writeByteArray(byte[] cmd) {

        if (cmdCharacteristic == null) {
            Log.d(TAG, "cmdCharacteristic == null ");
            return;
        }
        Log.d(TAG, "Writing " + Arrays.toString(cmd) + "len: " + cmd.length);

        writeCharacteristic(cmdCharacteristic, cmd).done(wr -> {
            Log.d(TAG, "writeCharacteristic: success ");
        }).fail((error, state) -> {
            Log.d(TAG, "writeCharacteristic: fail " + error + " " + state);
        }).enqueue();
    }

}
