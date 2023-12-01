package com.jcasrui.articulos.data.account

open class AccountException(message: String=""): RuntimeException(message) {
    class InvalidEmailFormat: AccountException("Email con formato inválido")
}