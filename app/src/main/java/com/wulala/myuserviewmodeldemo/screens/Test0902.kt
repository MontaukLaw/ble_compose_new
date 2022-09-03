package com.wulala.myuserviewmodeldemo.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myuserviewmodeldemo.ui.components.*
import kotlin.experimental.and
import kotlin.math.log

val cmd001 = byteArrayOf(
    0xB0.toByte(), 1,                                               // 第一条：序号为1
    0xff.toByte(), 0xff.toByte(),                                   // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）
    0x80.toByte(), 0xff.toByte(), 1, 0xff.toByte(),                 // +A通道强度变化量（2字节）+B通道强度变化量（2字节）
    0x64, 0x64,                                                     // A通道强度软上限（1字节）+B通道强度软上限（1字节）
    0xff.toByte(), 0, 0xff.toByte(), 0xff.toByte(),                 // A通道波形数据【1,9,10】
    0xff.toByte(), 0, 0xff.toByte(), 0xff.toByte()
)

// 1、在现版本安卓APP的基础上，搜索的设备名称改成DG-LAB_1000
// Done

// 2. 原本A通道波形数据，B通道波形数据以及A,B通道强度数据用的是三个分开的特性，现在换成了150A特性专门用来向设备写入，设备从150B特性通知回来
// Done

// 3. 在之前的版本里，对强度的处理已经进行了确认收到上一条强度命令的回复之后才发下一条的设计。
// 当时的判定方式是，收到的强度数值如果等于发出的强度数值那么认为已回复，举例发出了A,B通道强度为10,1。那么如果收到了10,1等于这一条已经收到。而新版本使用的是收到了和发出指令序列号一样的强度回调来确认已收到回复。
// 命令发送按钮后面有log显示

@Composable
fun Test0902Screen(bleViewModel: SimpleBLEViewModel) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    val cmdLog001 = remember { mutableStateOf("发送命令") }
    val cmdId = remember { mutableStateOf(1) }
    val channelAAbsProgress = remember { mutableStateOf(0) }
    val channelBAbsProgress = remember { mutableStateOf(0) }

    val channelARelProgress = remember { mutableStateOf(0) }
    val channelBRelProgress = remember { mutableStateOf(0) }

    val channelASoftLimit = remember { mutableStateOf("100") }
    val channelBSoftLimit = remember { mutableStateOf("100") }

    val channelAWaveX = remember { mutableStateOf("1") }
    val channelAWaveY = remember { mutableStateOf("100") }
    val channelAWaveZ = remember { mutableStateOf("10") }

    val channelBWaveX = remember { mutableStateOf("1") }
    val channelBWaveY = remember { mutableStateOf("100") }
    val channelBWaveZ = remember { mutableStateOf("10") }

    val focusRequester = remember { FocusRequester() }

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
                    Text("A通道绝对强度:${channelAAbsProgress.value} B通道绝对强度:${channelBAbsProgress.value}")

                    AbsPowerSlider(channelAAbsProgress)
                    AbsPowerSlider(channelBAbsProgress)

                    Text("A通道相对强度:${channelARelProgress.value} B通道相对强度:${channelBRelProgress.value}")

                    RelPowerSlide(channelARelProgress)
                    RelPowerSlide(channelBRelProgress)

                    NiceHorizonSpacer()

                    WaveXYZInputRow("CA", channelAWaveX, channelAWaveY, channelAWaveZ, focusRequester)
                    NiceHorizonSpacer()

                    WaveXYZInputRow("CB", channelBWaveX, channelBWaveY, channelBWaveZ, focusRequester)
                    NiceHorizonSpacer()

                    SoftLimitInputRow(channelASoftLimit, channelBSoftLimit, focusRequester)
                    NiceHorizonSpacer()

                    Button(onClick = {
                        val cmd = byteArrayOf(
                            0xB0.toByte(), cmdId.value.toByte(),                                                    // 第一条：序号为1
                            channelAAbsProgress.value.toByte(), channelBAbsProgress.value.toByte(),                 // A通道强度强制设定值（1字节）+B通道强度强制设定值（1字节）

                            // A通道强度相对设定值（2字节）
                            channelARelProgress.value.shr(8).toByte(), channelARelProgress.value.toByte(),

                            channelBRelProgress.value.shr(8).toByte(), channelBRelProgress.value.toByte(),  // +A通道强度变化量（2字节）+B通道强度变化量（2字节）

                            // A通道强度软上限（1字节）+B通道强度软上限（1字节）
                            channelASoftLimit.value.toByte(), channelBSoftLimit.value.toByte(),

                            // A通道波形数据
                            (channelAWaveX.value.toInt() and 0xFF).toByte(),
                            (channelAWaveY.value.toInt().shr(8).toByte() and 0xFF.toByte()),
                            (channelAWaveY.value.toInt() and 0xFF).toByte(),
                            (channelAWaveZ.value.toInt() and 0xFF).toByte(),

                            // B通道波形数据
                            (channelBWaveX.value.toInt() and 0xFF).toByte(),
                            (channelBWaveY.value.toInt().shr(8).toByte() and 0xFF.toByte()),
                            (channelBWaveY.value.toInt() and 0xFF).toByte(),
                            (channelBWaveZ.value.toInt() and 0xFF).toByte()
                        )
                        Log.d(TAG, "cmd: $cmd")

                        bleViewModel.writeCmd(cmd)
                        cmdLog001.value = "发送命令${cmdId.value}"
                        cmdId.value++
                    }) {
                        Text("发送命令")
                    }

                    NiceHorizonSpacer()

                    Text("发送0xB0: ${cmdId.value}")
                    // :[0]:0xb0:[1]:0x01:[2]:0x00:[3]:0x00:[4]:0x00:[5]:0x00:[6]:0x00:[7]:0x00:[8]:0x64:[9]:0x64:
                    // [10]:0x01:[11]:0x64:[12]:0x0a:[13]:0xff:[14]:0x01:[15]:0x64:[16]:0x0a:[17]:0xff:
                }
            }
        }
    }
}

