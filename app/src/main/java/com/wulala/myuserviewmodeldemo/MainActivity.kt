package com.wulala.myuserviewmodeldemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wulala.blecom.scan.ScannerStateLiveData
import com.wulala.blecom.upload.entity.GameState
import com.wulala.blecom.utils.CmdTestFactory.*
import com.wulala.blecom.utils.Utils
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.myus.UserForm
import com.wulala.myuserviewmodeldemo.logscreen.LogScreen
import com.wulala.myuserviewmodeldemo.scanner.Scanner
import com.wulala.myuserviewmodeldemo.screens.UploadScreen
import com.wulala.myuserviewmodeldemo.ui.theme.MyUserViewModelDemoTheme
import com.wulala.myuserviewmodeldemo.viewmodels.User
import no.nordicsemi.android.ble.livedata.state.ConnectionState

class MainActivity : ComponentActivity() {

    // var userViewModel = UserViewModel();

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var bleViewModel = ViewModelProvider(this).get(BLEViewModel::class.java)

            // var model = ViewModelProvider(this).get(MainViewModel::class.java)
            // bindObserver(bleViewModel)

            // 等同于上面的东西
            // val logViewModel by viewModels<LogViewModel>()

            // val items:List<String> by bleViewModel.logs.observeAsState(listOf())
            // val items:List<String> by bleViewModel.simpleTextLog.observeAsState(listOf())

            // val items: List<String> by logViewModel.simpleTextLog.observeAsState(listOf())

            // var uiLogs = listOf<String>()

            val items: List<String> by bleViewModel.simpleTextLog.observeAsState(listOf())

            MyUserViewModelDemoTheme {
                Scaffold(
                    drawerContent = {
                        Column(modifier = Modifier.padding(start = 25.dp, top = 25.dp)) {
                            ScanScreen({ bleViewModel.startScan() }, "开启扫描", bleViewModel)
                            LogScreen(logs = items)
                        }
                    }

                ) {
                    val scrollState = rememberScrollState()
                    // Smooth scroll to specified pixels on first composition
                    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

                    // Greeting("Android")
                    // InputTextFieldTest()
                    // UserFormScreen(userViewModel.user) { userViewModel.saveUser() }

                    // ScreenDemo(model)
                    Column(
                        modifier = Modifier
                            .padding(25.dp)
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {

                        UploadScreen("TestMsg")

                        CmdScreen(
                            cmdGameStart = { bleViewModel.sendTestCmd(testCmdGameStart) },
                            cmdBind = { bleViewModel.sendTestCmd(testCmdBind) },
                            cmdUnbind = { bleViewModel.sendTestCmd(testCmdUnbind) },
                            cmdGameSetting = { bleViewModel.sendTestCmd(testCmdGameSetting) },
                            cmdEventSetting = bleViewModel::sendEventSetting,
                            cmdGameStop = { bleViewModel.sendTestCmd(testCmdGameStop) },
                            cmdLoadAllConfig = { bleViewModel.sendTestCmd(testCmdLoadAll) },
                            cmdRemoveEvent = { bleViewModel.sendTestCmd(testCmdRemoveEvent) },
                            cmdTriggerSetting = { bleViewModel.sendTestCmd(testCmdTriggerSetting) },
                            cmdWaveDownload = { bleViewModel.sendTestCmd(testCmdDownloadWave) },
                            onSubDeviceSelected = bleViewModel::getSelectedSubDevice,
                            cmdKeepSending = bleViewModel::keepSendingCmd
                        )

                        // Button({ bleViewModel.simpleTextLog.addNewLog("aaaa") }) { Text("TestLog") }
                        // Button({ logViewModel.addItem("aaaa") }) { Text("TestLog") }

                        // bleViewModel.logs
                        // LogScreen()

                    }
                }

            }
        }
    }

/*
    fun bindObserver(viewModel: BLEViewModel, gameState: String, stateText: (ConnectionState) -> Unit) {
        // 获取连接状态
        var connStr: String

        // 获取连接状态
        viewModel.getConnectionState()
            .observe(this, { connectionState: ConnectionState? ->
                connStr = Utils.transState(connectionState)
            })
        viewModel.scannerStateLiveData.observe(this, { scannerState: ScannerStateLiveData ->
            if (scannerState.isScanningStarted) {
                //activityMainBinding.startScanBtn.setEnabled(false)
            } else {
                //activityMainBinding.startScanBtn.setEnabled(true)
            }
        })
        // 游戏状态

        // 游戏状态
        viewModel.getGameState().observe(this, Observer { gameState: GameState ->
            //activityMainBinding.gameStateTv.setText(gameState.gameStateStr)
            // activityMainBinding.gameTimeLeftTv.setText(gameState.countDownTimeSecText)
            if (gameState.isIfGaming) {
                // activityMainBinding.startGameBtn.setEnabled(false)
            } else {
                // activityMainBinding.startGameBtn.setEnabled(true)
            }
        })
    }

    */
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyUserViewModelDemoTheme {
        Greeting("Android")
    }
}

@Composable
fun UserFormScreen(user: User, submit: () -> Unit) {
    UserForm(user, submit = submit)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScanScreen(onClick: () -> Unit, btnStr: String, viewModel: BLEViewModel) {
    val connState by viewModel.getConnectionState().observeAsState()
    Scanner(onClick, "开启扫描", Utils.transState(connState))
}
