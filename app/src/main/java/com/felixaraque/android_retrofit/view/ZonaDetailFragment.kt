package com.felixaraque.android_retrofit.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.databinding.FragmentZonaDetailBinding
import com.felixaraque.android_retrofit.model.Usuario
import com.felixaraque.android_retrofit.model.Zona
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ZonaDetailFragment : Fragment() {

    private var usuario: Usuario ?= null
    private lateinit var zona: Zona
    private var _binding: FragmentZonaDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentZonaDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        zona = args!!.getSerializable("zona") as Zona
        usuario = args.getSerializable("usuario") as Usuario

        binding.fab.setOnClickListener { view ->
            onClickFab()
        }

        showInfo()
    }

    private fun onClickFab() {
        val args = Bundle()
        args.putSerializable("zona", zona)
        args.putSerializable("usuario", usuario)
        findNavController().navigate(R.id.action_ZonaDetailFragment_to_RecursosFragment, args)
    }

    private fun showInfo() {
        binding.tvnombredetail.text = zona.nombre
        binding.tvlocalizacionlatlondetail.text = "${zona.localizacion} (${zona.geom_lat},${zona.geom_lon})"
        binding.tvformacionesdetail.text = zona.formaciones_principales
        binding.tvpresentaciondetail.text = zona.presentacion
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}