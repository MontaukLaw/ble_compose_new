package com.wulala.blecom.upload.entity;

import static com.wulala.blecom.upload.NotifyMsgParser.CMD_MSG_UPLOAD_TRIGGER_INFO;

public class TriggerEvent implements BaseUploadMsg {


    private int eventId;
    private int color;

    private boolean trig;

    public boolean isTrig() {
        return trig;
    }

    public TriggerEvent(byte[] dataBytes, boolean triggerState) {
        color = dataBytes[1];
        eventId = dataBytes[2];
        trig = triggerState;
    }

    public int getEventId() {
        return eventId;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "TriggerEvent{" +
                "eventId=" + eventId +
                ", color=" + color +
                '}';
    }

    @Override
    public byte getMsgType() {
        return CMD_MSG_UPLOAD_TRIGGER_INFO;
    }
}
