package com.wulala.myuserviewmodeldemo.fortest

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer

@Composable
fun CmdDisplay(
    relayUploadMsgStr: String,
    cmdSentMsgStr: String,
    rightFB:String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {

                DisplayRow(cmdSentMsgStr, Icons.Filled.FileDownload)
                NiceHorizonDivider()
                DisplayRow(relayUploadMsgStr, Icons.Filled.FileUpload)
                NiceHorizonDivider()
                DisplayRow(rightFB, Icons.Filled.Transform)
            }
        }
    }
}

@Composable
fun SimpleCmdDisplay(
    humanLog:String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                DisplayRow(humanLog, Icons.Filled.Transform)
            }
        }
    }
}

@Composable
fun DisplayRow(text: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = "")
        NiceVerticalSpacer()
        Text(text)
    }
}