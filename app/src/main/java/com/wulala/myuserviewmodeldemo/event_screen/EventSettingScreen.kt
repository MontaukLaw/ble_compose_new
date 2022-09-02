package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulala.blecom.entity.Event
import com.wulala.blecom.utils.EventCmdBuilder
import com.wulala.blecom.utils.Tools
import com.wulala.myuserviewmodeldemo.model.EventBuilder
import com.wulala.myuserviewmodeldemo.ui.components.*


//Byte eventId, Byte ifMultiState,
//Byte caType, Byte caFrom, Byte caTo,
//Byte cbType, Byte cbFrom, Byte cbTo,
//Byte baseAFrom, Byte baseAto, Byte baseBFrom, Byte baseBTo,
//int timeChangeSecFrom, int timeChangeSecTo,
//Byte caWave, Byte cbWave

@Composable
fun CmdDetailRows(event: Event) {
    var ifShowDetailCmd = remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text("Event设置")
        Button(onClick = { ifShowDetailCmd.value = true }) {
            Text("发送")
        }
        Row {
            Text("Detail")
            NiceVerticalSpacer()
            IconButton(
                onClick = { ifShowDetailCmd.value = !ifShowDetailCmd.value },
                modifier = Modifier.size(ButtonDefaults.IconSize)
            ) {
                if (ifShowDetailCmd.value) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
            }
        }
    }
    NiceHorizonSpacer()
    if (ifShowDetailCmd.value) {
        Row {
            Text(
                Tools.bytes2HexString(
                    EventCmdBuilder.build(
                        event.id, event.ifMultiState, event.caType, event.caFrom, event.caTo,
                        event.cbType, event.cbFrom, event.cbTo, event.baseAFrom, event.baseATo,
                        event.baseBFrom, event.baseBTo, event.timeChangeSecFrom, event.timeChangeSecTo,
                        event.caWave, event.cbWave
                    )
                )
            )
        }
    }
}

@Composable
fun HexTextRow(content: List<String>) {
    Row {
        content.forEach {
            Text(Tools.StringNumber2HexString(it))
            NiceSmallVerticalSpacer()
        }
    }
}

/**
 * stateful
 */
@Composable
fun EventSettingCard(sendCmd: (Event) -> Unit) {
    val (eventId, onEventIdChange) = remember { mutableStateOf("") }
    val (eventType, onEventTypeChange) = rememberSaveable { mutableStateOf("") }

    val (caType, onCaTypeChange) = rememberSaveable { mutableStateOf("") }
    val (caFrom, onCaFromChange) = rememberSaveable { mutableStateOf("") }
    val (caTo, onCaToChange) = rememberSaveable { mutableStateOf("") }

    val (cbType, onCbTypeChange) = rememberSaveable { mutableStateOf("") }
    val (cbFrom, onCbFromChange) = rememberSaveable { mutableStateOf("") }
    val (cbTo, onCbToChange) = rememberSaveable { mutableStateOf("") }

    val (caWave, onCaWaveChange) = rememberSaveable { mutableStateOf("") }
    val (cbWave, onCbWaveChange) = rememberSaveable { mutableStateOf("") }

    val (timeChangeSecFrom, onTimeFromChange) = rememberSaveable { mutableStateOf("") }
    val (timeChangeSecTo, onTimeToChange) = rememberSaveable { mutableStateOf("") }

    val (baseAFrom, onBaseAFromChange) = rememberSaveable { mutableStateOf("") }
    val (baseATo, onBaseAToChange) = rememberSaveable { mutableStateOf("") }

    val (baseBFrom, onBaseBFromChange) = rememberSaveable { mutableStateOf("") }
    val (baseBTo, onBaseBToChange) = rememberSaveable { mutableStateOf("") }

    val onSend = {
        sendCmd(
            EventBuilder(
                id = eventId, ifMultiState = eventType,
                caType = caType, caFrom = caFrom, caTo = caTo,
                cbType = cbType, cbFrom = cbFrom, cbTo = cbTo,
                caWave = caWave, cbWave = cbWave, timeChangeSecFrom = timeChangeSecFrom, timeChangeSecTo = timeChangeSecTo,
                baseAFrom = baseAFrom, baseBFrom = baseBFrom, baseATo = baseATo, baseBTo = baseBTo
            )
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                // hex format cmd detail
                // CmdDetailRows(event)
                HexTextRow(listOf("32", eventId, eventType, "00", caWave, cbWave))
                HexTextRow(listOf(caType, caFrom, caTo, cbType, cbFrom, cbTo))
                HexTextRow(listOf(baseAFrom, baseATo, baseBFrom, baseBTo))
                HexTextRow(listOf(timeChangeSecFrom, timeChangeSecFrom, timeChangeSecTo, timeChangeSecTo))

                NiceHorizonDivider()
                // ca,cb Power change setting
                // PowerChangeEventSetting("通道A", caType, onCaTypeChange, caFrom, onCaFromChange, caTo, onCaToChange)
                SettingRow(
                    eventId, onEventIdChange, "  ID",
                    eventType, onEventTypeChange, "Type", onSend
                )
                SettingRow(
                    "通道A", caType, onCaTypeChange, "Type",
                    caFrom, onCaFromChange, "From", caTo, onCaToChange, "  To"
                )
                SettingRow(
                    "通道B", cbType, onCbTypeChange, "Type",
                    cbFrom, onCbFromChange, "From", cbTo, onCbToChange, "  To"
                )
                SettingRow(
                    "惩罚波形", caWave, onCaWaveChange, "  A通道",
                    cbWave, onCbWaveChange, "  B通道", null, {}, null
                )
                SettingRow(
                    "游戏时间变化", timeChangeSecFrom, onTimeFromChange, "From",
                    timeChangeSecTo, onTimeToChange, "  To", null, {}, null
                )
                SettingRow(
                    "基础强度变化通道A", baseAFrom, onBaseAFromChange, "From",
                    baseATo, onBaseAToChange, "  To", null, {}, null
                )
                SettingRow(
                    "基础强度变化通道B", baseBFrom, onBaseBFromChange, "From",
                    baseBTo, onBaseBToChange, "  To", null, {}, null
                )
            }
        }
    }
}


