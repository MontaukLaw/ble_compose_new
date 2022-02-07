package com.wulala.blecom.livedata;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class DiscoveredBluetoothDevice implements Parcelable {

    private static final String TAG = DiscoveredBluetoothDevice.class.getSimpleName();

    private final BluetoothDevice device;

    private String name;

    // 最新的扫描结果
    private ScanResult lastScanResult;

    // 传入ScanResult构造一个已经发现的BLE设备
    public DiscoveredBluetoothDevice(@NonNull final ScanResult scanResult) {
        device = scanResult.getDevice();
        update(scanResult);
    }


    @NonNull
    public ScanResult getScanResult() {
        return lastScanResult;
    }

    // 返回数据实体
    @NonNull
    public BluetoothDevice getDevice() {
        return device;
    }

    // 获取mac地址
    @NonNull
    public String getAddress() {
        return device.getAddress();
    }

    // 获取设备名称
    @NonNull
    public String getName() {
        // 有时候广播包里面不包含设备名称, 所以这里是个NonNull的类型
        return name;
    }

    public void update(@NonNull final ScanResult scanResult) {
        lastScanResult = scanResult;
        name = scanResult.getScanRecord() != null ? scanResult.getScanRecord().getDeviceName() : null;
        Log.d(TAG, "update: " + name + " mac: " + scanResult.getDevice().getAddress());
    }

    // 用mac来检查是否已经发现过这个从机
    // 避免重复记录
    public boolean matches(@NonNull final ScanResult scanResult) {
        return device.getAddress().equals(scanResult.getDevice().getAddress());
    }

    @Override
    public int hashCode() {
        return device.hashCode();
    }

    // 重写equals, mac地址相同, 就算同一个从机
    @Override
    public boolean equals(final Object o) {
        // iterate compare all items
        if (o instanceof DiscoveredBluetoothDevice) {
            final DiscoveredBluetoothDevice that = (DiscoveredBluetoothDevice) o;
            return device.getAddress().equals(that.device.getAddress());
        }
        return super.equals(o);
    }

    // 序列化的相关实现
    private DiscoveredBluetoothDevice(final Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        name = in.readString();
    }

    // 序列化的相关实现
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeParcelable(device, flags);
        parcel.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // 这部分是实现的Parcelable必须的方法
    public static final Creator<DiscoveredBluetoothDevice> CREATOR = new Creator<DiscoveredBluetoothDevice>() {
        @Override
        public DiscoveredBluetoothDevice createFromParcel(final Parcel source) {
            return new DiscoveredBluetoothDevice(source);
        }

        @Override
        public DiscoveredBluetoothDevice[] newArray(final int size) {
            return new DiscoveredBluetoothDevice[size];
        }
    };

}
