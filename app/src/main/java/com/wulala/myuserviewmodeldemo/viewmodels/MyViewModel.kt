package com.wulala.myuserviewmodeldemo.viewmodels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel : ViewModel() {

    // viewmodel 的livedata, 即真正包含的数据
    val counterLiveDate: LiveData<Int>
        get() = counter

    private val counter = MutableLiveData<Int>()
    private var count = 0

    fun increaseCounter() {
        counter.value = ++count
    }
}

@JvmOverloads
@Composable
fun ScreenDemo(model: MainViewModel) {
    val count by model.counterLiveDate.observeAsState(0)
    Demo("This is $count") { model.increaseCounter() }
}

@Composable
fun Demo(text: String, onClick: () -> Unit = {}) {
    Column {
        BasicText(text)
        Button(
            onClick = onClick,
        ) {
            BasicText(text = "Add 1")
        }
    }
}

@Preview
@Composable
fun PreviewDemo() {
    Demo("Preview")
}