package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulala.blecom.utils.Tools
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer
import com.wulala.myuserviewmodeldemo.ui.components.TextFieldInput

// Stateless ui
@Composable
fun PowerChangeEventSetting(
    settingTitle: String,
    type: String, onTypeChanged: (String) -> Unit,
    from: String, onFromChanged: (String) -> Unit,
    to: String, onToChanged: (String) -> Unit,
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(settingTitle)
        NiceVerticalSpacer()
        TextFieldInput(type, onTypeChanged, "类型", 50.dp)
        NiceVerticalSpacer()
        TextFieldInput(from, onFromChanged, "From", 60.dp)
        NiceVerticalSpacer()
        TextFieldInput(
            to, onToChanged, "To", 50.dp
        )
    }
}

@Composable
fun SettingRow(
    tf1Value: String, onTF1Changed: (String) -> Unit, tf1LabelText: String,
    tf2Value: String, onTF2Changed: (String) -> Unit, tf2LabelText: String,
    sendBtnClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.Bottom) {
        NiceVerticalSpacer()
        TextFieldInput(tf1Value, onTF1Changed, tf1LabelText, ((tf1LabelText.length) * 15).dp)
        NiceVerticalSpacer()
        TextFieldInput(tf2Value, onTF2Changed, tf2LabelText, ((tf2LabelText.length) * 15).dp)
        NiceVerticalSpacer()
        Button(onClick = sendBtnClick) {
            Text("发送指令")
        }
    }
}

@Composable
fun SettingRow(
    settingTitle: String,
    tf1Value: String, onTF1Changed: (String) -> Unit, tf1LabelText: String,
    tf2Value: String?, onTF2Changed: (String) -> Unit, tf2LabelText: String?,
    tf3Value: String?, onTF3Changed: (String) -> Unit, tf3LabelText: String?,
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(settingTitle)
        NiceVerticalSpacer()
        TextFieldInput(tf1Value, onTF1Changed, tf1LabelText, ((tf1LabelText.length) * 15).dp)

        if (tf2Value != null && tf2LabelText != null) {
            NiceVerticalSpacer()
            TextFieldInput(tf2Value, onTF2Changed, tf2LabelText, ((tf2LabelText.length) * 15).dp)
        }

        if (tf3Value != null && tf3LabelText != null) {
            NiceVerticalSpacer()
            TextFieldInput(tf3Value, onTF3Changed, tf3LabelText, ((tf3LabelText.length) * 15).dp)
        }

    }
}

@Composable
fun EventIdTypeSetting(
    id: String, onIdChanged: (String) -> Unit,
    type: String, onTypeChanged: (String) -> Unit
) {
}

@Composable
@Preview(showBackground = true)
fun PowerChangeEventSettingPreview() {
    PowerChangeEventSetting("通道A", "a", {}, "b", {}, "c", {})
}

@Composable
fun DownDropMenu(onSelect: (Byte?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember {
        mutableStateOf("")
    }
    Text(text)
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Chanel A Type")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(onClick = { onSelect(1) }) {
            Text("固定")
        }
        DropdownMenuItem(onClick = { onSelect(2) }) {
            Text("随机")
        }
    }
}

fun select(value: Byte?) {
    println("$value")
}
