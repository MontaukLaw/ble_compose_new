package com.wulala.blecom.upload.log.entity;

public class WaveLog implements BaseLog {

    private int bgCAWaveId;
    private int bgCAWaveIdx;

    private int bgCBWaveId;
    private int bgCBWaveIdx;

    private int punishCAWaveId;
    private int punishCAWaveIdx;

    private int punishCBWaveId;
    private int punishCBWaveIdx;

    @Override
    public String toString() {
        return "WaveLog{" +
                "bgCAWaveId=" + bgCAWaveId +
                ", bgCAWaveIdx=" + bgCAWaveIdx +
                ", bgCBWaveId=" + bgCBWaveId +
                ", bgCBWaveIdx=" + bgCBWaveIdx +
                ", punishCAWaveId=" + punishCAWaveId +
                ", punishCAWaveIdx=" + punishCAWaveIdx +
                ", punishCBWaveId=" + punishCBWaveId +
                ", punishCBWaveIdx=" + punishCBWaveIdx +
                '}';
    }

    public int getBgCAWaveId() {
        return bgCAWaveId;
    }

    public void setBgCAWaveId(int bgCAWaveId) {
        this.bgCAWaveId = bgCAWaveId;
    }

    public int getBgCAWaveIdx() {
        return bgCAWaveIdx;
    }

    public void setBgCAWaveIdx(int bgCAWaveIdx) {
        this.bgCAWaveIdx = bgCAWaveIdx;
    }

    public int getBgCBWaveId() {
        return bgCBWaveId;
    }

    public void setBgCBWaveId(int bgCBWaveId) {
        this.bgCBWaveId = bgCBWaveId;
    }

    public int getBgCBWaveIdx() {
        return bgCBWaveIdx;
    }

    public void setBgCBWaveIdx(int bgCBWaveIdx) {
        this.bgCBWaveIdx = bgCBWaveIdx;
    }

    public int getPunishCAWaveId() {
        return punishCAWaveId;
    }

    public void setPunishCAWaveId(int punishCAWaveId) {
        this.punishCAWaveId = punishCAWaveId;
    }

    public int getPunishCAWaveIdx() {
        return punishCAWaveIdx;
    }

    public void setPunishCAWaveIdx(int punishCAWaveIdx) {
        this.punishCAWaveIdx = punishCAWaveIdx;
    }

    public int getPunishCBWaveId() {
        return punishCBWaveId;
    }

    public void setPunishCBWaveId(int punishCBWaveId) {
        this.punishCBWaveId = punishCBWaveId;
    }

    public int getPunishCBWaveIdx() {
        return punishCBWaveIdx;
    }

    public void setPunishCBWaveIdx(int punishCBWaveIdx) {
        this.punishCBWaveIdx = punishCBWaveIdx;
    }

    public WaveLog(int bgCAWaveId, int bgCAWaveIdx, int bgCBWaveId, int bgCBWaveIdx, int punishCAWaveId, int punishCAWaveIdx, int punishCBWaveId, int punishCBWaveIdx) {
        this.bgCAWaveId = bgCAWaveId;
        this.bgCAWaveIdx = bgCAWaveIdx;
        this.bgCBWaveId = bgCBWaveId;
        this.bgCBWaveIdx = bgCBWaveIdx;
        this.punishCAWaveId = punishCAWaveId;
        this.punishCAWaveIdx = punishCAWaveIdx;
        this.punishCBWaveId = punishCBWaveId;
        this.punishCBWaveIdx = punishCBWaveIdx;

    }

    @Override
    public int getLogType() {
        return 1;
    }
}
