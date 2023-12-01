package com.jcasrui.articulos.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jcasrui.articulos.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    // La clase binding se crea automáticamente igual que el nombre de nuestro layout
    private var _binding: FragmentSignUpBinding? = null // Campo
    private val binding                                 // Propiedad
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

        // Al inflater no se le pasa el layout porque tenemos el binding?
        //return inflater.inflate(R.layout.fragment_sing_up, container, false)
    }

    /**
     * Se utiliza esta función para inicializar los componentes que se han creado ya en la función onCreateView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Crear colección de datos
        val itemList = arrayListOf("Private", "Public", "Empty")
        // 2. Crear Adapter
        // La diferencia entre context y requireContext() es que context acepta nulos y requireContext() no acepta nulos
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)  // Este elemento añader un margen a las opciones de nuestro spinner??

        /*
        binding.spProfile.adapter = adapter     // IMPORTANTE: Si no utilizo la clase FragmentSingUpBinding correcta, no se va a reconoces el spProfile ni  va a funcionar el adapter
        binding.spProfile.setSelection(2)
        // 3. Iniciar el listener que se lanza cuando el usuario modifica el valor
        binding.spProfile.onItemClickListener = null
        */

        // Esto es un objeto de escucha
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val profile = parent?.adapter?.getItem(position)    // parent: spinner
                Toast.makeText(requireContext(), "Elemento pulsado $profile", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Se usa el modismo With que dado un objeto se puede modificar propiedades dentro del bloque
        // Esto es util para no tener que estar todoo el rato poniendo binding.spProgile
        with(binding.spProfile) {
            this.adapter = adapter
            setSelection(2) // Esto es para que por defecto haya uno seleccionado?
            onItemSelectedListener = listener
        }
    }
}