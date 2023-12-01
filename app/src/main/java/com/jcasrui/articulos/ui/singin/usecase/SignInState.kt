package com.jcasrui.articulos.ui.singin.usecase

import com.jcasrui.articulos.data.network.Resource

// Las imagenes no se guardan en viewmodel, se guardan con un enum
sealed class SignInState {
    data object EmailEmptyError : SignInState()
    data object EmailFormatError : SignInState()
    data object PasswordEmptyError : SignInState()
    data object PasswordFormatError : SignInState()
    // Al añadir un parámetro hay que cambiar de dataObject a dataClass
    data class AuthenticationError(var message: String) : SignInState()
    data class Success(var account: Resource) : SignInState()
    // Se debe crear una clase que contiene un valor boleano que indica si se muestra el L
    data class Loading(var value: Boolean) : SignInState()
}