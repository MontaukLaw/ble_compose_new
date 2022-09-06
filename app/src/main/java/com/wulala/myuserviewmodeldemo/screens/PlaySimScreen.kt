package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer

var powerChangeCmd = byteArrayOf(
    0xB0.toByte(), 0x01,
    0xff.toByte(), 0xff.toByte(),                                  // 不涉及强度绝对变化
    0, 0, 0, 0,                                                       // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
    0x64, 0x64,                                                    // A通道强度软上限（1字节）+B通道强度软上限（1字节）
    0xff.toByte(), 4, 0xff.toByte(), 0xff.toByte(),                // 不涉及波形
    0xff.toByte(), 4, 0xff.toByte(), 0xff.toByte()
)

fun init_power_change_cmd(){

     powerChangeCmd = byteArrayOf(
        0xB0.toByte(), 0x01,
        0xff.toByte(), 0xff.toByte(),                                  // 不涉及强度绝对变化
        0, 0, 0, 0,                                                       // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
        0x64, 0x64,                                                    // A通道强度软上限（1字节）+B通道强度软上限（1字节）
        0xff.toByte(), 4, 0xff.toByte(), 0xff.toByte(),                // 不涉及波形
        0xff.toByte(), 4, 0xff.toByte(), 0xff.toByte()
    )

}

@Composable
fun PlaySimScreen(simpleBLEViewModel: SimpleBLEViewModel, powerCa: Int, powerCb: Int) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }
    var caPlayState by remember { mutableStateOf(false) }
    var cbPlayState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("A通道")
                        NiceVerticalSpacer()
                        Button(onClick = {
                            caPlayState = !caPlayState
                            simpleBLEViewModel.keepPlayWaveCA(powerCa)
                            // simpleBLEViewModel.keepSendingCmd(cmd001, 1000)

                            if(!caPlayState){
                                // 先初始化一下
                                init_power_change_cmd()
                                powerChangeCmd[2] = 0
                                simpleBLEViewModel.writeCmd(powerChangeCmd)
                            }
                        }) {
                            if (!caPlayState) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = "play",
                                )
                            } else {
                                Icon(
                                    Icons.Filled.Pause,
                                    contentDescription = "Stop",
                                )
                            }
                        }
                        NiceVerticalSpacer()

                        Button(onClick = {
                            init_power_change_cmd()
                            // +1
                            powerChangeCmd[4] = 0;
                            powerChangeCmd[5] = 1;
                            simpleBLEViewModel.writeCmd(powerChangeCmd)

                        }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Increase",
                            )
                        }
                        NiceVerticalSpacer()

                        Button(onClick = {
                            init_power_change_cmd()
                            // -1
                            powerChangeCmd[4] = 0xff.toByte();
                            powerChangeCmd[5] = 0xff.toByte();
                            simpleBLEViewModel.writeCmd(powerChangeCmd)

                        }) {
                            Icon(
                                Icons.Filled.Remove,
                                contentDescription = "Diminish",
                            )
                        }
                    }
                    NiceHorizonDivider()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("B通道")
                        NiceVerticalSpacer()
                        Button(onClick = {
                            cbPlayState = !cbPlayState
                            simpleBLEViewModel.keepPlayWaveCB(powerCb)

                            if(!cbPlayState){
                                // 先初始化一下
                                init_power_change_cmd()
                                powerChangeCmd[3] = 0
                                simpleBLEViewModel.writeCmd(powerChangeCmd)
                            }
                            // simpleBLEViewModel.keepSendingCmd(cmd001, 1000)
                        }) {
                            if (!cbPlayState) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = "play",
                                )
                            } else {
                                Icon(
                                    Icons.Filled.Pause,
                                    contentDescription = "Stop",
                                )
                            }
                        }
                        NiceVerticalSpacer()

                        Button(onClick = {
                            init_power_change_cmd()
                            // +1
                            powerChangeCmd[6] = 0;
                            powerChangeCmd[7] = 1;
                            simpleBLEViewModel.writeCmd(powerChangeCmd)

                        }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "play",
                            )
                        }
                        NiceVerticalSpacer()

                        Button(onClick = {
                            init_power_change_cmd()
                            // -1
                            powerChangeCmd[6] = 0xff.toByte();
                            powerChangeCmd[7] = 0xff.toByte();
                            simpleBLEViewModel.writeCmd(powerChangeCmd)
                        }) {
                            Icon(
                                Icons.Filled.Remove,
                                contentDescription = "play",
                            )
                        }
                    }
                }
            }
        }
    }
}