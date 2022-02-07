package com.wulala.blecom.upload.entity;

import java.util.Arrays;

public class UnknownMsg implements BaseUploadMsg{

    byte[] mData;

    public UnknownMsg(byte[] dataBytes) {
        mData = dataBytes;
    }

    @Override
    public String toString() {
        return "UnknownMsg{" +
                "mData=" + Arrays.toString(mData) +
                '}';
    }
    @Override
    public byte getMsgType() {
        return 0;
    }
}
