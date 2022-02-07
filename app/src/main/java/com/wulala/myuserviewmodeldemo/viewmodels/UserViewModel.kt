package com.wulala.myuserviewmodeldemo

import androidx.lifecycle.LiveData
import com.wulala.myuserviewmodeldemo.viewmodels.User

/**
 * User 的ViewModel
 * 里面只有一个LiveData作为保存的数据
 */
class UserViewModel {

    // var user = UserLiveData()
    var user = User("", "")

    fun saveUser() {
        println("user like ${user.username}")
    }
}

class UserLiveData : LiveData<User>(User("", ""))
// class UserLiveData : LiveData<User>()
