package com.jcasrui.articulos.data.repository
// este fichero va dentro de infraestructura y dentro de firebase, en el main, en una captera llamada
// en el gradle de infraestructure añadir la dependencia
// Implementar acceso a firebase?
// implementation (proyect(":domain:entity"))

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.jcasrui.articulos.data.account.Account
import com.jcasrui.articulos.data.model.Email
import com.jcasrui.articulos.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthFirebase {
    companion object {

        private var authFirebase = FirebaseAuth.getInstance()

        suspend fun login(email: String, password: String): Resource {
            return withContext(Dispatchers.IO) {
                try {
                    val authResult: AuthResult =
                        authFirebase.signInWithEmailAndPassword(email, password).await()
                    val account = Account.create(
                        authResult.user.hashCode(),
                        Email(email),
                        password,
                        authResult.user!!.displayName
                    )
                    Resource.Success(account)

                    //var id: Int = authResult.user!!.hashCode()
                    //var email = Email(email)
                    //var name: String = authResult.user!!.displayName!!
                    //account = Account.create(id, email, password, name)
                } catch (e: Exception) {
                    Resource.Error(e)
                }
            }
            //return Resource.Success(account)
            //return Resource.Success(data = account)
        }
    }
}