package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UploadScreen(uploadMsg: String) {
    Row(modifier = Modifier.padding(top = 25.dp)) {
        Text(uploadMsg);
    }
}