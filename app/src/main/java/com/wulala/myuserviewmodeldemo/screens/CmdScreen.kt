package com.wulala.myuserviewmodeldemo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulala.blecom.entity.Event
import com.wulala.myuserviewmodeldemo.screens.EventSettingCard
import com.wulala.myuserviewmodeldemo.screens.SubDeviceSelectorScreen

@Composable
fun ButtonRow(onClick: () -> Unit, btnStr: String, labelText: String) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Button(
            onClick = onClick
        ) {
            Text(btnStr)
        }
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(labelText)
            //Text("dsfadf")
        }
    }
}


@Composable
fun CmdScreen(
    cmdBind: () -> Unit,
    cmdUnbind: () -> Unit,
    cmdGameStart: () -> Unit,
    cmdGameStop: () -> Unit,
    cmdTriggerSetting: () -> Unit,
    cmdRemoveEvent: () -> Unit,
    cmdEventSetting: (Event) -> Unit,
    cmdGameSetting: () -> Unit,
    cmdWaveDownload: () -> Unit,
    cmdLoadAllConfig: () -> Unit,
    onSubDeviceSelected: (String) -> Unit,
    cmdKeepSending: () -> Unit
) {

    Column {
        ButtonRow(cmdBind, "0x01", "绑定可用设备")

        Row {
            ButtonRow(cmdUnbind, "0x04", "解绑")
        }

        SubDeviceSelectorScreen(onSubDeviceSelected)

        ButtonRow(cmdGameStart, "0x11", "Game start")
        ButtonRow(cmdGameStop, "0x12", "Game stop")

        // EventSettingRow()
        EventSettingCard(cmdEventSetting)

        ButtonRow(cmdRemoveEvent, "0x21", "删除Event")

        ButtonRow(cmdTriggerSetting, "0x30", "配件触发设置")

        ButtonRow(cmdGameSetting, "0x40", "游戏配置")

        ButtonRow(cmdWaveDownload, "0xA0", "波形下载")

        ButtonRow(cmdLoadAllConfig, "0xFF", "查询所有配置")

        ButtonRow(cmdKeepSending, "测试用", "连续发送指令")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCmdScreen() {
    // CmdScreen()
}




