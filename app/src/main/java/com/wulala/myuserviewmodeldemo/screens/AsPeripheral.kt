package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonSpacer
import com.wulala.myuserviewmodeldemo.ui.components.NiceSmallVerticalSpacer

@Composable
fun AsPeripheralCmdScreenSimple(bleViewModel: SimpleBLEViewModel) {
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
                    Text("SuM-LINK", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(0x01)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("搜索并连接子设备") }
                    }
                    NiceHorizonDivider()
                    Text("设置从电击器(别搞错)", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Text("设置游戏时间为200-100秒, 游戏结束后触发, 5秒后取消触发")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 一共15个字节
                            // 0x40（1字节） +延时3秒启动（2字节）+ 基础强度设置（2字节）+ AB通道背景波形（2字节）+ 游戏时长（6字节） + 安全设置（2字节）
                            val cmd = byteArrayOf(
                                0x40, 0x00, 0x03,  // 0x40（1字节） +延时3秒启动（2字节）
                                0x03, 0x02,  // 基础强度设置（2字节）
                                0x09, 0x0a,  // AB通道背景波形（2字节）
                                0x00, 100.toByte(), 0x00, 32.toByte(), 0x00, 200.toByte(),   // 游戏时长（6字节）
                                100, 120     // 安全设置（2字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("保存游戏设置") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x11)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("游戏开始") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x12)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("游戏停止") }
                    }

                    NiceHorizonDivider()
                    Text("设置游戏结束触发event ID 03, 10秒后取消触发, 透传id: 11, 12, 13, 14, 15, 16")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 共17字节, 0x50 +颜色（1字节） +触发配置（15字节，除了有效字节之外后面是占位）
                            // 配件玩法结束触发（2字节）透传触发（6字节）
                            val cmd = byteArrayOf(
                                0x50, 0x02,   //  0x50 +颜色（1字节）
                                // 配件玩法结束触发（2字节）
                                0x03, 0x0a,   // 游戏结束触发event ID 03, 10秒后取消触发
                                11, 12, 13, 14, 15, 16,
                                0, 0, 0, 0, 0, 0, 0
                            )
                            bleViewModel.writeCmd(cmd)
                        }) {
                            Text("把电击器当按钮设置触发")
                        }
                    }

                    NiceHorizonDivider()
                    Text("设置触发结果EventID 2, 透传IDX为3, 5秒后会自动取消触发, 只有多状态, 没有一次性结果")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 共20字节，0x20（1字节）+触发事件ID（1字节） + 停止/启动游戏（半字节）+ 20220610增加：透传触发（半字节）
                            // +输出指定波形（2字节）+ 强度临时改变（6字节）+强度永久改变（4字节）+ 游戏时间改变（4字节）+20220610增加：触发生效时间（1字节）
                            val cmd = byteArrayOf(
                                0x20, 0x02,   // 0x20（1字节）+触发事件ID（1字节）
                                0x03,  // 停止/启动游戏（半字节）+ 20220610增加：透传触发（半字节）
                                0x01, 0x0a,   // 输出指定波形（2字节）
                                0x02, 0x02, 0x05, 0x05, 0x02, 0x11,      // 强度临时改变（6字节）
                                0, 0, 0, 0,                              //  强度永久改变（4字节）
                                // (-2).toByte(), 0x05, 0x03, 0x03,      //  强度永久改变（4字节）
                                // 0x00, 0x05, 0x00, 0x0a,               // 游戏时间改变（4字节）
                                0, 0, 0, 0,
                                5  // 触发生效时间（1字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("保存Event2") }
                    }

                    NiceHorizonDivider()
                    Text("设置按钮按下后触发Event2")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x01,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("黄") }
                        NiceSmallVerticalSpacer()

                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x02,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("红") }
                        NiceSmallVerticalSpacer()

                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x03,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("紫") }
                    }
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x04,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("蓝") }
                        NiceSmallVerticalSpacer()

                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x05,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("青") }
                        NiceSmallVerticalSpacer()

                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x06,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("绿") }
                    }
                }
            }
        }
    }
}

