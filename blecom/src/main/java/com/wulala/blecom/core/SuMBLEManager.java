package com.wulala.blecom.core;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.wulala.blecom.livedata.SimpleTextLogLiveData;
import com.wulala.blecom.livedata.SubDeviceStateLiveData;
import com.wulala.blecom.livedata.TriggerEventLiveData;
import com.wulala.blecom.upload.NotifyMsgParser;
import com.wulala.blecom.upload.entity.BaseUploadMsg;
import com.wulala.blecom.upload.RelayDataCallback;
import com.wulala.blecom.upload.entity.GameState;
import com.wulala.blecom.upload.entity.PowerLiveData;
import com.wulala.blecom.upload.entity.SubDevice;
import com.wulala.blecom.upload.entity.TriggerEvent;
import com.wulala.blecom.upload.entity.UnknownMsg;
import com.wulala.blecom.upload.log.LogDataCallback;
import com.wulala.blecom.upload.log.entity.BaseLog;
import com.wulala.blecom.upload.log.entity.PowerLog;
import com.wulala.blecom.upload.log.entity.WaveLog;
import com.wulala.blecom.utils.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.livedata.ObservableBleManager;

public class SuMBLEManager extends ObservableBleManager {

    private static final String TAG = SuMBLEManager.class.getSimpleName();

    // public final static String SM_BLE_NAME = "D-LAB ESTIM LOCK";
    public final static String SM_BLE_NAME = "DG-LAB_1000";
    // D-LAB ESTIM01

    public final static int POWER_LIST_LEN = 25;

    // log用的UUID
    public final static UUID LOG_UUID_SERVICE = UUID.fromString("00002003-0000-1000-8000-00805f9b34fb");
    public final static UUID LOG_UUID_CHAR = UUID.fromString("00000008-0000-1000-8000-00805f9b34fb");

    // 主Service UUID
    // public final static UUID SM_UUID_SERVICE = UUID.fromString("955a180c-0fe2-f5aa-a094-84b8d4f3e8ad");
    public final static UUID SM_UUID_SERVICE = UUID.fromString("0000180c-0000-1000-8000-00805f9b34fb");

    // 0x150A 写命令
    // 0x150B Notify
    // 0x150C 波形下载
    private final static UUID CMD_UUID_BUTTON_CHAR = UUID.fromString("0000150a-0000-1000-8000-00805f9b34fb");
    private final static UUID NOTIFY_UUID_BUTTON_CHAR = UUID.fromString("0000150b-0000-1000-8000-00805f9b34fb");

    private BluetoothGattCharacteristic buttonCharacteristic, ledCharacteristic;
    private BluetoothGattCharacteristic cmdCharacteristic, notifyCharacteristic;

    private boolean supported;

    // 主livedata初始化
    private final MutableLiveData<BaseUploadMsg> relayMsgLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<String>> relayMsgLiveDataList = new MutableLiveData<>();

    private final MutableLiveData<GameState> gameStateLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> logStr = new MutableLiveData<>();

    private final MutableLiveData<WaveLog> waveLogMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<PowerLog> powerLogMutableLiveData = new MutableLiveData<>();

    // private final MutableLiveData<SubDevice> subDeviceMutableLiveData = new MutableLiveData<>();

    private final SubDeviceStateLiveData subDeviceStateLiveData;

    private final MutableLiveData<TriggerEvent> triggerEventLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> uploadContent = new MutableLiveData<>();

    private final SimpleTextLogLiveData simpleTextLogLiveData;

    private final MutableLiveData<String> logForHuman = new MutableLiveData<>();

    public MutableLiveData<String> getLogForHuman() {
        return logForHuman;
    }

    /////////////////////////////////// Getter /////////////////////////////

    public SimpleTextLogLiveData getSimpleTextLogLiveData() {
        return simpleTextLogLiveData;
    }

    public MutableLiveData<List<String>> getRelayMsgLiveDataList() {
        return relayMsgLiveDataList;
    }

    public MutableLiveData<TriggerEvent> getTriggerEventLiveData() {
        return triggerEventLiveData;
    }

    public MutableLiveData<WaveLog> getWaveLogMutableLiveData() {
        return waveLogMutableLiveData;
    }

    public SubDeviceStateLiveData getSubDeviceStateLiveData() {
        return subDeviceStateLiveData;
    }

    public MutableLiveData<String> getLogStr() {
        return logStr;
    }

    public MutableLiveData<PowerLog> getPowerLogMutableLiveData() {
        return powerLogMutableLiveData;
    }

    public MutableLiveData<BaseUploadMsg> getRelayMsgLiveData() {
        return relayMsgLiveData;
    }

    public MutableLiveData<GameState> getGameStateLiveData() {
        return gameStateLiveData;
    }

    public MutableLiveData<String> getUpload() {
        return uploadContent;
    }

    // 构造
    public SuMBLEManager(@NonNull final Context context) {
        super(context);

        subDeviceStateLiveData = new SubDeviceStateLiveData();

        simpleTextLogLiveData = new SimpleTextLogLiveData();
    }

    public MutableLiveData<List<Integer>> channelAPowerList = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> channelBPowerList = new MutableLiveData<>();

