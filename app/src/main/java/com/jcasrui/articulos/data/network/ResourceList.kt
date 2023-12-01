package com.jcasrui.articulos.data.network

sealed class ResourceList {
    data class Success<T>(var data: T) : ResourceList()
    data class Error(var exception: Exception) : ResourceList()
}