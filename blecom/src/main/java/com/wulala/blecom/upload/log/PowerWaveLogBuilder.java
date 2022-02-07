package com.wulala.blecom.upload.log;

import android.util.Log;

import com.wulala.blecom.upload.log.entity.BaseLog;
import com.wulala.blecom.upload.log.entity.PowerLog;
import com.wulala.blecom.upload.log.entity.WaveLog;

import static com.wulala.blecom.utils.Tools.*;

public class PowerWaveLogBuilder {

    private static final String TAG = "PowerWaveLogBuilder";

    public static BaseLog parsePowerLog(final byte[] bytes) {

        if(bytes.length == 0){
            Log.d(TAG, "null.. ");
            return null;
        }

        Log.d(TAG, "bytes len: " + bytes.length);

        if (bytes[0] == 0x02) {
            PowerLog baseLog = new PowerLog();
            baseLog.setBaseDutyA(comboUint(bytes[1], bytes[2]));
            baseLog.setBaseDutyB(comboUint(bytes[3], bytes[4]));

            //02 00 8C 00 8C 00 8C 00 00 00 8C 00 46 01 18 00 D2 00 00
            baseLog.setFixA(comboInt(bytes[5], bytes[6]));
            baseLog.setFixB(comboInt(bytes[7], bytes[8]));

            baseLog.setTempGradientPowerA(comboInt(bytes[9], bytes[10]));
            baseLog.setTempGradientPowerB(comboInt(bytes[11], bytes[12]));

            baseLog.setOutputA(comboUint(bytes[13], bytes[14]));
            baseLog.setOutputB(comboUint(bytes[15], bytes[16]));
            return baseLog;

        } else if ((bytes[0] == 0x03)) {
            WaveLog waveLog = new WaveLog(bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7], bytes[8]);
            return  waveLog;
        }

        return null;
    }
}
