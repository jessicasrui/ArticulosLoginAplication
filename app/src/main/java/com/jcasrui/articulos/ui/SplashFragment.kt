package com.jcasrui.articulos.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jcasrui.articulos.R
import com.jcasrui.articulos.databinding.FragmentSplashBinding

private const val WAIT_TIME: Long = 2000L

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null  // Campo
    private val binding                                  // Propiedad
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        var r = Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
        // Manejador
        Handler(Looper.getMainLooper()).postDelayed(r, WAIT_TIME)
    }
}