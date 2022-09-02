package com.wulala.blecom.livedata;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wulala.blecom.upload.entity.SubDevice;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleTextLogLiveData extends MutableLiveData<List<String>> {

    private static final String TAG = SimpleTextLogLiveData.class.getSimpleName();

    @NonNull
    private List<String> simpleTextLog;

    public SimpleTextLogLiveData() {
        simpleTextLog = new ArrayList<>();
        postValue(simpleTextLog);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNewLog(String newLog) {
        String hms = LocalTime.now().toString();
        simpleTextLog.add(hms + ": " + newLog);

        List<String> newList = new ArrayList<>();
        newList.addAll(simpleTextLog);

        if (newList.size() > 20) {
            simpleTextLog.remove(0);
        }

        Log.d(TAG, "addNewLog: " + newLog + " size: " + simpleTextLog.size());
        Log.d(TAG, "newList size: " + newList.size());

        postValue(newList);
    }
}
