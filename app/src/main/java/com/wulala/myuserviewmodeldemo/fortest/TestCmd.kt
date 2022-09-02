package com.wulala.myuserviewmodeldemo.fortest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonSpacer
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer

const val BTN_PER_ROW = 2

// 将按钮用list方式表现出来, 且将命令统一传输到ViewModel的逻辑fun中
@Composable
fun CmdSendBtns(
    cmdList: List<ByteArray>,
    cmdBtnText: List<String>,
    cmdHandler: (ByteArray) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var rowNumber = 1
        println("cmdList.size: ${cmdList.size}")

        if (cmdList.size > BTN_PER_ROW) {
            rowNumber = cmdList.size / BTN_PER_ROW;
            if ((cmdList.size % BTN_PER_ROW) != 0) {
                rowNumber++;
            }
        }

        // 限制一下每行4个item

        println("rowNumber: $rowNumber")
        for (rowIndex in 0 until rowNumber) {
            Row {
                // 最后一行
                if (rowIndex * BTN_PER_ROW + BTN_PER_ROW > cmdList.size) {
                    for (idx: Int in rowIndex * BTN_PER_ROW until cmdList.size) {
                        // println("line $rowIndex: $idx")
                        Button(onClick = { cmdHandler(cmdList[idx]) }) {
                            Text(cmdBtnText[idx])
                        }
                        NiceVerticalSpacer()
                    }
                } else {
                    for (idx: Int in rowIndex * BTN_PER_ROW until rowIndex * BTN_PER_ROW + BTN_PER_ROW) {
                        // println("line $rowIndex: $idx")
                        Button(onClick = { cmdHandler(cmdList[idx]) }) {
                            Text(cmdBtnText[idx])
                        }
                        NiceVerticalSpacer()
                    }
                }
            }
            NiceHorizonSpacer()
        }
    }
}

//for ((index, value) in cmdList.withIndex()) {
//    Button(onClick = { cmdHandler(value) }) {
//        Text(cmdBtnText[index])
//    }
//}

