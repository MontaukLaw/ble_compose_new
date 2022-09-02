package com.wulala.myuserviewmodeldemo.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wulala.blecom.core.SuMBLEManager
import com.wulala.blecom.viewmodels.BLEViewModel

const val channelAZeroX = 20f
const val channelAZeroY = 400f
const val xAxisLen = 800f

const val channelBZeroY = 900f
const val channelBZeroX = 20f

const val barNumber = 25
const val barWidth = 30f

@Composable
fun PowerCurveScreen(viewModel: BLEViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {

            // var barHeight = 100f
            // var barHeightList = mutableListOf<Float>()

            val barAHeightList by viewModel.caPower.observeAsState()
            val barBHeightList by viewModel.cbPower.observeAsState()

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                // Text(barHeightList?.size.toString())
                if (barAHeightList?.size == SuMBLEManager.POWER_LIST_LEN) {
                    Text("通道A" + barAHeightList?.get(SuMBLEManager.POWER_LIST_LEN - 1).toString())
                }

                Spacer(modifier = Modifier.width(2.dp))

                if (barAHeightList?.size == SuMBLEManager.POWER_LIST_LEN) {
                    Text("通道B" + barBHeightList?.get(SuMBLEManager.POWER_LIST_LEN - 1).toString())
                }
            }

            DrawXAxis(channelAZeroX, channelAZeroY, xAxisLen, Color.Blue)
            DrawYAxis(channelAZeroX, channelAZeroY, 400f, Color.Blue)

            DrawXAxis(channelBZeroX, channelBZeroY, xAxisLen, Color.Red)
            DrawYAxis(channelBZeroX, channelBZeroY, 400f, Color.Red)

            DrawPowerBars(barAHeightList, barBHeightList)
        }
    }
}

// height 是从0-2000,
// 对应 0-400, 可以除以5
const val SCALE = 5

@Composable
fun DrawPowerBars(powerListCA: List<Int>?, powerListCB: List<Int>?) {
    if (powerListCA != null) {
        for ((index, height) in powerListCA.withIndex()) {
            // println("$index,$height")
            DrawBar(Color.Green, channelAZeroX + (index * barWidth), channelAZeroY - (height / SCALE).toFloat(), (height / SCALE).toFloat())
        }
    }

    if (powerListCB != null) {
        for ((index, height) in powerListCB.withIndex()) {
            // println("$index,$height")
            DrawBar(Color.Gray, channelBZeroX + (index * barWidth), channelBZeroY - (height / SCALE).toFloat(), (height / SCALE).toFloat())
        }
    }
}

@Composable
fun DrawYAxis(zeroX: Float, zeroY: Float, len: Float, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(
            start = Offset(x = zeroX, y = zeroY),
            end = Offset(x = zeroX, y = zeroY - len),
            color = color,
            strokeWidth = 5f
        )
    }
}

@Composable
fun DrawXAxis(zeroX: Float, zeroY: Float, len: Float, color: Color) {

    Canvas(modifier = Modifier) {
        drawLine(
            start = Offset(x = zeroX, y = zeroY),
            end = Offset(x = zeroX + len, y = zeroY),
            color = color,
            strokeWidth = 5f
        )
    }
}

@Composable
fun DrawBar(color: Color, topLeftX: Float, topLeftY: Float, barHeight: Float) {
    Canvas(modifier = Modifier) {
        drawRect(
            color = color,
            topLeft = Offset(topLeftX, topLeftY),
            size = Size(barWidth, barHeight),
            alpha = 0.3f
        )
    }
}