package com.wulala.blecom.upload;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

import no.nordicsemi.android.ble.data.Data;


public class SuMNotifyCallback{

}

//public class SuMNotifyCallback implements RelayDataCallback{
//
//    @Override
//    public void justLogNewMsg(@NonNull final byte[] data) {
//        logStr.postValue(Tools.bytes2HexString(Objects.requireNonNull(data)));
//    }
//
//    @Override
//    public void onNewMSg(@NonNull final BluetoothDevice device, final BaseNotiMsg notifyMsg) {
//        // relayMsg
//        // relayMsgLiveData.postValue(notifyMsg);
//
//        switch (notifyMsg.getMsgType()) {
//            case NotifyMsgParser.DEVICE_STATE:
//                deviceStateLiveData.postValue((DeviceState) notifyMsg);
//                break;
//
//            default:
//                Log.e(TAG, "unknown msg type");
//                Log.e(TAG, ((UnknownMsg) notifyMsg).toString());
//                break;
//        }
//    }
//
//    @Override
//    public void onInvalidDataReceived(@NonNull final BluetoothDevice device,
//                                      @NonNull final Data data) {
//        log(Log.WARN, "Invalid data received: " + data);
//        // bleLogViewModel.showLog(Tools.bytesToHex(cmd));
//    }
//}