@Composable
fun AsPeripheralCmdScreen(bleViewModel: BLEViewModel) {

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
                    Text("SuM-LINK", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Row {
                        Button({
                            val cmd = byteArrayOf(0x01)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("搜索并连接子设备") }
                    }
                    NiceHorizonDivider()
                    Text("设置从电击器(别搞错)", fontWeight = FontWeight.Bold)
                    NiceHorizonDivider()
                    Text("设置游戏时间为200-100秒, 游戏结束后触发, 5秒后取消触发")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 一共15个字节
                            // 0x40（1字节） +延时3秒启动（2字节）+ 基础强度设置（2字节）+ AB通道背景波形（2字节）+ 游戏时长（6字节） + 安全设置（2字节）
                            val cmd = byteArrayOf(
                                0x40, 0x00, 0x03,  // 0x40（1字节） +延时3秒启动（2字节）
                                0x03, 0x02,  // 基础强度设置（2字节）
                                0x09, 0x0a,  // AB通道背景波形（2字节）
                                0x00, 100.toByte(), 0x00, 32.toByte(), 0x00, 200.toByte(),   // 游戏时长（6字节）
                                100, 120     // 安全设置（2字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("保存游戏设置") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x11)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("游戏开始") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x12)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("游戏停止") }
                    }

                    NiceHorizonDivider()
                    Text("设置游戏结束触发event ID 03, 10秒后取消触发, 透传id: 11, 12, 13, 14, 15, 16")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 共17字节, 0x50 +颜色（1字节） +触发配置（15字节，除了有效字节之外后面是占位）
                            // 配件玩法结束触发（2字节）透传触发（6字节）
                            val cmd = byteArrayOf(
                                0x50, 0x02,   //  0x50 +颜色（1字节）
                                // 配件玩法结束触发（2字节）
                                0x03, 0x0a,   // 游戏结束触发event ID 03, 10秒后取消触发
                                11, 12, 13, 14, 15, 16,
                                0, 0, 0, 0, 0, 0, 0
                            )
                            bleViewModel.writeCmd(cmd)
                        }) {
                            Text("把电击器当按钮设置触发")
                        }
                    }

                    NiceHorizonDivider()
                    Text("设置触发结果EventID 2, 透传IDX为3, 5秒后会自动取消触发")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 共20字节，0x20（1字节）+触发事件ID（1字节） + 停止/启动游戏（半字节）+ 20220610增加：透传触发（半字节）
                            // +输出指定波形（2字节）+ 强度临时改变（6字节）+强度永久改变（4字节）+ 游戏时间改变（4字节）+20220610增加：触发生效时间（1字节）
                            val cmd = byteArrayOf(
                                0x20, 0x02,   // 0x20（1字节）+触发事件ID（1字节）
                                0x03,  // 停止/启动游戏（半字节）+ 20220610增加：透传触发（半字节）
                                0x01, 0x0a, 0x02, 0x02, 0x05, 0x05, 0x02, 0x11, (-2).toByte(), 0x05, 0x03, 0x03, // +输出指定波形（2字节）+ 强度临时改变（6字节）+ 强度永久改变（4字节）
                                0x00, 0x05, 0x00, 0x0a,  // 游戏时间改变（4字节）
                                5  // 触发生效时间（1字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("保存Event2") }
                    }

                    NiceHorizonDivider()
                    Text("设置黄色按钮按下后触发Event2")
                    NiceHorizonSpacer()
                    Row {
                        Button({
                            // 触发配置：(16字节)：随机反应模式触发（4字节）按钮触发（2字节）加速度触发（3字节）预留（6字节）
                            val cmd = byteArrayOf(
                                0x30, 0x01,  // 颜色
                                0x00, 0x02, 0x05, 0x0a, // 随机反应模式触发（4字节）
                                0x02, 0x00,  // 按下后触发event 02
                                0x00, 0x17, 0x14,   // 加速度触发（3字节）
                                0x00, 0x00, 0x00, 0x00, 0x00, 0x00  // 预留（6字节）
                            )
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("保存触发配置") }
                        NiceSmallVerticalSpacer()
                        Button({
                            val cmd = byteArrayOf(0x01)
                            // println(powerValue)
                            bleViewModel.writeCmd(cmd)
                        }) { Text("扫描按钮") }
                    }

                }
            }
        }
    }
}