package com.wulala.blecom.utils;

public class EventCmdBuilder {

    private final static int CMD_LEN = 20;
    private final static Byte EVENT_CMD_HEAD = 0x20;

    public static Byte[] build(Byte eventId, Byte ifMultiState,
                               Byte caType, Byte caFrom, Byte caTo,
                               Byte cbType, Byte cbFrom, Byte cbTo,
                               Byte baseAFrom, Byte baseAto, Byte baseBFrom, Byte baseBTo,
                               int timeChangeSecFrom, int timeChangeSecTo,
                               Byte caWave, Byte cbWave
    ) {
        Byte[] rtn = new Byte[CMD_LEN];

        rtn[0] = 0x20;
        rtn[1] = eventId;
        rtn[2] = ifMultiState;  // 0x01 一次性事件 0x00 多次事件
        rtn[3] = 0x00;  // 不立刻停止 // todo

        rtn[4] = caWave;  // 通道A 波形 id
        rtn[5] = cbWave;  // 通道B 波形 id

        // 指定通道A强度变化
        rtn[6] = caType;  // 固定
        rtn[7] = caFrom;  // 固定模式第一个值有效, 第二个无效
        rtn[8] = caTo;

        // 指定通道B强度变化
        rtn[9] = cbType;  // 每0.5秒强度变化1
        rtn[10] = cbFrom;  // 强度变化起始值
        rtn[11] = cbTo;  // 强度变化结束值

        // 基础强度变化
        rtn[12] = baseAFrom;  // 通道A强度变化
        rtn[13] = baseAto;
        rtn[14] = baseBFrom;
        rtn[15] = baseBTo;

        // 游戏剩余时间改变
        rtn[16] = (byte) (timeChangeSecFrom >> 8);
        rtn[17] = (byte) (timeChangeSecFrom);

        rtn[18] = (byte) (timeChangeSecTo >> 8);
        rtn[19] = (byte) (timeChangeSecTo);

        return rtn;
    }
}
