package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.blecom.utils.CRC16.ccittFalse
import com.wulala.blecom.utils.CRC16.crcTest
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonSpacer
import com.wulala.myuserviewmodeldemo.ui.components.NiceSmallVerticalSpacer

@Composable
fun LockCmdScreen(bleViewModel: SimpleBLEViewModel) {

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }
    var crcResult by remember { mutableStateOf(" ") }

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
                    Text("测试命令", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Text(crcResult)
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(0x11)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("准备上锁") }
                        NiceSmallVerticalSpacer()

                        Button({
                            val cmd = byteArrayOf(0x20.toByte(), 0x01, 0x01, 0x0a, 0x00, 0x0a, 0x00, 0x20, 0, 0x01, 0x00, 0x02, 0x00, 10, 0x01)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("evt1") }
                        NiceSmallVerticalSpacer()

                        Button({
                            val cmd = byteArrayOf(0x20.toByte(), 0x02, 0x01, 0x0a, 0x00, 0x01, 0x00, 0x03, 0, 0x00, 0x2, 0x00, 0x08, 10, 0x01)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("evt2") }
                        NiceSmallVerticalSpacer()

                        Button({
                            val cmd = byteArrayOf(0xee.toByte())
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("test") }

                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            crcResult = ccittFalse(byteArrayOf(0xab.toByte(), 0xcd.toByte()))
                            // ccittFalse()
                        }) { Text("CRC测试") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x12)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("解锁") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x40, 0x00, 0xF0.toByte(), 0x00, 0xFF.toByte(), 0x01, 0x00, 0xFE.toByte())
                            bleViewModel.writeCmd(cmd)
                        }) { Text("游戏设置") }
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(0x20.toByte(), 24.toByte(), 1, 2, 0, 0, 0, 0, 30.toByte(), 10, 1)
                            bleViewModel.writeCmd(cmd)
                            // ccittFalse()
                        }) { Text("特殊event") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x30, 0x01, 0x00, 0x02, 0x05, 0x0a, 0x18, 0x00, 0x03, 0xAE.toByte(), 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
                            bleViewModel.writeCmd(cmd)

                            // ccittFalse()
                        }) { Text("给黄色按钮分配特殊event") }
                    }
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(0x30, 0x02, 0x00, 0x02, 0x05, 0x0a, 0x18, 0x00, 0x03, 0xAE.toByte(), 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
                            bleViewModel.writeCmd(cmd)

                            // ccittFalse()
                        }) { Text("给红色按钮分配特殊event") }
                    }
                }
            }
        }
    }
}