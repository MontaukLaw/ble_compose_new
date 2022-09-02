package com.wulala.blecom.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
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
    public MutableLiveData<String> getLogForHuman(){
        return newSuMManager.getLogForHuman();
    }

    private BluetoothDevice relayDevice;

    public void clearDevices() {
        scanResultDevicesLiveData.clear();
    }

    public SimpleBLEViewModel(@NonNull Application application) {
        super(application);

        scannerStateLiveData = new ScannerStateLiveData(Utils.isBleEnabled(), Utils.isLocationEnabled(application));
        scanResultDevicesLiveData = new ScanResultDevicesLiveData();
        scanner = BluetoothLeScannerCompat.getScanner();

        Log.d(TAG, "BLEViewModel: init");
        newSuMManager = new NewSuMManager(application);

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
        }else{
            Log.d(TAG, "Not connected: ");
        }
    }

}
