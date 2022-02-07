package com.wulala.blecom.upload;

import com.wulala.blecom.upload.entity.BaseUploadMsg;
import com.wulala.blecom.upload.entity.GameState;
import com.wulala.blecom.upload.entity.SubDevice;
import com.wulala.blecom.upload.entity.TriggerEvent;
import com.wulala.blecom.upload.entity.UnknownMsg;

// 这里应考虑使用泛型
public class NotifyMsgParser {
    // 游戏状态
    public final static byte GAME_STATE = 0x32;

    // 子设备信息
    public final static byte SUB_DEVICE_INFO = 0x01;

    public final static byte CMD_MSG_UPLOAD_TRIGGER_INFO = (byte)0xFC;

    public final static byte CMD_MSG_UPLOAD_CANCEL_TRIGGER_INFO = (byte)0xFD;

    public static BaseUploadMsg parse(byte[] dataBytes) {
        byte b = dataBytes[0];
        BaseUploadMsg rtnMsg;
        switch (b) {
            case GAME_STATE:
                rtnMsg = new GameState(dataBytes);
                break;

            case SUB_DEVICE_INFO:
                rtnMsg = new SubDevice(dataBytes);
                break;

            case CMD_MSG_UPLOAD_TRIGGER_INFO:
                rtnMsg = new TriggerEvent(dataBytes, true);
                break;

            case CMD_MSG_UPLOAD_CANCEL_TRIGGER_INFO:
                rtnMsg = new TriggerEvent(dataBytes, false);
                break;

            default:
                rtnMsg = new UnknownMsg(dataBytes);
                break;
        }

        return rtnMsg;
    }
}