@Composable
fun RelPowerSlide(channelRelProgress: MutableState<Int>) {

    Slider(
        value = (channelRelProgress.value).toFloat() / 400f + 0.5f,
        onValueChange = {
            channelRelProgress.value = ((it - 0.5f) * 400f).toInt()
        },
    )
}

@Composable
fun AbsPowerSlider(channelAbsProgress: MutableState<Int>) {
    Slider(
        value = channelAbsProgress.value / 200.toFloat(),
        onValueChange = {
            channelAbsProgress.value = (it * 200).toInt()
        },
    )
}

@Composable
fun WaveXYZInputRow(inputTitle: String, waveX: MutableState<String>, waveY: MutableState<String>, waveZ: MutableState<String>, focusRequester: FocusRequester) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(inputTitle)
        NiceVerticalSpacer()
        TextFieldInputWithFocus(input = waveX.value, inputChanged = {
            waveX.value = it
        }, labelText = "X", width = 50.dp, focusRequester)
        NiceVerticalSpacer()
        TextFieldInputWithFocus(input = waveY.value, inputChanged = {
            waveY.value = it
        }, labelText = "Y", width = 80.dp, focusRequester)
        NiceVerticalSpacer()
        TextFieldInputWithFocus(input = waveZ.value, inputChanged = {
            waveZ.value = it
        }, labelText = "Z", width = 50.dp, focusRequester)
    }
}

@Composable
fun SoftLimitInputRow(softLimitA: MutableState<String>, softLimitB: MutableState<String>, focusRequester: FocusRequester) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("通道AB软上限: ")
        NiceVerticalSpacer()
        TextFieldInputWithFocus(input = softLimitA.value, inputChanged = {
            softLimitA.value = it
        }, labelText = "A", width = 80.dp, focusRequester)
        NiceVerticalSpacer()
        TextFieldInputWithFocus(input = softLimitB.value, inputChanged = {
            softLimitB.value = it
        }, labelText = "B", width = 80.dp, focusRequester)
    }
}