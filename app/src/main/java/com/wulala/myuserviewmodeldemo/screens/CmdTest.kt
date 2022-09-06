package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonSpacer
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer


@Composable
fun CmdTestScreen(bleViewModel: SimpleBLEViewModel) {
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
                    Text("命令测试", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(
                                0x40.toByte(), 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
                                0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("2022072101") }

                        NiceVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(
                                0x40.toByte(), 0x00, 0x03,  // 0x40（1字节） +延时3秒启动（2字节）
                                0x03, 0x02,  // 基础强度设置（2字节）
                                0x09, 0x0a,  // AB通道背景波形（2字节）
                                0x00, 0x00, 0x00, 32.toByte(), 0x00, 200.toByte(),   // 游戏时长（6字节）
                                100, 120     // 安全设置（2字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("2022072102") }
                    }

                    NiceHorizonSpacer()

                    Row {
                        Button({
                            val cmd = byteArrayOf(
                                0x40.toByte(), 0x00, 0x03,  // 0x40（1字节） +延时3秒启动（2字节）
                                0x03, 0x02,  // 基础强度设置（2字节）
                                0x09, 0x0a,  // AB通道背景波形（2字节）
                                0x00, 0x01, 0x00, 0x00, 0x00, 200.toByte(),   // 游戏时长（6字节）
                                100, 120     // 安全设置（2字节）
                            )
                            bleViewModel.writeCmd(cmd)
                        }) { Text("2022072103") }

                        NiceVerticalSpacer()

                        Button({
                            val cmd = byteArrayOf(
                                0x40.toByte(), 0x00, 0x03,   // 0x40（1字节） +延时3秒启动（2字节）
                                0x03, 0x02,                  // 基础强度设置（2字节）
                                0x09, 0x0a,                  // AB通道背景波形（2字节）
                                0x00, 0x01, 0x00, 0x03, 0x00, 0x00,   // 游戏时长（6字节）
                                100, 120                     // 安全设置（2字节）
                            )
                            bleViewModel.writeCmd(cmd)
                        }) { Text("2022072104") }
                    }

                    NiceHorizonSpacer()

                    Row {
                        Button({
                            val cmd = byteArrayOf(
                                0x20, 0x02,   // 0x20（1字节）+触发事件ID（1字节）
                                0x03,  // 停止/启动游戏（半字节）+ 20220610增加：透传触发（半字节）
                                0x01, 0x0a,   // 输出指定波形（2字节）
                                0x02, 0x02, 0x05, 0x05, 0x02, 0x11,      // 强度临时改变（6字节）
                                5, 0x01, 0x03, 0x0a,                              //  强度永久改变（4字节）
                                // (-2).toByte(), 0x05, 0x03, 0x03,      //  强度永久改变（4字节）
                                // 0x00, 0x05, 0x00, 0x0a,               // 游戏时间改变（4字节）
                                10, 0x01, 0x10, 0x14,  // 相当于x: 10, y: 272, z: 20
                                5  // 触发生效时间（1字节）
                            )
                            bleViewModel.writeCmd(cmd)
                        }) { Text("2022072201") }
                    }

                    NiceHorizonSpacer()

                    // 0xB0（1字节指令类型）+序列号（1字节）+A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                    // +A通道强度变化量（2字节）+B通道强度变化量（2字节）+A通道强度软上限（1字节）+B通道强度软上限（1字节）
                    // +A通道波形X+Y+Z（4字节）+B通道波形X+Y+Z（4字节），共18字节
                    Row {
                        Button({
                            val cmd = byteArrayOf(
                                0xB0.toByte(), 0x02,             // 0xB0（1字节）+ 包ID（1字节）
                                0x0, 0x05,                       // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                                0x00, 0x01, 0x00, 0x05,          // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                200.toByte(), 210.toByte(),      // +A通道强度软上限（1字节）+B通道强度软上限（1字节）
                                0, 0, 0, 0,                      // +A通道波形X+Y+Z（4字节）+B通道波形X+Y+Z（4字节）
                                0, 0, 0, 0,
                            )
                            bleViewModel.writeCmd(cmd)
                        }) { Text("16字节波强混合") }

                        NiceVerticalSpacer()

                        // 第一条：序号为1，A通道强度强制设定为【无设置】，A通道强度变化量为【+10】，A通道强度软上限为【无设置】，A通道波形数据【1,9,10】
                        // 第二条：序号为0，A通道强度强制设定为【10】，A通道强度变化量为【-5】，A通道强度软上限为【无设置】，A通道波形数据【1,9,15】
                        // 第三条：序号为2，A通道强度强制设定为【无设置】，A通道强度变化量为【无设置】，A通道强度软上限为【无设置】，A通道波形数据【1,9,20】

                        Button({
                            val cmd1 = byteArrayOf(
                                0xB0.toByte(), 1,                // 第一条：序号为1
                                0xFF.toByte(), 0x0a,                       // A通道强度强制设定为【无设置】，
                                0, 0x0a, 0x00, 0x05,             // A通道强度变化量为【+10】
                                210.toByte(), 210.toByte(),                 // A通道强度软上限为【无设置】
                                1, 0, 9, 0x0a,                   // A通道波形数据【1,9,10】
                                5, 0, 0x03, 0x0a
                            )

                            val cmd2 = byteArrayOf(
                                0xB0.toByte(), 0,                // 第二条：序号为0
                                0x0a, 0x05,                      // A通道强度强制设定为【10】
                                0xFF.toByte(), 0xFB.toByte(), 0x00, 0x05, // A通道强度变化量为【-5】
                                254.toByte(), 210.toByte(),      // A通道强度软上限为【无设置】
                                1, 0, 9, 0x0f,                   // A通道波形数据【1,9,15】
                                5, 0, 0x03, 0x0a
                            )

                            val cmd3 = byteArrayOf(
                                0xB0.toByte(), 0x02,             // 第三条：序号为2
                                0xFF.toByte(), 0x0a,             // A通道强度强制设定为【无设置】
                                0x00, 0x01, 0x00, 0x05,          // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                210.toByte(), 210.toByte(),      // A通道强度软上限为【无设置】
                                1, 0, 9, 0x10,                   // A通道波形数据【1,9,20】
                                5, 0, 0x03, 0x0a
                            )

                            bleViewModel.writeCmd(cmd1)
                            bleViewModel.writeCmd(cmd2)
                            bleViewModel.writeCmd(cmd3)

                        }) { Text("连续发3个包") }
                    }

                    NiceHorizonSpacer()

                    // 0xB0（1字节指令类型）+序列号（1字节）+A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                    // +A通道强度变化量（2字节）+B通道强度变化量（2字节）+A通道强度软上限（1字节）+B通道强度软上限（1字节）
                    // +A通道波形X+Y+Z（4字节）+B通道波形X+Y+Z（4字节），共18字节
                    Row {
                        Button({
                            // B0 01 32 50 00 00 64 64 01 00 09 14 05 03 E3 0A
                            val cmd = byteArrayOf(
                                0xB0.toByte(), 1,                // 第一条：序号为1
                                0x32, 0x50,                      // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                                0, 0, 0, 0,                      // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                0x64, 0x64,                      // A通道强度软上限（1字节）+B通道强度软上限（1字节）
                                1, 0, 9, 0x0a,                   // A通道波形数据【1,9,10】
                                0x05, 0x03, 0xE3.toByte(), 0x0A
                            )

                            bleViewModel.writeCmd(cmd)

                        }) { Text("辛老师的测试") }


                        NiceVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(
                                0xFF.toByte()
                            )

                            bleViewModel.writeCmd(cmd)

                        }) { Text("0xFF") }
                    }
                    NiceHorizonSpacer()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("通道A XYZ:1, 9, 10, 强度2, 通道B:000")
                        NiceVerticalSpacer()
                        Button({
                            // B0 01 32 50 00 00 64 64 01 00 09 14 05 03 E3 0A
                            val cmd = byteArrayOf(
                                0xB0.toByte(), 1,                // 第一条：序号为1
                                0x2, 0x3,                      // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                                0, 0, 0, 0,                      // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                0x64, 0x64,                      // A通道强度软上限（1字节）+B通道强度软上限（1字节）
                                1, 0, 9, 0x0a,                   // A通道波形数据【1,9,10】
                                0, 0, 0, 0
                            )

                            bleViewModel.writeCmd(cmd)

                        }) { Text("GO") }
                    }
                    NiceHorizonSpacer()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("绝对强度255")
                        NiceVerticalSpacer()
                        Button({
                            // B0 01 32 50 00 00 64 64 01 00 09 14 05 03 E3 0A
                            val cmd = byteArrayOf(
                                0xB0.toByte(), 1,                // 第一条：序号为1
                                0xff.toByte(), 0xff.toByte(),    // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                                0, 0, 0, 0,                      // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                0x64, 0x64,                      // A通道强度软上限（1字节）+B通道强度软上限（1字节）
                                1, 0, 9, 0x0a,                   // A通道波形数据【1,9,10】
                                0, 0, 0, 0
                            )

                            bleViewModel.writeCmd(cmd)

                        }) { Text("GO") }

                        NiceVerticalSpacer()
                        Button({
                            // B0 01 32 50 00 00 64 64 01 00 09 14 05 03 E3 0A
                            val cmd = byteArrayOf(
                                0xB0.toByte(), 1,                // 第一条：序号为1
                                0xff.toByte(), 0xff.toByte(),    // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
                                0x80.toByte(), 0xff.toByte(), 1, 0xff.toByte(),                      // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
                                0x64, 0x64,                      // A通道强度软上限（1字节）+B通道强度软上限（1字节）
                                0xff.toByte(), 0, 0xff.toByte(), 0xff.toByte(),                   // A通道波形数据【1,9,10】
                                0xff.toByte(), 0, 0xff.toByte(), 0xff.toByte()
                            )

                            bleViewModel.writeCmd(cmd)

                        }) { Text("错误波形") }
                    }
                }
            }
        }
    }
}