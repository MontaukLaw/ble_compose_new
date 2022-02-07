package com.wulala.blecom.utils;

public class CmdTestFactory {

    // BIND_PUBLIC_ADV_DEVICE = 0x01, //  绑定可用设备，蓝牙名称包含 DGLAB 字段视为有效
    // UNBIND_DEVICE = 0x04,  // 解绑指定颜色设备
    //
    // GAME_START = 0x11,   // APP通过指令启动配件玩法（如果设置了延时启动那么就开始倒计时，如果设置了条件启动就等待条件）
    // GAME_STOP = 0x12,    // 关闭配件玩法（强制结束配件玩法）
    //
    // TRIGGER_SETTING = 0x20,  // 指定触发ID的触发结果
    // TRIGGER_REMOVE = 0x21,   // 删除指定ID触发结果
    //
    // EVENT_SETTING = 0x30, // 配件触发设置
    //
    // GAME_SETTING = 0x40,  // 游戏配置
    //
    // WAVE_DOWNLOAD = 0xA0, // 波形下载
    // LOAD_ALL_CONFIG = 0xFF,  // 查询所有配置

    public final static byte testCmdBind[] = {0x01};
    public final static byte testCmdUnbind[] = {0x04, 0x05}; // 解绑黄色按钮

    public final static byte testCmdGameStart[] = {0x11}; // 游戏启动了
    public final static byte testCmdGameStop[] = {0x12};  // 停止游戏

    public final static byte testCmdTriggerSetting[] = {0x30};   // 指定触发ID的触发结果


    public final static byte testCmdEventSetting[] = {0x20};   // 配件Event设置
    public final static byte testCmdRemoveEvent[] = {0x21};   // 删除Event

    public final static byte testCmdGameSetting[] = {0x40};     // 游戏配置
    public final static byte testCmdDownloadWave[] = {(byte) 0xA0};    // 波形下载
    public final static byte testCmdLoadAll[] = {(byte) 0xFF};    // 查询所有配置

}
