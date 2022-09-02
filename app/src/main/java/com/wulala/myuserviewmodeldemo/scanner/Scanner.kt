package com.wulala.myuserviewmodeldemo.scanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.myuserviewmodeldemo.logscreen.addToLog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Scanner(onScanClicked: () -> Unit, text: String, connState: String?) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Button(onClick = onScanClicked) {
            Text(text)
        }

        if (connState == null) {
            Text("", modifier = Modifier.padding(start = 16.dp))

        } else {
            Text(connState, modifier = Modifier.padding(start = 16.dp))
        }

    }
}

@Composable
fun ScanResultText(connState: String?) {
    if (connState == null) {
        Text("")
    } else {
        Text(connState)
    }
}


