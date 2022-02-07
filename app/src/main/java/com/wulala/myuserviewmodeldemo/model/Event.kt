package com.wulala.myuserviewmodeldemo.model

import androidx.lifecycle.LiveData
import com.wulala.blecom.entity.Event
import com.wulala.blecom.utils.Tools

fun EventBuilder(
    id: String,
    ifMultiState: String,
    caType: String, caFrom: String, caTo: String,
    cbType: String, cbFrom: String, cbTo: String,
    baseAFrom: String, baseATo: String, baseBFrom: String, baseBTo: String,
    timeChangeSecFrom: String, timeChangeSecTo: String,
    caWave: String, cbWave: String
): Event {
    return Event(
        Tools.DecString2Byte(id), Tools.DecString2Byte(ifMultiState),
        Tools.DecString2Byte(caType), Tools.DecString2Byte(caFrom), Tools.DecString2Byte(caTo),
        Tools.DecString2Byte(cbType), Tools.DecString2Byte(cbFrom), Tools.DecString2Byte(cbTo),
        Tools.DecString2Byte(baseAFrom), Tools.DecString2Byte(baseATo), Tools.DecString2Byte(baseBFrom), Tools.DecString2Byte(baseBTo),
        Tools.String2Int(timeChangeSecFrom), Tools.String2Int(timeChangeSecTo), Tools.DecString2Byte(caWave), Tools.DecString2Byte(cbWave),
    );
}

class EventLiveData : LiveData<Event>()