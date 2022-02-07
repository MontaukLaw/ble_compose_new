package com.wulala.blecom.livedata;

import android.os.ParcelUuid;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.wulala.blecom.core.SuMBLEManager;

import java.util.ArrayList;
import java.util.List;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class ScanResultDevicesLiveData extends LiveData<List<DiscoveredBluetoothDevice>> {

    private static final String TAG = ScanResultDevicesLiveData.class.getSimpleName();

    // 使用蓝牙名称过滤
    private boolean filterOurDeviceOnly;

    @NonNull
    private final List<DiscoveredBluetoothDevice> devices = new ArrayList<>();

    @Nullable
    public List<DiscoveredBluetoothDevice> getFilteredDevices() {
        return filteredDevices;
    }
    // 实际的数据List
    @Nullable
    private List<DiscoveredBluetoothDevice> filteredDevices = null;

    // Constructor
    public ScanResultDevicesLiveData() {
    }

    // 仅防蓝牙被突然禁用
    synchronized void bluetoothDisabled() {
        devices.clear();
        filteredDevices = null;
        postValue(null);
    }

    public synchronized void clear() {
        devices.clear();
        filteredDevices = null;
        postValue(null);
    }

    public boolean filterByName(final boolean ourDeviceOnly) {
        filterOurDeviceOnly = ourDeviceOnly;
        return applyFilter();
    }

    public synchronized boolean applyFilter() {
        final List<DiscoveredBluetoothDevice> tmp = new ArrayList<>();
        for (final DiscoveredBluetoothDevice device : devices) {
            final ScanResult result = device.getScanResult();
            // 将现有的所有devices根据是否筛选以及筛选的条件分别筛选, 之后形成filteredDevices
            if (matchesOurDeviceFilter(result)) {
                tmp.add(device);
                Log.d(TAG, "found : " + tmp.size());
            }
        }
        filteredDevices = tmp;
        postValue(filteredDevices);
        return !filteredDevices.isEmpty();
    }


    // 根据device Name检查是否是想要的device
    private boolean matchesOurDeviceFilter(@NonNull final ScanResult result) {

        final ScanRecord record = result.getScanRecord();
        if (record == null)
            return false;

        final String deviceName = record.getDeviceName();
        if (deviceName == null)
            return false;

        Log.d(TAG, "matchesOurDeviceFilter: " + deviceName);
        return deviceName.equals(SuMBLEManager.SM_BLE_NAME);
    }

    private int indexOf(@NonNull final ScanResult result) {
        int i = 0;
        for (final DiscoveredBluetoothDevice device : devices) {
            if (device.matches(result))
                return i;
            i++;
        }
        return -1;
    }

    public synchronized boolean deviceDiscovered(@NonNull final ScanResult result) {
        DiscoveredBluetoothDevice device;

        // Check if it's a new device.
        final int index = indexOf(result);
        if (index == -1) {
            device = new DiscoveredBluetoothDevice(result);
            devices.add(device);
        } else {
            device = devices.get(index);
        }

        // Update RSSI and name, it can be null or keep changing
        device.update(result);

        // Return true if the device was on the filtered list or is to be added.
        // return (filteredDevices != null && filteredDevices.contains(device));
        return true;
    }

}
