package com.wulala.blecom.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.wulala.blecom.livedata.ScanResultDevicesLiveData;
import com.wulala.blecom.scan.ScannerStateLiveData;
import com.wulala.blecom.utils.Utils;

import java.util.List;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

// LiveData在ViewModel中永远是个单例
public class ScannerViewModel extends AndroidViewModel {

    private static final String TAG = ScannerViewModel.class.getSimpleName();
    final BluetoothLeScannerCompat scanner;

    public ScanResultDevicesLiveData getScanResultDevicesLiveData() {
        return scanResultDevicesLiveData;
    }

    // 这个Devices是扫描到的DiscoveredBluetoothDevice的List
    // 单例
    private final ScanResultDevicesLiveData scanResultDevicesLiveData;

    private final ScannerStateLiveData scannerStateLiveData;

    public void clearDevices() {
        scanResultDevicesLiveData.clear();
    }

    public ScannerViewModel(@NonNull Application application) {

        super(application);
        scannerStateLiveData = new ScannerStateLiveData(Utils.isBleEnabled(), Utils.isLocationEnabled(application));
        scanResultDevicesLiveData = new ScanResultDevicesLiveData();
        scanner = BluetoothLeScannerCompat.getScanner();
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
            }
        }

        @Override
        public void onScanFailed(final int errorCode) {
            scannerStateLiveData.scanningStopped();
        }
    };

}
