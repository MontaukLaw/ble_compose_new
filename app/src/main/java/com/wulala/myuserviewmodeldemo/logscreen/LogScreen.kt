package com.wulala.myuserviewmodeldemo.logscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import com.wulala.blecom.viewmodels.BLEViewModel

class LogViewModel : ViewModel() {

    private var _logItems = MutableLiveData(listOf<String>())
        private set

    val logItems: LiveData<List<String>> = _logItems

    @RequiresApi(Build.VERSION_CODES.O)
    fun addItem(item: String) {
        val myObj = LocalTime.now()
        val hms = myObj.toString()
        _logItems.value = _logItems.value!! + listOf(hms + item)

        if (_logItems.value!!.size > 8) {
            _logItems.value = _logItems.value!!.toMutableList().also {
                it.remove(_logItems.value!!.toMutableList()[0])
            }
        }
        // println("log size is : ${logItems.size}")
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun LogScreen(
    logs: List<String>
) {

    // val (logSize, onLogChange) = rememberSaveable { mutableStateOf(logs.size) }
    // val (items, onLogChange) = rememberSaveable { mutableStateOf(bleViewModel.simpleTextLog.value!!) }

    Column {
        // Text("log size : $logSize")

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = logs) {
                Text(
                    text = it,
                )
            }
        }
    }
    // For quick testing, a random item generator button
}


@RequiresApi(Build.VERSION_CODES.O)
fun addToLog(newLog: String, logs: List<String>) {
    val myObj = LocalTime.now()
    val hms = myObj.toString()
    val newList = arrayListOf<String>()

}


@Preview(showBackground = true)
@Composable
fun PreviewLogScreen() {
    val items = listOf(
        "Learn compose",
        "Take the codelab",
        "Apply state",
        "Build dynamic UIs"
    )
    // LogScreen(items)
}
