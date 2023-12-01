package com.jcasrui.articulos.ui.singin.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcasrui.articulos.data.network.Resource
import com.jcasrui.articulos.data.repository.AuthFirebase
import kotlinx.coroutines.launch

const val TAG = "ViewModel"

class SignInViewModel : ViewModel() {
    // LiveData que controlan los datos introducidos en la UI
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()


    //LiveData que tendrá si Observador en el Fragment y controla las excepciones/casos de uso
    // de la operación Login
    private var state = MutableLiveData<SignInState>()

    // Crear la clase sellada que permitirá gestionar las excepciones de la vista


    /**
     * Esta función se ejecuta directamente desde el fichero XML al usa dataBinding
     * android:onClick="@{()->viewmodel.validateCredentials()"
     */
    suspend fun validateCredentials() {
        // Ejemplo de prueba
        //Log.i(TAG, "El email es: ${email.value} y el password es ${password.value}")
        //email.value = "nuevo valor"

        when {
            //email.value!!.isEmpty() -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(email.value) -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignInState.PasswordEmptyError
            // EmailFormat
            // PasswordFormat
            else -> {
                val account: Resource = AuthFirebase.login(email.value!!, password.value!!)
                state.value = SignInState.Success(account)

                // Se crea una corrutina que suspende el hilo principal hasta que el bloque
                // withContext del repositorio termina de ejecutarse
                viewModelScope.launch {
                    // Vamos a ejecutar el Login del repositorio -> que pregunta a la capa de la infraestructura
                    state.value = SignInState.Loading(true)
                    // La respuesta del repositorio es asincrona
                    //val result = UserRepository.login(email.value!!, password.value!!)
                    val result = AuthFirebase.login(email.value!!, password.value!!)

                    // ES OBLIGATORIO: pusar/quitar el FragmentDialog antes de mostrar el error. Ya que el Fragment SignIn está pausado
                    state.value = SignInState.Loading(false)

                    // is cuando sea un data class (IMPORTANTE para el EXAMEN)
                    when (result) {
                        // Como el tipo de Success es T, que admite toodo tipo de dato, ponemos un *
                        is Resource.Success<*> -> {
                            // Aquí tenemos que hacer un Casting Seguro porque el tipo de dato es generico
                            //val account: Resource = AuthFirebase.login(email.value!!, password.value!!)
                            state.value = SignInState.Success(account)
                            Log.i(TAG, "Login correcto del usuario")
                        }

                        is Resource.Error -> {
                            Log.i(TAG, "Información del dato ${result.exception.message}")
                            state.value =
                                SignInState.AuthenticationError(result.exception.message!!)
                        }
                    }
                }
            }
        }
    }

    /**
     * Se crea solo la función de obtención de la variable State. No se puede modificar su valor
     * fuera de ViewModel
     */
    fun getState(): LiveData<SignInState> {
        return state
    }
}