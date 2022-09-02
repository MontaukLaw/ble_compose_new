package com.wulala.myuserviewmodeldemo.fortest

import androidx.compose.runtime.*
import com.wulala.blecom.utils.Tools
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.blecom.viewmodels.ScannerViewModel

@Composable
fun BindTestCard(
    cmdSentMsgStrSet: (String) -> Unit
) {
    val cmdTextList = listOf(cmd1Text, cmd2Text)

    TestItemCardSimple(testName = "绑定测试") {
        CmdSendBtns(cmdList = listOf(cmd1, cmd2), cmdBtnText = cmdTextList) {
            val cmdSentMsgStr = Tools.bytesToHex(it)
            cmdSentMsgStrSet(cmdSentMsgStr)
            // println(cmdSentMsgStr)
        }
    }
}

@Composable
fun TestCard(
    cardName : String,
    cmdList: List<ByteArray>,
    cmdTextList: List<String>,
    viewModel: BLEViewModel,
    cmdSentMsgStrSet: (String) -> Unit,
) {
    TestItemCardV2(testName = cardName) {

        CmdSendBtns(cmdList = cmdList, cmdBtnText = cmdTextList) {
            val cmdSentMsgStr = Tools.bytesToHex(it)
            cmdSentMsgStrSet(cmdSentMsgStr)

            // 下发命令
            viewModel.writeCmd(it)
            // println(cmdSentMsgStr)
        }
    }
}

