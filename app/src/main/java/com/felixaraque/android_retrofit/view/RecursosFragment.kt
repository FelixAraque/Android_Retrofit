package com.felixaraque.android_retrofit.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.adapters.AdapterRecursos
import com.felixaraque.android_retrofit.databinding.FragmentRecursosBinding
import com.felixaraque.android_retrofit.model.Recurso
import com.felixaraque.android_retrofit.model.Zona
import com.felixaraque.android_retrofit.viewmodel.MainViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RecursosFragment : Fragment() {

    private lateinit var rvrecursos: RecyclerView
    private lateinit var adapter: AdapterRecursos
    private lateinit var recursos: List<Recurso>
    private lateinit var viewModel: MainViewModel
    private lateinit var zona: Zona
    private var _binding: FragmentRecursosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecursosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        zona = args!!.getSerializable("zona") as Zona

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rvrecursos  = binding.rvrecursos

        initRV()
        getRecursos()
    }

    private fun getRecursos() {
        viewModel.getRecursos(zona.id).observe(viewLifecycleOwner, Observer { it ->
            it?.let{
                recursos = it
                showRecursos()
            }
        })
    }

    private fun showRecursos() {
        adapter.setRecursos(recursos)
    }

    private fun initRV() {
        adapter = AdapterRecursos((activity as Activity),R.layout.rowrecursos)
        rvrecursos.adapter = adapter
        rvrecursos.layoutManager = LinearLayoutManager((activity as Activity))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}