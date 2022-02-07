package com.wulala.myuserviewmodeldemo.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldInput(input: String?, inputChanged: (String) -> Unit, labelText: String?, width: Dp) {
    // var text = remember { mutableStateOf("") }
    if (input != null && labelText != null) {
        OutlinedTextField(
            modifier = Modifier.width(width),
            value = input,
            onValueChange = inputChanged,
            singleLine = true,
            label = { Text(labelText) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TextFieldInputDemo() {
    TextFieldInput("0x01", {}, "input", 80.dp)
}