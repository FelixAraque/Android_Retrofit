package com.felixaraque.android_retrofit.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.adapters.AdapterZonas
import com.felixaraque.android_retrofit.databinding.FragmentZonasBinding
import com.felixaraque.android_retrofit.model.Usuario
import com.felixaraque.android_retrofit.model.Zona
import com.felixaraque.android_retrofit.viewmodel.MainViewModel
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ZonasFragment : Fragment() {

    private var _binding: FragmentZonasBinding? = null
    private lateinit var rvzonas: RecyclerView
    private lateinit var adapter: AdapterZonas
    private lateinit var zonas: List<Zona>
    private lateinit var viewModel: MainViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentZonasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonFirst.setOnClickListener {
        //    findNavController().navigate(R.id.action_ZonasFragment_to_ZonaDetailFragment)
        //}

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rvzonas  = binding.rvzonas

        initRV()
        getZonas()

    }

    private fun getZonas() {
        viewModel.getZonas().observe(viewLifecycleOwner, Observer { it ->
            it?.let{
                zonas = it
                showZonas()
            }
        })
    }

    private fun showZonas() {
        adapter.setZonas(zonas)
    }

    private fun initRV() {
        adapter = AdapterZonas((activity as Activity),R.layout.rowzona)
        rvzonas.adapter = adapter
        rvzonas.layoutManager = LinearLayoutManager((activity as Activity))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}