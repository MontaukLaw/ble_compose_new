package com.wulala.myuserviewmodeldemo.fortest

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulala.myuserviewmodeldemo.ui.components.NiceHorizonDivider
import com.wulala.myuserviewmodeldemo.ui.components.NiceVerticalSpacer
import com.wulala.myuserviewmodeldemo.ui.theme.MyUserViewModelDemoTheme


@Composable
fun TestItemCardV2(
    testName: String,
    cmdRow: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(testName, fontWeight = FontWeight.Bold)
                }
                NiceHorizonDivider()
                cmdRow()
            }
        }
    }
}

@Composable
fun TestItemCardSimple(
    testName: String,
    cmdRow: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        var ifExpand by remember { mutableStateOf(false) }
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(testName, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { ifExpand = !ifExpand }) {
                        if (ifExpand) {
                            Icon(Icons.Filled.ExpandLess, contentDescription = "Localized description")
                        } else {
                            Icon(Icons.Filled.ExpandMore, contentDescription = "Localized description")
                        }
                    }
                }
                if (ifExpand) {
                    NiceHorizonDivider()
                    cmdRow()
                }
            }
        }
    }
}

@Composable
fun TestItemCard(
    relayUploadMsgStr: String,
    testName: String,
    cmdSentMsgStr: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {

        var ifExpand by remember { mutableStateOf(false) }

        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(testName, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { ifExpand = !ifExpand }) {
                        if (ifExpand) {
                            Icon(Icons.Filled.ExpandLess, contentDescription = "Localized description")
                        } else {
                            Icon(Icons.Filled.ExpandMore, contentDescription = "Localized description")
                        }
                    }
                }
                if (ifExpand) {
                    NiceHorizonDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text("回应: ")
                        Text(relayUploadMsgStr)
                    }
                    NiceHorizonDivider()
                    Text(cmdSentMsgStr)
                    NiceHorizonDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {}) { Text("发送") }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MyCardPreview() {
    MyUserViewModelDemoTheme {
        TestItemCard("", "", "")
    }
}