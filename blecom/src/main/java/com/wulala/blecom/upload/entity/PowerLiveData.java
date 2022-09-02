package com.wulala.blecom.upload.entity;

import static com.wulala.blecom.utils.Tools.comboUint;

public class PowerLiveData implements BaseUploadMsg {

    private Integer caPower = 0, cbPower = 0;

    public Integer getCaPower() {
        return caPower;
    }

    public Integer getCbPower() {
        return cbPower;
    }

    public PowerLiveData(byte[] dataBytes) {
        caPower = comboUint(dataBytes[1], dataBytes[2]);
        cbPower = comboUint(dataBytes[3], dataBytes[4]);
    }

    @Override
    public byte getMsgType() {
        return (byte) 0xE1;
    }

    @Override
    public String toString() {
        return "PowerLiveData{" +
                "caPower=" + caPower +
                ", cbPower=" + cbPower +
                '}';
    }
}
