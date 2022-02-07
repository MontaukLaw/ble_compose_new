package com.wulala.myuserviewmodeldemo.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NiceHorizonSpacer(){
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun NiceBiggerHorizonSpacer(){
    Spacer(modifier = Modifier.height(25.dp))
}

@Composable
fun NiceVerticalSpacer(){
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun NiceSmallVerticalSpacer(){
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun NiceHorizonDivider(){
    NiceHorizonSpacer()
    Divider()
    NiceHorizonSpacer()
}