package com.wulala.myuserviewmodeldemo.trigger_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulala.myuserviewmodeldemo.screens.SettingRow

@Composable
fun TriggerSettingCard() {
    val (triggerId, onEventIdChange) = remember { mutableStateOf("") }
    val (eventType, onEventTypeChange) = rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                //SettingRow(
                    //"通道A", caType, onCaTypeChange, "Type",
                    // caFrom, onCaFromChange, "From", caTo, onCaToChange, "  To"
                //)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TriggerSettingCardPreview() {
    TriggerSettingCard()
}