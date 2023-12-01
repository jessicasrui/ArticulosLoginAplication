package com.jcasrui.articulos.data.repository

import com.jcasrui.articulos.data.model.User
import com.jcasrui.articulos.data.network.Resource
import com.jcasrui.articulos.data.network.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Esto es una clase Singleton
 * Esta clase es accesible para todoo el proyecto. No se puede crear objetos de esta clase
 * constructor privado. Y tiene un objeto que contiene el listado de usuarios.
 */
class UserRepository private constructor() {
    companion object {
        val dataSet: MutableList<User> = initDataSetUser()

        init {
            initDataSetUser()
        }

        private fun initDataSetUser(): MutableList<User> {
            val dataSet: MutableList<User> = ArrayList()
            dataSet.add(User("Jessica", "Castro", "jessica@iesportada.com"))
            dataSet.add(User("Pablo", "Lopez", "pablo@iesportada.com"))
            dataSet.add(User("Manuel", "Moreno", "manuel@iesportada.com"))
            dataSet.add(User("Lola", "Jimenez", "lola@iesportada.com"))
            return dataSet
        }

        /**
         * La función que pregunta a Firebase/Room (Sqlite) por el usuario
         * no estamos utilizando email y password pero no hay problema, lo utilizamos al llamar login
         */
        suspend fun login(email: String, password: String): Resource {
            // Este código se ejecuta en un hilo especifico para operaciones entrada/salida (IO)
            withContext(Dispatchers.IO) {
                delay(1000)
                // Se ejecutará el código de consulta a Firbase que puede tardar más de 5 segundos y en ese
                // caso se obtiene el error ANR (Android Not Responding) porque puede bloquear la vista.
            }
            return Resource.Error(Exception("El password es incorrecto"))
            //return Resource.Success<>
            //return Result.Success(data = Account.create(1, Email("jcasrui@gmail.com"), "1234", "Jessica", AccountState.VERIFIED))
        }

        /**
         * Funcion suspendida
         * Esta funcion simula una consulta asincrona y devueve el conjunto de usuarios de la aplicacion
         */
        suspend fun getUserList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(2000)
                when {
                    dataSet.isEmpty() -> ResourceList.Error(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<User>)
                }
            }

        }
    }
}