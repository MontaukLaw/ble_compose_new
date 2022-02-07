package com.wulala.blecom.scan;

import androidx.lifecycle.LiveData;

public class ScannerStateLiveData extends LiveData<ScannerStateLiveData> {

    private boolean scanningStarted;

    public boolean isScanningStarted() {
        return scanningStarted;
    }

    private boolean bluetoothEnabled;
    private boolean locationEnabled;
    private boolean hasRecords;

    public ScannerStateLiveData(final boolean bluetoothEnabled,
                                final boolean locationEnabled) {
        this.scanningStarted = false;
        this.bluetoothEnabled = bluetoothEnabled;
        this.locationEnabled = locationEnabled;
        postValue(this);
    }

    public ScannerStateLiveData() {
    }

    void refresh() {
        postValue(this);
    }

    public void scanningStarted() {
        scanningStarted = true;
        postValue(this);
    }

    public void scanningStopped() {
        scanningStarted = false;
        postValue(this);
    }

    synchronized void bluetoothDisabled() {
        bluetoothEnabled = false;
        hasRecords = false;
        postValue(this);
    }

    public void setLocationEnabled(final boolean enabled) {
        locationEnabled = enabled;
        postValue(this);
    }

    public void recordFound() {
        hasRecords = true;
        postValue(this);
    }

    public boolean isScanning() {
        return scanningStarted;
    }

    public boolean hasRecords() {
        return hasRecords;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    public void clearRecords() {
        hasRecords = false;
        postValue(this);
    }
}
