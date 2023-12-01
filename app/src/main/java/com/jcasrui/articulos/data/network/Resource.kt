package com.jcasrui.articulos.data.network
// poner este archivo dentro de un directorio networkstate

// T, E --> tipos genericos
/**
 * Esta clase sellada representa la respuesta de un servicio API REST, firebase
 * donde se declara la clase Error que almacenara los errores de la infraestructura.
 * Y el caso de éxito sea una coleccion de datos.
 */
sealed class Resource {
    //data class Success<T, E>(var data: T, var response: E) : Resource()
    //data class Success<T>(var data: Collection<T>) : Resource() // List<String>, Set<User>, List<Accunt>
    data class Success<T>(var data: T) : Resource() // List<String>, Set<User>, List<Accunt>
    data class Error(var exception: Exception) : Resource()
}