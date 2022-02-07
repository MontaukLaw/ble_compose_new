package com.wulala.blecom.upload.entity;

import com.wulala.blecom.upload.NotifyMsgParser;

// 0x01 颜色（1字节）+子设备类型（1字节）+电量百分比（1字节）+连接状态（1字节）
public class SubDevice implements BaseUploadMsg {

    private String color = "";

    private int colorIdx = 0;
    private int batVolPercentage = 0;
    private String deviceType = "";
    private String connectionState = "";

    private boolean ifTriggered = false;

    public boolean isIfTriggered() {
        return ifTriggered;
    }

    public void setIfTriggered(boolean ifTriggered) {
        this.ifTriggered = ifTriggered;
    }

    public boolean isIfStateChanged() {
        if (ifStateChanged) {
            ifStateChanged = false;
            return true;
        }
        return false;
    }

    public void setIfStateChanged(boolean ifStateChanged) {
        this.ifStateChanged = ifStateChanged;
    }

    private boolean ifStateChanged = false;

    public SubDevice(int mColorIdx) {
        colorIdx = mColorIdx;
    }

    public SubDevice(byte[] dataBytes) {
        switch (dataBytes[1]) {
            case 0x01:
                color = "黄";
                colorIdx = 1;
                break;

            case 0x02:
                color = "红";
                colorIdx = 2;
                break;

            case 0x03:
                color = "紫";
                colorIdx = 3;
                break;

            case 0x04:
                color = "蓝";
                colorIdx = 4;

                break;

            case 0x05:
                color = "青";
                colorIdx = 5;

                break;

            case 0x06:
                color = "绿";
                colorIdx = 6;

                break;

            default:
                color = "未知";
                colorIdx = 0;
                break;

        }

        // 类型
        switch (dataBytes[2]) {
            case 0x01:
                deviceType = "按钮";
                break;

            case 0x02:
                deviceType = "项圈";
                break;

            case 0:
                deviceType = "未知";
                break;
        }

        batVolPercentage = dataBytes[3];

        switch (dataBytes[4]) {
            case 0:
                connectionState = "离线";
                break;
            case 1:
                connectionState = "在线";
                break;
        }
    }

    public String getColor() {
        return color;
    }

    public int getBatVolPercentage() {
        return batVolPercentage;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getConnectionState() {
        return connectionState;
    }

    public int getColorIdx() {
        return colorIdx;
    }

    @Override
    public byte getMsgType() {
        return NotifyMsgParser.SUB_DEVICE_INFO;
    }
}
