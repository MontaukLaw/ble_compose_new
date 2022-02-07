package com.wulala.blecom.viewmodels;

import static com.wulala.blecom.utils.CmdFactory.START_GAME_SIMPLE_CMD;
import static com.wulala.blecom.utils.CmdTestFactory.testCmdBind;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wulala.blecom.core.SuMBLEManager;
import com.wulala.blecom.entity.Event;
import com.wulala.blecom.livedata.DiscoveredBluetoothDevice;
import com.wulala.blecom.livedata.ScanResultDevicesLiveData;
import com.wulala.blecom.livedata.SimpleTextLogLiveData;
import com.wulala.blecom.livedata.SubDeviceStateLiveData;
import com.wulala.blecom.scan.ScannerStateLiveData;
import com.wulala.blecom.upload.entity.BaseUploadMsg;
import com.wulala.blecom.upload.entity.GameState;
import com.wulala.blecom.upload.entity.TriggerEvent;
import com.wulala.blecom.utils.Utils;

import java.util.List;

import no.nordicsemi.android.ble.livedata.state.ConnectionState;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

public class BLEViewModel extends AndroidViewModel {

    private static final String TAG = BLEViewModel.class.getSimpleName();

    private final SuMBLEManager suMBLEManager;

    final BluetoothLeScannerCompat scanner;

    private final ScanResultDevicesLiveData scanResultDevicesLiveData;

    private final ScannerStateLiveData scannerStateLiveData;

    public final LiveData<GameState> getGameState() {
        return suMBLEManager.getGameStateLiveData();
    }

    public final LiveData<ConnectionState> getConnectionState() {
        return suMBLEManager.getState();
    }

    public SubDeviceStateLiveData getSubDeviceState() {
        return suMBLEManager.getSubDeviceStateLiveData();
    }

    public MutableLiveData<TriggerEvent> getTriggerEventLiveData() {
        return suMBLEManager.getTriggerEventLiveData();
    }

    public MutableLiveData<String> renewLog() {
        return suMBLEManager.getUpload();
    }

    int subDeviceColorInt = 0;

    public SimpleTextLogLiveData getSimpleTextLog() {
        return suMBLEManager.getSimpleTextLogLiveData();
    }

    public MutableLiveData<List<String>> getLogs() {

        return suMBLEManager.getRelayMsgLiveDataList();
    }

    // 中继实例
    private BluetoothDevice relayDevice;

    public ScanResultDevicesLiveData getScanResultDevicesLiveData() {
        return scanResultDevicesLiveData;
    }

    public ScannerStateLiveData getScannerStateLiveData() {
        return scannerStateLiveData;
    }

    public void clearDevices() {
        scanResultDevicesLiveData.clear();
    }

    // 构造
    public BLEViewModel(@NonNull Application application) {
        super(application);

        scannerStateLiveData = new ScannerStateLiveData(Utils.isBleEnabled(), Utils.isLocationEnabled(application));
        scanResultDevicesLiveData = new ScanResultDevicesLiveData();
        scanner = BluetoothLeScannerCompat.getScanner();

        Log.d(TAG, "BLEViewModel: init");
        suMBLEManager = new SuMBLEManager(application);

    }

    // 扫描开启
    public void startScan() {
        if (scannerStateLiveData.isScanning()) {
            return;
        }
        clearDevices();

        // Scanning settings
        final ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(500)
                .setUseHardwareBatchingIfSupported(false)  // old device support
                .build();

        scanner.startScan(null, settings, scanCallback);
        scannerStateLiveData.scanningStarted();
    }

    public void stopScan() {
        if (scannerStateLiveData.isScanning() && scannerStateLiveData.isBluetoothEnabled()) {
            scanner.stopScan(scanCallback);
            scannerStateLiveData.scanningStopped();
        }
    }

