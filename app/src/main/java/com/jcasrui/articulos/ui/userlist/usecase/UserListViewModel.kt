package com.jcasrui.articulos.ui.userlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcasrui.articulos.data.model.User
import com.jcasrui.articulos.data.network.ResourceList
import com.jcasrui.articulos.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserListViewModel() : ViewModel() {
    private var state = MutableLiveData<UserListState>()

    fun getState(): LiveData<UserListState>{
        return state
    }

    /**
     * Funcion que pide el listado de usuarios al repositorio
     */
    fun getUserList(){
        //Iniciar la corrutina
        viewModelScope.launch {
            // Opcion 1: me devuelve difirentes estados
            state.value = UserListState.Loading(true)
            when(val result = UserRepository.getUserList()){
                is ResourceList.Success<*> -> state.value = UserListState.Success(result.data as ArrayList<User>)
                is ResourceList.Error -> state.value = UserListState.NoDataError
            }

            // Opcion 2: me devuelve la lista, porque solo tenemos 2 posibles estados de error y el de exito
            /*val data = UserRepository.getUserList()
            when{
                data.isEmpy() -> state.value = UserListState.Success(data)
                else -> state.value = UserListState.NoDataError
            }*/
        }
    }
}