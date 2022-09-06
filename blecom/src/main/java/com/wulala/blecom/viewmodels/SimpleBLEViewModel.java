package com.wulala.blecom.viewmodels;

import static com.wulala.blecom.utils.Tools.bytesToHex;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wulala.blecom.core.NewSuMManager;
import com.wulala.blecom.livedata.DiscoveredBluetoothDevice;
import com.wulala.blecom.livedata.ScanResultDevicesLiveData;
import com.wulala.blecom.scan.ScannerStateLiveData;
import com.wulala.blecom.utils.Utils;

import java.util.List;

import no.nordicsemi.android.ble.livedata.state.ConnectionState;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

public class SimpleBLEViewModel extends AndroidViewModel {
    private static final String TAG = SimpleBLEViewModel.class.getSimpleName();
    private final NewSuMManager newSuMManager;

    final BluetoothLeScannerCompat scanner;

    private final ScanResultDevicesLiveData scanResultDevicesLiveData;

    private final ScannerStateLiveData scannerStateLiveData;

    public final LiveData<ConnectionState> getConnectionState() {
        return newSuMManager.getState();
    }

    // Human LOG
    public MutableLiveData<String> getLogForHuman() {
        return newSuMManager.getLogForHuman();
    }

    public MutableLiveData<Integer>getChannelAPower(){
        return newSuMManager.getChannelAPower();
    }

    public MutableLiveData<Integer>getChannelBPower(){
        return newSuMManager.getChannelBPower();
    }

    private BluetoothDevice relayDevice;

    private byte[] keepSendingCmd = new byte[18];
    private long keepSendingDelayMs = 0;
    boolean sending = false;

    public void clearDevices() {
        scanResultDevicesLiveData.clear();
    }

    boolean keepPlayWaveA = false;
    boolean keepPlayWaveB = false;

    // byte[][] waveA = {{1, 9, 5}, {1, 9, 10}, {1, 9, 15}, {1, 9, 20}};
    byte[][] waveA = {{2, 8, 20}, {2, 8, 20}, {2, 8, 20}, {2, 8, 20}};
    byte[][] waveB = {{10, (byte) 140, 20}, {10, (byte) 140, 15}, {10, (byte) 140, 10}, {10, (byte) 140, 5}};

    private int caIdxCounter = 0;
    private int cbIdxCounter = 0;

    private int powerCa=0;
    private int powerCb=0;


    private void copyWaveData(byte[] source, int startIdx) {
        keepSendingCmd[startIdx ] = source[0];
        keepSendingCmd[startIdx + 1] = 0;
        keepSendingCmd[startIdx + 2] = source[1];
        keepSendingCmd[startIdx + 3] = source[2];
    }

    // CA: 10, 11, 12, 13
    // CB: 14, 15, 16, 17
    private void buildKeenSendingCmd() {
        keepSendingCmd[0] = (byte)0xB0;
        keepSendingCmd[1] = 0x01;
        keepSendingCmd[2] = (byte)0xFF;
        keepSendingCmd[3] = (byte)0xFF;

        keepSendingCmd[8] = (byte)100;
        keepSendingCmd[9] = (byte)100;

        if (keepPlayWaveA) {
            copyWaveData(waveA[caIdxCounter], 10);

            caIdxCounter ++;
            if(caIdxCounter > 3){
                caIdxCounter = 0;
            }
        }else{
            keepSendingCmd[10] = 0;
            keepSendingCmd[11] = 0;
            keepSendingCmd[12] = 0;
            keepSendingCmd[13] = 0;
        }
        if (keepPlayWaveB) {
            copyWaveData(waveB[cbIdxCounter], 14);

            cbIdxCounter ++;
            if(cbIdxCounter > 3){
                cbIdxCounter = 0;
            }

        }else{
            keepSendingCmd[14] = 0;
            keepSendingCmd[15] = 0;
            keepSendingCmd[16] = 0;
            keepSendingCmd[17] = 0;
        }
        Log.d(TAG, "buildKeenSendingCmd: CA: " + bytesToHex(keepSendingCmd));

    }

    public SimpleBLEViewModel(@NonNull Application application) {
        super(application);

        scannerStateLiveData = new ScannerStateLiveData(Utils.isBleEnabled(), Utils.isLocationEnabled(application));
        scanResultDevicesLiveData = new ScanResultDevicesLiveData();
        scanner = BluetoothLeScannerCompat.getScanner();

        Log.d(TAG, "BLEViewModel: init");
        newSuMManager = new NewSuMManager(application);

        final Handler mHandler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // writeCmd(cmd);
                if (sending) {
                    // writeCmd(keepSendingCmd);

                    Log.d(TAG, "sending: ");
                }
                //每隔n秒循环执行run方法
                mHandler.postDelayed(this, keepSendingDelayMs);
            }
        };
        mHandler.postDelayed(r, 1);

        final Handler waveHandler = new Handler();
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                // writeCmd(cmd);
                if (keepPlayWaveA || keepPlayWaveB) {
                    // writeCmd(keepSendingCmd);
                    buildKeenSendingCmd();
                    // Log.d(TAG, "sending: ");
                    writeCmd(keepSendingCmd);
                }
                //每隔n秒循环执行run方法
                waveHandler.postDelayed(this, 100);
            }
        };

        waveHandler.postDelayed(r2, 1);

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
            Log.d(TAG, "stopScan: ");
            scannerStateLiveData.scanningStopped();
        }
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
            newSuMManager.connect(relayDevice)
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
        newSuMManager.disconnect().enqueue();
    }

    public void writeCmd(byte[] cmd) {
        if (newSuMManager.isConnected()) {
            newSuMManager.writeByteArray(cmd);
        } else {
            Log.d(TAG, "Not connected: ");
        }
    }

    public void keepSendingCmd(byte[] cmd, long delayMillSecs) {
        sending = !sending;

        if (sending) {
            keepSendingDelayMs = delayMillSecs;
            keepSendingCmd = cmd;
        }
    }

    public void keepPlayWaveCB(int powerCb) {
        keepPlayWaveB = !keepPlayWaveB;
        this.powerCb = powerCb;
    }

    public void keepPlayWaveCA(int powerCa) {
        keepPlayWaveA = !keepPlayWaveA;
        this.powerCa = powerCa;
    }
}
