package com.jcasrui.articulos.ui.userlist.usecase

import com.jcasrui.articulos.data.model.User

sealed class UserListState {
    data object NoDataError : UserListState()
    data class Success(val dataset: ArrayList<User>) : UserListState()
    data class Loading(val value: Boolean) : UserListState()
}