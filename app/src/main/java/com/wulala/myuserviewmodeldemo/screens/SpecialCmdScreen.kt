package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.fortest.*
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonSpacer
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer

@Composable
fun SpecialCmdScreen(bleViewModel: SimpleBLEViewModel) {

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("强度(绝对值)直接设置", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    absPowerSetRow("通道A", bleViewModel, 0xE3.toByte())
                    NiceHorizonSpacer()
                    absPowerSetRow("通道B", bleViewModel, 0xE4.toByte())
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("连续发送3个命令", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Text(cmd27Text)
                        NiceVerticalSpacer()
                        Text(cmd39Text)
                        NiceVerticalSpacer()
                        Text(cmd41Text)
                    }
                    NiceHorizonDivider()
                    Button({
                        bleViewModel.writeCmd(cmd27)
                        bleViewModel.writeCmd(cmd39)
                        bleViewModel.writeCmd(cmd41)
                    }) {
                        Text("发送")
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("按钮触发的切换", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(cmd22Text)
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(cmd22)
                        }) { Text("发送") }
                    }
                    NiceHorizonDivider()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(triggerCmdForColor1Text)
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(triggerCmdForColor1)
                        }) { Text("发送") }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("清除所有的event/trigger", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Button({
                        bleViewModel.writeCmd(byteArrayOf(0xFE.toByte()))
                    }) {
                        Text("清除")
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("测试命令", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xB0.toByte(), 0x01, 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(),
                                    0x00, 0x07, 0x00, 0x07, 0, 0, 0, 0, 0, 0, 0, 0
                                )
                            )
                        }) {
                            Text("各自+7")
                        }
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xB0.toByte(), 0x02, 0, 0x55.toByte(), 0, 0x55.toByte(),
                                    0x00, 0x07, 0x00, 0x07, 0x01, 0x14, 0x00, 0x09, 0x05, 0x0A.toByte(), 0x03, 0xE3.toByte()
                                )
                            )
                        }) {
                            Text("波形")
                        }
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xB0.toByte(), 0x01, 0, 14.toByte(), 0, 140.toByte(),
                                    0x00, 0x07, 0x00, 0x07, 0, 0, 0, 0, 0, 0, 0, 0
                                )
                            )
                        }) {
                            Text("设置绝对强度A:14/B:140")
                        }
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xB0.toByte(), 0x01, 0, 28.toByte(), 0, 49.toByte(),
                                    0x00, 0x07, 0x00, 0x07, 0, 0, 0, 0, 0, 0, 0, 0
                                )
                            )
                        }) {
                            Text("设置绝对强度A:28/B:49")
                        }
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xC0.toByte()
                                )
                            )
                        }) {
                            Text("开/关锁")
                        }
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xC4.toByte()
                                )
                            )
                        }) {
                            Text("70ms间隔")
                        }
                    }

                    NiceHorizonDivider()
                    Row {
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xC1.toByte()
                                )
                            )
                        }) {
                            Text("响3声")
                        }
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xC2.toByte()
                                )
                            )
                        }) {
                            Text("降Key")
                        }
                        NiceVerticalSpacer()
                        Button({
                            bleViewModel.writeCmd(
                                byteArrayOf(
                                    0xC3.toByte()
                                )
                            )
                        }) {
                            Text("响4声")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun absPowerSetRow(text: String, bleViewModel: SimpleBLEViewModel, channel: Byte) {
    var powerValue by remember { mutableStateOf("0") }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text)
        OutlinedTextField(value = powerValue, onValueChange = { powerValue = it }, modifier = Modifier.width(50.dp))
        Button({
            val cmd = byteArrayOf(channel, powerValue.toByte())
            // println(powerValue)
            bleViewModel.writeCmd(cmd)
        }) { Text("发送") }
    }
}