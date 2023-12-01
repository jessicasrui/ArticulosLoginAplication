package com.jcasrui.articulos.ui.singin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.jcasrui.articulos.R
import com.jcasrui.articulos.databinding.FragmentSignInBinding
import com.jcasrui.articulos.ui.singin.usecase.SignInState
import com.jcasrui.articulos.ui.singin.usecase.SignInViewModel

class SignInFragment : Fragment() {

    // Campos
    private var _binding: FragmentSignInBinding? = null

    // Se iniciara posteriormente
    //private lateinit var viewModel: SignInViewModel
    private val viewModel: SignInViewModel by viewModels()

    // Propiedad con su método get como en C#
    private val binding
        get() = _binding!!  // si hay un null exception es nuestra responsabilidad. !! indicamos que pueda ser nulo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Pasamos a la interfaz la instancia del ViewModel para que actualice/recoga los valores
        // del email y password automáticamente y asociar el evento onClick del botón a una función

        //TODO descomentar cuando funcipne el binding de signin
        //binding.viewmodel = this.viewModel
        // IMPORTANTE: Hay que establecer el Fragment/Activity vinculado al binding para actualizar
        // los valores del Binding en base al ciclo de vida
        //TODO descomentar cuando funcipne el binding de signin
        //binding.lifecycleOwner = this

        // Se asigna el objetode la clase interna creada a los Texinput
        binding.tieEmailSignIn.addTextChangedListener(LoginTextWatcher(binding.tieEmailSignIn))
        binding.tiePasswordSignIn.addTextChangedListener(LoginTextWatcher(binding.tiePasswordSignIn))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUserList.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_userListFragment)
        } // boton/UserList

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        } // boton/Registrarse

        // Este codigo ya no es necesario ya que se implmenta mediante Data Binding
        /*binding.btnLogin.setOnClickListener {
            //findNavController().navigate(R.id.action_signInFragment_to_userListFragment)
            viewModel.validateCredentials()

        }*/ // entrar/Iniciar secion

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SignInState.EmailEmptyError -> setEmailEmptyError()
                SignInState.PasswordEmptyError -> setPasswordEmptyError()
                is SignInState.AuthenticationError -> showMessage(it.message)
                is SignInState.Loading -> showProgressbar(it.value)
                else -> onSuccess()
            }
        })

    }

    /**
     * Mostrar un progressbar en el comienzo de una operacion larga como es una consulta
     * a la base de datos, Firebase o bien ocultar cuando la operacion ha terminado
     */
    private fun showProgressbar(value: Boolean) {
        if(value){
            findNavController().navigate(R.id.action_signInFragment_to_fragmentProgressDialog)
        } else {
            findNavController().popBackStack()
        }
    }

    /**
     * Función que muestra al usuario un mensaje
     */
    private fun showMessage(message: String) {
        //Toast.makeText(requireContext(), "Mi primer MVVM: $message", Toast.LENGTH_SHORT).show()
        val action = SignInFragmentDirections.actionSignInFragmentToBaseFragmentDialog("Error", message)
        // Navegamos al fragmento Dialog mediante la variable creada
        findNavController().navigate(action)    // No se indica R.id.
    }

    /**
     * Función que muestra el error del Email Empty
     */
    private fun setEmailEmptyError() {
        binding.tilEmailSignIn.error = getString(R.string.errEmailEmpty)
        // El cursor del foco se coloca en el til que tiene el error
        binding.tilEmailSignIn.requestFocus()
    }

    /**
     * Función que muestra el error del Email Empty
     */
    private fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(R.string.errPasswordEmpty)
        // El cursor del foco se coloca en el til que tiene el error
        binding.tilPasswordSignIn.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireContext(), "Caso de uso/Error", Toast.LENGTH_SHORT).show()
    }

    // Limpieza en el recolector de basura
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Con esta clase internal nos permite acceder a la clase externa
    //internal inner class LoginTextWatcher(private val textInput: TextInputLayout, private val message: String) : TextWatcher {

    // Le hemos pasado una variable a la clase como constructor, en el add de arriba
    internal inner class LoginTextWatcher(private val textView: TextInputEditText) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // no se usa
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // no se usa
        }

        // Implementar este metodo
        override fun afterTextChanged(s: Editable?) {
            when (textView.id) {
                R.id.tieEmailSignIn -> {
                    binding.tilEmailSignIn.error = null
                    binding.tilEmailSignIn.isErrorEnabled = false
                }

                R.id.tiePasswordSignIn -> binding.tilEmailSignIn.error = null
            }
        }

    }
}