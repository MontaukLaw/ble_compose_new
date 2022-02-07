package com.wulala.blecom.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.wulala.blecom.upload.entity.SubDevice;

import java.util.ArrayList;
import java.util.List;

public class SubDeviceStateLiveData extends LiveData<List<SubDevice>> {

    private final static int ERROR = 99;
    private static final String TAG = SubDeviceStateLiveData.class.getSimpleName();
    @NonNull
    private final List<SubDevice> subDevices;

    private int getListIdxByColorIdx(int colorIdx) {
        for (int i = 0; i < 6; i++) {
            if (subDevices.get(i).getColorIdx() == colorIdx) {
                return i;
            }
        }
        return ERROR;
    }

    public void updateSubDeviceTriggerState(boolean ifTrig, int colorIdx) {
        subDevices.get(getListIdxByColorIdx(colorIdx)).setIfTriggered(ifTrig);
        postValue(subDevices);
    }

    public void updateSubDevice(SubDevice subDevice) {
        subDevice.setIfStateChanged(true);

        int idx = getListIdxByColorIdx(subDevice.getColorIdx());

        if (idx != ERROR) {
            subDevices.set(idx, subDevice);
            postValue(subDevices);
        }
    }

    public List<SubDevice> getSubDevices() {
        return subDevices;
    }

    public SubDeviceStateLiveData() {
        subDevices = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            subDevices.add(new SubDevice(i));
        }
        postValue(subDevices);

        Log.d(TAG, "subDevice size: " + subDevices.size());
    }
}
