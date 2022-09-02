package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.myuserviewmodeldemo.fortest.*


@Composable
fun MainScreen(bleViewModel: BLEViewModel, updateSendMsg: (String) -> Unit) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    // CmdDisplay(relayUploadMsgStr = relayUploadMsgStr, cmdSentMsgStr = cmdSentMsgStr, humanLog)
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {

        TestCard(
            "绑定", listOf(cmd1, cmd2),
            listOf(cmd1Text, cmd2Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "解绑", listOf(cmd3, cmd4, cmd5, cmd6),
            listOf(cmd3Text, cmd4Text, cmd5Text, cmd6Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "启动游戏", listOf(cmd7, cmd8),
            listOf(cmd7Text, cmd8Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "停止游戏", listOf(cmd9, cmd10),
            listOf(cmd9Text, cmd10Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "保存Event", listOf(cmd11, cmd14, cmd15, cmd46, cmd47, cmd48, cmd16, cmd49),
            listOf(cmd11Text, cmd14Text, cmd15Text, cmd46Text, cmd47Text, cmd48Text, cmd16Text, cmd49Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "删除Event", listOf(cmd17, cmd18, cmd19, cmd20, cmd21),
            listOf(cmd17Text, cmd18Text, cmd19Text, cmd20Text, cmd21Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "设置触发", listOf(cmd22, cmd23, cmd24, cmd50),
            listOf(cmd22Text, cmd23Text, cmd24Text, cmd50Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "游戏设置", listOf(cmd25, cmd26, cmd52, cmd53, cmd54, cmd55),
            listOf(cmd25Text, cmd26Text, cmd52Text, cmd53Text, cmd54Text, cmd55Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "查询所有配置", listOf(cmd27, cmd28),
            listOf(cmd27Text, cmd28Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "查询游戏状态", listOf(cmd29, cmd30),
            listOf(cmd29Text, cmd30Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "查询游戏设置", listOf(cmd39, cmd40),
            listOf(cmd39Text, cmd40Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "查询所有触发", listOf(cmd41, cmd42),
            listOf(cmd41Text, cmd42Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "查询所有Event", listOf(cmd43, cmd44),
            listOf(cmd43Text, cmd44Text), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "挨个发触发配置", listOf(
                triggerCmdForColor2, triggerCmdForColor3, triggerCmdForColor1,
                triggerCmdForColor4, triggerCmdForColor5, triggerCmdForColor6,
                triggerCmdForColor2_1
            ),
            listOf(
                triggerCmdForColor2Text, triggerCmdForColor3Text, triggerCmdForColor1Text,
                triggerCmdForColor4Text, triggerCmdForColor5Text, triggerCmdForColor6Text,
                triggerCmdForTazerText
            ), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "挨个移除", listOf(
                unbindCmdForColor1, unbindCmdForColor2, unbindCmdForColor3,
                unbindCmdForColor4, unbindCmdForColor5, unbindCmdForColor6
            ),
            listOf(
                unbindCmdForColor1Text, unbindCmdForColor2Text, unbindCmdForColor3Text,
                unbindCmdForColor4Text, unbindCmdForColor5Text, unbindCmdForColor6Text,
            ), bleViewModel
        ) { updateSendMsg(it) }

        TestCard(
            "DEBUG", listOf(
                printOnlineBtnsCmd, bindEvent2Cmd, bindEvent3Cmd, bindEvent4Cmd, bindEvent24Cmd, getPowerLiveCmd, getPrintBindCmd
            ),
            listOf(
                printOnlineBtnsCmdText, bindEvent2CmdText, bindEvent3CmdText,
                bindEvent4CmdText, bindEvent24CmdText, getPowerLiveCmdText, getPrintBindCmdText
            ), bleViewModel
        ) { updateSendMsg(it) }

        PowerCurveScreen(bleViewModel)
    }

}