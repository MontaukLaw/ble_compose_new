package com.wulala.blecom.utils;

public class UploadMsgUtils {

    private final static String ERROR_02 = "命令报文格式不正确";

    private final static String ERROR_03 = "工作状态不正确";

    public static String getSubDeviceColor(byte b) {
        switch (b) {
            case 1:
                return "黄色";
            case 2:
                return "红色";
            case 3:
                return "紫色";
            case 4:
                return "蓝色";
            case 5:
                return "青色";
            case 6:
                return "绿色";
            default:
                return "未知颜色";
        }
    }

    public static String getSubDeviceType(byte b) {
        switch (b) {
            case 1:
                return "按钮";
            case 2:
                return "项圈";
            default:
                return "未知类型";
        }
    }

    public static String ifDeviceOnline(byte b) {
        if (b == 0x01) {
            return "在线";
        } else {
            return "离线";
        }
    }

    public static String getSubDeviceState(byte[] uploadBytes) {

        String result = getSubDeviceColor(uploadBytes[1]);

        result = result + getSubDeviceType(uploadBytes[2]);

        result = result + " 电量:" + (int) uploadBytes[3] + "% ";

        result = result + ifDeviceOnline(uploadBytes[4]);

        return result;
    }

    public static String getGameState(byte b) {
        switch (b) {
            case 0x01:
                return "待机状态";

            case 0x02:
                return "等待开始";

            case 0x03:
                return "游戏中";

            default:
                return "未知状态";
        }
    }

    public static String getGameStateDetail(byte[] uploadBytes) {

        String result = getGameState(uploadBytes[1]);

        int countDownSec = Tools.comboUint(uploadBytes[2], uploadBytes[3]);

        if (countDownSec != 0) {
            result = result + "倒计时: " + countDownSec + "秒";
        }

        return result;
    }

    public static String getTriggerMsgStr(byte[] uploadBytes) {

        String result = getSubDeviceColor(uploadBytes[1]);
        result = result + "子设备触发了Event:" + (int) uploadBytes[2];
        return result;
    }

    public static String translateLogFoHuman(byte[] uploadBytes) {

        switch (uploadBytes[0]) {
            case (byte) 0x0F:
                return getSubDeviceState(uploadBytes);

            case (byte) 0x91:
                return getTriggerMsgStr(uploadBytes);

            case 0x02:
                return "成功连接到合法设备并停止扫描";

            case 0x03:
                return "扫描超时停止";

            case 0x04:
                return "绑定到旧设备";

            case 0x09:
                return "解绑成功";

            case 0x10:
            case (byte) 0xf1:
                return getGameStateDetail(uploadBytes);

            case 0x21:
                return "Event保存";

            case (byte) 0xE0:
                // E0-40-02
                if (uploadBytes[2] == 0x02) {
                    return "cmd 0x" + Integer.toHexString(uploadBytes[1]) + " 错误: " + ERROR_02;
                } else if (uploadBytes[2] == 0x03) {
                    return "cmd 0x" + Integer.toHexString(uploadBytes[1]) + " 错误: " + ERROR_03;
                } else {
                    return "cmd 0x" + Integer.toHexString(uploadBytes[1]) + " 错误: " + "其他错误";
                }

            case (byte) 0xB1:
                return "B1反馈id:" + uploadBytes[1] + " A通道强度: " + uploadBytes[2] + " B通道强度: "
                        + uploadBytes[3] + " A通道软上限: " + uploadBytes[4] + " B通道软上限: " + uploadBytes[5];

            default:
                return "暂时未知类型的消息";

        }

    }
}


//  BIND_PUBLIC_ADV_DEVICE_SUCCESS = 0x02,  // 成功连接到合法设备并停止扫描，将该设备信息发给APP  20220216 重新整理过
//  BIND_PUBLIC_ADV_DEVICE_OVER_TIME = 0x03,  // 扫描超时停止  20220216 重新整理过
//
//  BOND_OLD_DEVICE = 0x04, // 202202616 绑定到旧设备
//
//  UNBIND_DEVICE_SUCCESS = 0x09,  // 指定颜色设备解绑成功  20220216 变更为0x09
//  // UNBIND_DEVICE_NO_DEVICE_ERROR = 0x06,  // 指定颜色的设备在电击器内存中不存在 202202616, 这条错误码被移动到了归纳性的表中.
//
//  SUB_DEVICE_STATE_UPDATE = 0x0F,  // 设备电量或者连接状态发生变化时上传
//
//  GAME_STATE = 0x10, // 上传当前处于的工作状态和倒计时
//
//  EVENT_SETTING_SAVE_FB = 0x21, // 20220216 新增
//  EVENT_SETTING_REMOVE_FB = 0x29,  // 20220216 新增
//
//  TRIGGER_SAVE_SUCCESS = 0x31, // 20220216 新增
//
//  GAME_SETTING_SUCCESS_FB = 0x41, // 20220216 新增
//
//  EVENT_TRIGGERED = 0x90, // 向APP发送触发消息
//  EVENT_TRIGGER_CANCELED = 0x91, // 向APP发送触发取消消息
//
//  WAVE_DOWNLOAD_GOOD = 0xA2, // 收到全部包后向APP回传校验CRC
//
//  LOAD_CONFIG_HEAD = 0xF0, // 包头：+包数量+数据长度+CRC
//  LOAD_CONFIG_GAME_STATE = 0xF1,  // 当前配件玩法的工作状态以及剩余时间
//  LOAD_CONFIG_EVENT_SETTING = 0xF2,  // 触发Event设置
//  LOAD_CONFIG_SUB_DEVICE_STATE_AND_CONFIG = 0xF3,  // 配件本身的状态以及配件触发设置
//  LOAD_CONFIG_GAME_SETTING = 0xF4,  // 启动/停止设置
//
//  CMD_GENERAL_FB = 0xE0,
//  LIVE_POWER = 0xE1,
