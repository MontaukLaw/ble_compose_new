package com.wulala.blecom.upload.log.entity;


public class PowerLog implements BaseLog {

    private int baseDutyA, baseDutyB;
    private int fixA, fixB;
    private int tempGradientPowerA, tempGradientPowerB;
    private int outputA, outputB;

    public PowerLog() {
    }

    @Override
    public String toString() {
        return "PowerLog{" +
                "baseDutyA=" + baseDutyA +
                ", baseDutyB=" + baseDutyB +
                ", fixA=" + fixA +
                ", fixB=" + fixB +
                ", tempGradientPowerA=" + tempGradientPowerA +
                ", tempGradientPowerB=" + tempGradientPowerB +
                ", outputA=" + outputA +
                ", outputB=" + outputB +
                '}';
    }

    public int getBaseDutyA() {
        return baseDutyA;
    }

    public void setBaseDutyA(int baseDutyA) {
        this.baseDutyA = baseDutyA;
    }

    public int getBaseDutyB() {
        return baseDutyB;
    }

    public void setBaseDutyB(int baseDutyB) {
        this.baseDutyB = baseDutyB;
    }

    public int getFixA() {
        return fixA;
    }

    public void setFixA(int fixA) {
        this.fixA = fixA;
    }

    public int getFixB() {
        return fixB;
    }

    public void setFixB(int fixB) {
        this.fixB = fixB;
    }

    public int getTempGradientPowerA() {
        return tempGradientPowerA;
    }

    public void setTempGradientPowerA(int tempGradientPowerA) {
        this.tempGradientPowerA = tempGradientPowerA;
    }

    public int getTempGradientPowerB() {
        return tempGradientPowerB;
    }

    public void setTempGradientPowerB(int tempGradientPowerB) {
        this.tempGradientPowerB = tempGradientPowerB;
    }

    public int getOutputA() {
        return outputA;
    }

    public void setOutputA(int outputA) {
        this.outputA = outputA;
    }

    public int getOutputB() {
        return outputB;
    }

    public void setOutputB(int outputB) {
        this.outputB = outputB;
    }

    public PowerLog(int baseDutyA, int baseDutyB, int fixA, int fixB, int tempGradientPowerA, int tempGradientPowerB, int outputA, int outputB) {
        this.baseDutyA = baseDutyA;
        this.baseDutyB = baseDutyB;
        this.fixA = fixA;
        this.fixB = fixB;
        this.tempGradientPowerA = tempGradientPowerA;
        this.tempGradientPowerB = tempGradientPowerB;
        this.outputA = outputA;
        this.outputB = outputB;
    }

    @Override
    public int getLogType() {
        return 2;
    }
}
