package com.wulala.myuserviewmodeldemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wulala.blecom.utils.CmdTestFactory.*
import com.wulala.blecom.utils.Utils
import com.wulala.blecom.viewmodels.BLEViewModel
import com.wulala.blecom.viewmodels.SimpleBLEViewModel
import com.wulala.myus.UserForm
import com.wulala.myuserviewmodeldemo.fortest.*
import com.wulala.myuserviewmodeldemo.logscreen.LogScreen
import com.wulala.myuserviewmodeldemo.scanner.ScanResultText
import com.wulala.myuserviewmodeldemo.scanner.Scanner
import com.wulala.myuserviewmodeldemo.screens.*
import com.wulala.myuserviewmodeldemo.ui.theme.MyUserViewModelDemoTheme
import com.wulala.myuserviewmodeldemo.viewmodels.User

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var simpleBleViewModel = ViewModelProvider(this).get(SimpleBLEViewModel::class.java)
            var expanded by remember { mutableStateOf(false) }
            // var cmdSentMsgStr by remember { mutableStateOf("发送消息") }
            val connState by simpleBleViewModel.getConnectionState().observeAsState()

            val humanLog: String by simpleBleViewModel.logForHuman.observeAsState("中继反馈")

            MyUserViewModelDemoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("中继设备测试程序") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    expanded = true
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            },
                        )
                    },
                    drawerContent = {
                        Column(modifier = Modifier.padding(start = 25.dp, top = 25.dp)) {
                        }
                    },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {
                            // 开启扫描
                            simpleBleViewModel.startScan()
                        },
                            // text = { Text("未连接") },
                            text = { ScanResultText(Utils.transState(connState)) },
                            icon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search"
                                )
                            }
                        )
                    },
                ) {
                    Column {
                        SimpleCmdDisplay(humanLog)

                        NavHost(navController = navController, startDestination = "test0902_page") {
                            // composable("main_page") { MainScreen(bleViewModel) { cmdSentMsgStr = it } }
                            composable("special_cmd_page") { SpecialCmdScreen(simpleBleViewModel) }
                            // composable("lock_cmd_page") { LockCmdScreen(bleViewModel) }
                            composable("as_peripheral_cmd_page") { AsPeripheralCmdScreenSimple(simpleBleViewModel) }
                            composable("cmd_test_page") { CmdTestScreen(simpleBleViewModel) }
                            composable("lock_cmd_page") { LockCmdScreen(simpleBleViewModel) }
                            composable("test0902_page") { Test0902Screen(simpleBleViewModel) }

                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        /*
                         DropdownMenuItem(onClick = { navController.navigate("main_page") }) {
                             Text("主页面")
                         }
                         DropdownMenuItem(onClick = { navController.navigate("special_cmd_page") }) {
                             Text("命令发送页面")
                         }
                         */
                        DropdownMenuItem(onClick = { navController.navigate("special_cmd_page") }) {
                            Text("旧版测试命令")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { navController.navigate("as_peripheral_cmd_page") }) {
                            Text("SM-Link测试部分")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { navController.navigate("cmd_test_page") }) {
                            Text("命令测试")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { navController.navigate("lock_cmd_page") }) {
                            Text("方形锁测试页面")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { navController.navigate("test0902_page") }) {
                            Text("0902测试内容")
                        }
                    }

                }
            }

        }
    }
}

class MainActivityMain : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // var relayUploadMsgStr by remember { mutableStateOf("收到的") }
            val navController = rememberNavController()

            var rightFB by remember { mutableStateOf("正确答案") }

            var bleViewModel = ViewModelProvider(this).get(BLEViewModel::class.java)

            // log list
            val items: List<String> by bleViewModel.simpleTextLog.observeAsState(listOf())

            val connState by bleViewModel.getConnectionState().observeAsState()

            val relayUploadMsgStr: String by bleViewModel.renewLog().observeAsState("")

            val humanLog: String by bleViewModel.logForHuman.observeAsState("翻译后的Log")

            // val connectState:String by bleViewModel.connectionState.observeAsState(String)
            var expanded by remember { mutableStateOf(false) }

            var cmdSentMsgStr by remember { mutableStateOf("发送消息") }

            MyUserViewModelDemoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("中继设备测试程序") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    expanded = true
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            },
                        )
                    },
                    drawerContent = {
                        Column(modifier = Modifier.padding(start = 25.dp, top = 25.dp)) {
                            LogScreen(logs = items)
                        }
                    },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {
                            // 开启扫描
                            bleViewModel.startScan()
                        },
                            // text = { Text("未连接") },
                            text = { ScanResultText(Utils.transState(connState)) },
                            icon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search"
                                )
                            }
                        )
                    },
                ) {
                    Column {
                        CmdDisplay(relayUploadMsgStr, cmdSentMsgStr, humanLog)

                        NavHost(navController = navController, startDestination = "as_peripheral_cmd_page") {
                            composable("main_page") { MainScreen(bleViewModel) { cmdSentMsgStr = it } }
                            // composable("special_cmd_page") { SpecialCmdScreen(bleViewModel) }
                            // composable("lock_cmd_page") { LockCmdScreen(bleViewModel) }
                            composable("as_peripheral_cmd_page") { AsPeripheralCmdScreen(bleViewModel) }
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(onClick = { navController.navigate("main_page") }) {
                            Text("主页面")
                        }
                        DropdownMenuItem(onClick = { navController.navigate("special_cmd_page") }) {
                            Text("命令发送页面")
                        }
                        DropdownMenuItem(onClick = { navController.navigate("lock_cmd_page") }) {
                            Text("方形锁测试页面")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Text("空白页面")
                        }
                    }

                }
            }
        }
    }
}

////////////////////////////////////// 暂时不用下面的内容 /////////////////////////////

class MainActivityOld : ComponentActivity() {

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



