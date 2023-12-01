package com.jcasrui.articulos.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcasrui.articulos.databinding.FragmentUserListBinding
import com.jcasrui.articulos.adapter.UserAdapter
import com.jcasrui.articulos.data.model.User
import com.jcasrui.articulos.ui.userlist.usecase.UserListState
import com.jcasrui.articulos.ui.userlist.usecase.UserListViewModel

class UserListFragment : Fragment(), UserAdapter.OnUserClick {

    private var _binding: FragmentUserListBinding? = null   // Campo
    private val binding                                     // Propiedad
        get() = _binding!!

    private val viewmodel: UserListViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUpDataSetUser() //??setUpUserAdapter() // se debe llamar antes que la creación del Recycler
        setUpUserRecycler()

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                is UserListState.Loading -> showProgressBar(it.value)
                UserListState.NoDataError -> showNodataError()
                is UserListState.Success -> onSuccess(it.dataset)
            }
        })
    }

    /**
     * Cuando el fragment se inicia debe pedir el listado de usuarios al ViewModel-Infraestructura
     * (Firebase, Json, Room ...)
     */
    override fun onStart() {
        super.onStart()
        viewmodel.getUserList()
    }
    /**
     * Funcion que contiene el listado de usuarios
     */
    private fun onSuccess(dataset: ArrayList<User>){
        // Desactivar la animacion y visualizar el recyclerview
        hideNodataError()
        userAdapter.update(dataset)
    }
    private fun hideNodataError(){
        // TODO Crear en el xml del user list una id con animationView
        //binding.animationView.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }
    /**
     * Funcion que muestra el error de no hay datos
     */
    private fun showNodataError(){
        // TODO Crear en el xml del user list una id con animationView
        //binding.animationView.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }
    /**
     * Mostrar el progressbar en la vista
     */
    private fun showProgressBar(value: Boolean){

    }

    /**
     * Función que inicializa el RecyclerView que muestra el listado de usuario de la aplicación
     */
    private fun setUpUserRecycler(){
        // Crear el Adapter con los valores en el Constructor primariio
        userAdapter = UserAdapter(this){
            Toast.makeText(requireContext(), "Usuario seleccionado mediante $it", Toast.LENGTH_SHORT).show()
        }

        // 1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.rvUser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = userAdapter
        }
    }

    /**
     * Esta función se llamará de forma asíncrona
     */
    override fun userClick(user: com.jcasrui.articulos.data.model.User) {
        Toast.makeText(requireContext(), "Pulsación corta en el usuario $user", Toast.LENGTH_SHORT).show()
    }

    override fun userOnLongClick(user: com.jcasrui.articulos.data.model.User) {
        Toast.makeText(requireContext(), "Pulsación larga en el usuario $user", Toast.LENGTH_SHORT).show()
    }
}