package com.wulala.blecom.entity;

public class Event {
    private byte id, ifMultiState, caType, caFrom, caTo, cbType, cbFrom, cbTo;
    private byte baseAFrom, baseATo, baseBFrom, baseBTo, caWave, cbWave;
    private int timeChangeSecFrom, timeChangeSecTo;

    public Event(byte id, byte ifMultiState, byte caType, byte caFrom, byte caTo,
                 byte cbType, byte cbFrom, byte cbTo, byte baseAFrom, byte baseATo,
                 byte baseBFrom, byte baseBTo, int timeChangeSecFrom, int timeChangeSecTo,
                 byte caWave, byte cbWave) {
        this.id = id;
        this.ifMultiState = ifMultiState;
        this.caType = caType;
        this.caFrom = caFrom;
        this.caTo = caTo;
        this.cbType = cbType;
        this.cbFrom = cbFrom;
        this.cbTo = cbTo;
        this.baseAFrom = baseAFrom;
        this.baseATo = baseATo;
        this.baseBFrom = baseBFrom;
        this.baseBTo = baseBTo;
        this.timeChangeSecFrom = timeChangeSecFrom;
        this.timeChangeSecTo = timeChangeSecTo;
        this.caWave = caWave;
        this.cbWave = cbWave;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", ifMultiState=" + ifMultiState +
                ", caType=" + caType +
                ", caFrom=" + caFrom +
                ", caTo=" + caTo +
                ", cbType=" + cbType +
                ", cbFrom=" + cbFrom +
                ", cbTo=" + cbTo +
                ", baseAFrom=" + baseAFrom +
                ", baseATo=" + baseATo +
                ", baseBFrom=" + baseBFrom +
                ", baseBTo=" + baseBTo +
                ", timeChangeSecFrom=" + timeChangeSecFrom +
                ", timeChangeSecTo=" + timeChangeSecTo +
                ", caWave=" + caWave +
                ", cbWave=" + cbWave +
                '}';
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public byte getIfMultiState() {
        return ifMultiState;
    }

    public void setIfMultiState(byte ifMultiState) {
        this.ifMultiState = ifMultiState;
    }

    public byte getCaType() {
        return caType;
    }

    public void setCaType(byte caType) {
        this.caType = caType;
    }

    public byte getCaFrom() {
        return caFrom;
    }

    public void setCaFrom(byte caFrom) {
        this.caFrom = caFrom;
    }

    public byte getCaTo() {
        return caTo;
    }

    public void setCaTo(byte caTo) {
        this.caTo = caTo;
    }

    public byte getCbType() {
        return cbType;
    }

    public void setCbType(byte cbType) {
        this.cbType = cbType;
    }

    public byte getCbFrom() {
        return cbFrom;
    }

    public void setCbFrom(byte cbFrom) {
        this.cbFrom = cbFrom;
    }

    public byte getCbTo() {
        return cbTo;
    }

    public void setCbTo(byte cbTo) {
        this.cbTo = cbTo;
    }

    public byte getBaseAFrom() {
        return baseAFrom;
    }

    public void setBaseAFrom(byte baseAFrom) {
        this.baseAFrom = baseAFrom;
    }

    public byte getBaseATo() {
        return baseATo;
    }

    public void setBaseATo(byte baseATo) {
        this.baseATo = baseATo;
    }

    public byte getBaseBFrom() {
        return baseBFrom;
    }

    public void setBaseBFrom(byte baseBFrom) {
        this.baseBFrom = baseBFrom;
    }

    public byte getBaseBTo() {
        return baseBTo;
    }

    public void setBaseBTo(byte baseBTo) {
        this.baseBTo = baseBTo;
    }

    public int getTimeChangeSecFrom() {
        return timeChangeSecFrom;
    }

    public void setTimeChangeSecFrom(int timeChangeSecFrom) {
        this.timeChangeSecFrom = timeChangeSecFrom;
    }

    public int getTimeChangeSecTo() {
        return timeChangeSecTo;
    }

    public void setTimeChangeSecTo(int timeChangeSecTo) {
        this.timeChangeSecTo = timeChangeSecTo;
    }

    public byte getCaWave() {
        return caWave;
    }

    public void setCaWave(byte caWave) {
        this.caWave = caWave;
    }

    public byte getCbWave() {
        return cbWave;
    }

    public void setCbWave(byte cbWave) {
        this.cbWave = cbWave;
    }
}