@Composable
fun EventSettingCardOld(event: Event) {

    var eventId = remember { mutableStateOf("" + event.id) }
    val eventType = remember { mutableStateOf("" + event.ifMultiState) }

    val caType = remember { mutableStateOf("" + event.caType) }
    val caFrom = remember { mutableStateOf("" + event.caFrom) }
    val caTo = remember { mutableStateOf("" + event.caTo) }

    val cbType = remember { mutableStateOf("" + event.cbType) }
    val cbFrom = remember { mutableStateOf("" + event.cbFrom) }
    val cbTo = remember { mutableStateOf("" + event.cbTo) }

    val caWave = remember { mutableStateOf("" + event.caWave) }
    val cbWave = remember { mutableStateOf("" + event.cbWave) }

    val timeChangeSecFrom = remember { mutableStateOf("" + event.timeChangeSecFrom) }
    val timeChangeSecTo = remember { mutableStateOf("" + event.timeChangeSecTo) }

    val baseAFrom = remember { mutableStateOf("" + event.baseAFrom) }
    val baseATo = remember { mutableStateOf("" + event.baseATo) }

    val baseBFrom = remember { mutableStateOf("" + event.baseBFrom) }
    val baseBTo = remember { mutableStateOf("" + event.baseBTo) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                CmdDetailRows(event)
                NiceHorizonSpacer()
                Row(verticalAlignment = Alignment.Bottom) {
                    TextFieldInput(eventId.value, {
                        eventId.value = it
                        event.id = Tools.DecString2Byte(it)
                    }, "Id", 50.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(eventType.value, {
                        eventType.value = it
                        event.ifMultiState = Tools.DecString2Byte(it)
                    }, "类型", 50.dp)
                    // DownDropMenu( { select(caType.value) })
                }
                NiceHorizonSpacer()
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("通道A")
                    NiceVerticalSpacer()
                    TextFieldInput(caType.value, {
                        caType.value = it
                        event.caType = Tools.DecString2Byte(it)
                    }, "类型", 50.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(caFrom.value, {
                        caFrom.value = it
                        event.caFrom = Tools.DecString2Byte(it)
                    }, "From", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(caTo.value, {
                        caTo.value = it
                        event.caTo = Tools.DecString2Byte(it)
                    }, "To", 50.dp)
                }
                NiceHorizonSpacer()
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("通道B")
                    NiceVerticalSpacer()
                    TextFieldInput(cbType.value, {
                        cbType.value = it
                        event.cbType = Tools.DecString2Byte(it)
                    }, "类型", 50.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(cbFrom.value, {
                        cbFrom.value = it
                        event.cbFrom = Tools.DecString2Byte(it)
                    }, "From", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(cbTo.value, {
                        cbTo.value = it
                        event.cbTo = Tools.DecString2Byte(it)
                    }, "To", 50.dp)
                }
                NiceHorizonSpacer()

                Row(verticalAlignment = Alignment.Bottom) {
                    Text("波形变化")
                    NiceVerticalSpacer()
                    TextFieldInput(caWave.value, {
                        caWave.value = it
                        event.caWave = Tools.DecString2Byte(it)
                    }, "A通道", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(cbWave.value, {
                        cbWave.value = it
                        event.cbWave = Tools.DecString2Byte(it)
                    }, "B通道", 60.dp)
                }

                NiceHorizonDivider()

                Row(verticalAlignment = Alignment.Bottom) {
                    Text("游戏时间变化")
                    NiceVerticalSpacer()
                    TextFieldInput(timeChangeSecFrom.value, {
                        timeChangeSecFrom.value = it
                        event.timeChangeSecFrom = Tools.String2Int(it)
                    }, "From", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(timeChangeSecTo.value, {
                        timeChangeSecTo.value = it
                        event.timeChangeSecTo = Tools.String2Int(it)
                    }, "To", 60.dp)
                }
                NiceHorizonSpacer()

                Row(verticalAlignment = Alignment.Bottom) {
                    Text("通道A基础强度变化")
                    NiceVerticalSpacer()
                    TextFieldInput(baseAFrom.value, {
                        baseAFrom.value = it
                        event.baseAFrom = Tools.DecString2Byte(it)
                    }, "From", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(baseATo.value, {
                        baseATo.value = it
                        event.baseATo = Tools.DecString2Byte(it)
                    }, "To", 60.dp)
                }

                NiceHorizonSpacer()

                Row(verticalAlignment = Alignment.Bottom) {
                    Text("通道B基础强度变化")
                    NiceVerticalSpacer()
                    TextFieldInput(baseBFrom.value, {
                        baseBFrom.value = it
                        event.baseBFrom = Tools.DecString2Byte(it)
                    }, "From", 60.dp)
                    NiceVerticalSpacer()
                    TextFieldInput(baseBTo.value, {
                        baseBTo.value = it
                        event.baseBTo = Tools.DecString2Byte(it)
                    }, "To", 60.dp)
                }

                NiceHorizonSpacer()

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { }) {
                        Text("发送")
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun EventSettingRowPreview() {
    //  EventSettingRow()
}
