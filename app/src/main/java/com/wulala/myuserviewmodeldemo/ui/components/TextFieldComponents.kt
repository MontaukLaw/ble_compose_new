package com.wulala.myuserviewmodeldemo.ui.components

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val TAG = "MyTextField"

@Composable
fun TextFieldInputWithFocus(input: String?, inputChanged: (String) -> Unit, labelText: String?, width: Dp, focusRequester: FocusRequester) {
    // var text = remember { mutableStateOf("") }
    val focusRequesterInside = remember { FocusRequester() }
    var textVal = "not change"
    // Text(textVal)
    if (input != null && labelText != null) {

//        TextField(
//            value = input,
//            onValueChange = {
//                inputChanged(it)
//                textVal = it
//            },
//            singleLine = true,
//            // label = { Text(labelText) },
//            modifier = Modifier
//                .width(width)
//                .focusRequester(focusRequester)
//                .focusOrder(focusRequesterInside)
//                .onFocusChanged {
//                    textVal = "TextFieldInputWithFocus: ${it.isFocused}"
//                }
//                .focusable(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            // onTextInputStarted = { textVal = "changed" },
//        )

        OutlinedTextField(
            modifier = Modifier
                .focusOrder(focusRequesterInside)
                .width(width)
                .focusable(),
            value = input,
            onValueChange = inputChanged,
            singleLine = true,
            label = { Text(labelText) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun TextFieldInput(input: String?, inputChanged: (String) -> Unit, labelText: String?, width: Dp) {
    if (input != null && labelText != null) {
        OutlinedTextField(
            modifier = Modifier
                .width(width)
                .focusable(),
            value = input,
            onValueChange = inputChanged,
            singleLine = true,
            label = { Text(labelText) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TextFieldInputDemo() {
    // TextFieldInput("0x01", {}, "input", 80.dp)
}