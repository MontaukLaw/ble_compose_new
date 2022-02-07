package com.wulala.blecom.upload.entity;

import com.wulala.blecom.upload.NotifyMsgParser;
import com.wulala.blecom.utils.Tools;

// 游戏状态
public class GameState implements BaseUploadMsg {

    private String gameStateStr;
    private int countDownTimeSec;
    private final static byte STANDBY = 0x01;
    private final static byte WAIT_FOR_START = 0x02;
    private final static byte GAMING = 0x03;

    private boolean ifGaming = false;

    public boolean isIfGaming() {
        return ifGaming;
    }

    public GameState(byte[] dataBytes) {

        switch (dataBytes[1]) {
            case STANDBY:
                gameStateStr = "待机状态";
                ifGaming = false;
                break;

            case WAIT_FOR_START:
                gameStateStr = "等待开始";
                countDownTimeSec = Tools.comboUint(dataBytes[2], dataBytes[3]);
                ifGaming = false;
                break;

            case GAMING:
                gameStateStr = "游戏中";
                countDownTimeSec = Tools.comboUint(dataBytes[2], dataBytes[3]);
                ifGaming = true;
                break;
        }
    }

    public String getGameStateStr() {
        return gameStateStr;
    }

    public void setDeviceState(String deviceState) {
        this.gameStateStr = deviceState;
    }

    public int getCountDownTimeSec() {
        return countDownTimeSec;
    }

    public String getCountDownTimeSecText() {
        return "剩余时间: " + countDownTimeSec + "秒";
    }

    public void setCountDownTimeSec(int countDownTimeSec) {
        this.countDownTimeSec = countDownTimeSec;
    }

    @Override
    public String toString() {
        return "DeviceState{" +
                "deviceState='" + gameStateStr + '\'' +
                ", countDownTimeSec=" + countDownTimeSec +
                '}';
    }

    @Override
    public byte getMsgType() {
        return NotifyMsgParser.GAME_STATE;
    }

}