    public void getSelectedSubDevice(String subDeviceColor) {
        Log.d(TAG, "getSelectedSubDevice: " + subDeviceColor);

        switch (subDeviceColor) {
            case "黄":
                subDeviceColorInt = 1;
                break;
            case "红":
                subDeviceColorInt = 2;
                break;
            case "紫":
                subDeviceColorInt = 3;
                break;
            case "蓝":
                subDeviceColorInt = 4;
                break;
            case "青":
                subDeviceColorInt = 5;
                break;
            case "绿":
                subDeviceColorInt = 6;
                break;
            default:
                subDeviceColorInt = 7;
                break;
        }
        Log.d(TAG, "subDeviceColor: " + subDeviceColorInt);
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, @NonNull final ScanResult result) {
            Log.d(TAG, "onScanResult: ");

            if (scanResultDevicesLiveData.deviceDiscovered(result)) {
                scanResultDevicesLiveData.applyFilter();
                scannerStateLiveData.recordFound();
                Log.d(TAG, "devicesLiveData len: " + scanResultDevicesLiveData.getValue());
            }
        }

        @Override
        public void onBatchScanResults(@NonNull final List<ScanResult> results) {

            boolean atLeastOneMatchedFilter = false;

            for (final ScanResult result : results) {
                atLeastOneMatchedFilter = scanResultDevicesLiveData.deviceDiscovered(result) || atLeastOneMatchedFilter;
                Log.d(TAG, "atLeastOneMatchedFilter: " + atLeastOneMatchedFilter);
            }

            if (atLeastOneMatchedFilter) {
                Log.d(TAG, "onBatchScanResults: ");

                scanResultDevicesLiveData.applyFilter();
                scannerStateLiveData.recordFound();

                checkDevice(scanResultDevicesLiveData.getFilteredDevices());
            }
        }

        @Override
        public void onScanFailed(final int errorCode) {
            scannerStateLiveData.scanningStopped();
        }
    };

    @SuppressLint("MissingPermission")
    private void checkDevice(List<DiscoveredBluetoothDevice> filteredDevices) {
        if (filteredDevices != null && filteredDevices.size() > 0) {
            Log.d(TAG, "newDevices len: " + filteredDevices.size());
            for (DiscoveredBluetoothDevice device : filteredDevices) {
                Log.d(TAG, "device name is " + device.getDevice().getName());
                Log.d(TAG, "device getAddress is " + device.getAddress());
                Log.d(TAG, "找到了, mac地址为" + device.getAddress());
                // relayDeviceViewModel.connect(device);
                // D6:54:D9:53:44:66 -> my baby
                if (device.getAddress().equals("EA:68:ED:0D:D2:5C")) {
                    break;
                }
                connect(device);
            }
            // 找到就停止扫描
            stopScan();
        }
    }

    private void reconnect() {
        if (relayDevice != null) {
            suMBLEManager.connect(relayDevice)
                    .retry(3, 100)
                    .useAutoConnect(false)
                    .enqueue();
        }
    }

    private void connect(@NonNull final DiscoveredBluetoothDevice target) {

        relayDevice = target.getDevice();
        reconnect();
    }

    // 断开连接
    private void disconnect() {
        relayDevice = null;
        suMBLEManager.disconnect().enqueue();
    }

    private void writeCmd(byte[] cmd) {
        if (suMBLEManager.isConnected()) {
            suMBLEManager.writeByteArray(cmd);
        }
    }

    public void startGame() {
        byte[] cmd = START_GAME_SIMPLE_CMD;
        writeCmd(cmd);
    }

    // 以下仅供测试用
    public void sendTestCmd(byte[] cmd) {
        writeCmd(cmd);
    }

    public void sendEventSetting(Event event) {
        Log.d(TAG, "sendEventSetting: " + event);
    }

    public void keepSendingCmd() {
        final Handler mHandler = new Handler();
        byte[] cmd = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Runnable r = new Runnable() {
            @Override
            public void run() {
                writeCmd(cmd);
                // Log.d(TAG, "running: ");
                //每隔n秒循环执行run方法
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(r, 1);
    }

}
