package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SubDeviceSelectorScreen(
    onSelect:(String)->Unit
) {
    // We have two radio buttons and only one can be selected
    val radioOptions = listOf("黄", "红", "紫", "蓝", "青", "绿")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Row(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            println("$text")

                            onSelect(text)
                        },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSubDeviceSelectorScreen() {
    val radioOptions = listOf("黄", "红", "紫", "蓝", "青", "绿")

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    // SubDeviceSelectorScreen(radioOptions, selectedOption, onOptionSelected)
}