    public MutableLiveData<List<Integer>> getChannelAPowerList() {
        if (channelAPowerList == null) {
            channelAPowerList = new MutableLiveData<>();
            caPower = new ArrayList<>();
            channelAPowerList.postValue(caPower);
        }
        return channelAPowerList;
    }

    public MutableLiveData<List<Integer>> getChannelBPowerList() {
        if (channelBPowerList == null) {
            channelBPowerList = new MutableLiveData<>();
            cbPower = new ArrayList<>();
            channelBPowerList.postValue(cbPower);
        }
        return channelBPowerList;
    }

    private List<Integer> caPower = new ArrayList<>();
    private List<Integer> cbPower = new ArrayList<>();

    private void addChannelPowerList(Integer newValue, MutableLiveData<List<Integer>> powerListLiveData, List<Integer> powerList) {

        powerList.add(newValue);

        if (powerList.size() > POWER_LIST_LEN) {
            powerList.remove(0);
        }

        powerListLiveData.postValue(powerList);
    }

    // char具体用于读写操作
    private BluetoothGattCharacteristic logChar;

    // 这个内部类必须是内部类
    private class SuMBleManagerGattCallback extends BleManagerGattCallback {

        // 初始化过程
        @Override
        protected void initialize() {
            // 设置chara的通知回调(notifyCallback)
            // 具体的回调设置在下面
            setNotificationCallback(notifyCharacteristic).with(notifyCallback);

            // 对log chara进行读操作
            readCharacteristic(logChar).with(logDataCallback).enqueue();

            // 打开中继的上传消息用chara的notify功能
            enableNotifications(notifyCharacteristic).enqueue();
            // enableNotifications(logChar).enqueue();

            Log.d(TAG, "initialize: ");
        }

        @Override
        public boolean isRequiredServiceSupported(@NonNull final BluetoothGatt gatt) {

            final BluetoothGattService logService = gatt.getService(LOG_UUID_SERVICE);
            if (logService != null) {
                Log.d(TAG, "logService: ");
                logChar = logService.getCharacteristic(LOG_UUID_CHAR);

                if (logChar != null) {
                    Log.d(TAG, "log char found");
                }
            } else {
                Log.d(TAG, "no logService: ");
            }

            final BluetoothGattService service = gatt.getService(SM_UUID_SERVICE);

            if (service != null) {
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

    LogDataCallback logDataCallback = new LogDataCallback() {

        @Override
        public void onNewLogRead(@NonNull BluetoothDevice device, @NonNull BaseLog log) {
            Log.d(TAG, "onNewLogRead: " + log);

            switch (log.getLogType()) {
                case 1:
                    WaveLog waveLog = (WaveLog) log;
                    Log.d(TAG, " " + waveLog);
                    waveLogMutableLiveData.postValue(waveLog);
                    break;

                case 2:
                    PowerLog powerLog = (PowerLog) log;
                    Log.d(TAG, " " + powerLog);
                    powerLogMutableLiveData.postValue(powerLog);
                    break;
            }

        }
    };

    private int counter = 0;

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new SuMBleManagerGattCallback();
    }

    // 核心的notify callback
    private final RelayDataCallback notifyCallback = new RelayDataCallback() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void printLog(String log) {
            // _todoItems.value = _todoItems.value!! + listOf(item)
            // Objects.requireNonNull(relayMsgLiveDataList.getValue()).add(log);
            // relayMsgLiveDataList.postValue(relayMsgLiveDataList.getValue());
            simpleTextLogLiveData.addNewLog(log);
            counter++;
            uploadContent.postValue("NO." + counter + " >> " + log);

        }

        @Override
        public void transLog(String humanLog) {
            logForHuman.postValue(humanLog);
        }

        @Override
        public void getPowerNow(byte ca, byte cb) {

        }

        @Override
        public void onNewMsg(BaseUploadMsg notifyMsg) {

            switch (notifyMsg.getMsgType()) {
                case NotifyMsgParser.GAME_STATE:

                    gameStateLiveData.postValue((GameState) notifyMsg);
                    break;

                case NotifyMsgParser.SUB_DEVICE_INFO:

                    subDeviceStateLiveData.updateSubDevice((SubDevice) notifyMsg);
                    break;

                case NotifyMsgParser.CMD_MSG_UPLOAD_TRIGGER_INFO:

                    TriggerEvent te = (TriggerEvent) notifyMsg;
                    subDeviceStateLiveData.updateSubDeviceTriggerState(te.isTrig(), te.getColor());

                    break;

                case NotifyMsgParser.CMD_POWER_LIVE_INFO:
                    PowerLiveData pld = (PowerLiveData) notifyMsg;

                    addChannelPowerList(pld.getCaPower(), channelAPowerList, caPower);
                    addChannelPowerList(pld.getCbPower(), channelBPowerList, cbPower);

                    break;
                default:
                    Log.e(TAG, "unknown msg type");
                    Log.e(TAG, ((UnknownMsg) notifyMsg).toString());
                    break;
            }
        }

        // 兜底的
        @Override
        public void onInvalidDataReceived(@NonNull final BluetoothDevice device,
                                          @NonNull final Data data) {
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
