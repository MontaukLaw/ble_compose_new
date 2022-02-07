package com.wulala.myus

import com.wulala.myuserviewmodeldemo.viewmodels.User

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(input: String, onChange: (String) -> Unit, modifier: Modifier, label: @Composable () -> Unit) {

    OutlinedTextField(input, onValueChange = onChange, modifier = modifier, label = label)
}

@Composable
fun UserForm(user: User, submit: () -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Spacer(modifier = Modifier.height(25.dp))

        Text("欢迎你回来: ${username}")

        Spacer(modifier = Modifier.height(25.dp))

        InputTextField(input = username, onChange = {
            username = it
            user.username = username
            println("$username")

        }, modifier = Modifier, label = { Text("用户名") })

        Spacer(modifier = Modifier.height(25.dp))

        InputTextField(password, {
            password = it
            user.password = password

        }, modifier = Modifier, label = { Text("密码") })

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedButton(onClick = submit, modifier = Modifier) {
            Text("提交")
        }
    }
}

@Composable
fun UserFormTest(formSubmit: () -> Unit) {
    val user = User("", "")
    UserForm(user, submit = formSubmit)
}

@Composable
fun InputTextFieldTest() {
    InputTextField("", {}, modifier = Modifier, label = { Text("用户名") })
}

