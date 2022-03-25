package com.felixaraque.android_retrofit.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.adapters.AdapterComentarios
import com.felixaraque.android_retrofit.databinding.FragmentRecursoDetailBinding
import com.felixaraque.android_retrofit.model.*
import com.felixaraque.android_retrofit.viewmodel.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RecursoDetailFragment : Fragment() {

    private var usuario: Usuario ?= null
    private lateinit var comentarios: List<Comentario>
    private lateinit var viewModel: MainViewModel
    private lateinit var rvcomentarios: RecyclerView
    private lateinit var adapter: AdapterComentarios
    private lateinit var recurso: Recurso
    private var _binding: FragmentRecursoDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecursoDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        recurso = args!!.getSerializable("recurso") as Recurso
        usuario = args!!.getSerializable("usuario") as Usuario

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rvcomentarios  = binding.rvcomentarios

        initRV()
        showPajaro()
        getComentarios()
        binding.fabrecurso.setOnClickListener { view ->
            onClickfab()
        }

    }

    private fun onClickfab() {
        if (usuario == null) {
            msg("No estás logueado")
        }
        else {
            comentario()
        }
    }

    private fun comentario() {
        val builder = AlertDialog.Builder((activity as Activity))
        builder.setTitle("Comentario")
        val ll = LinearLayout((activity as Activity))
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutComentario = TextInputLayout((activity as Activity))
        textInputLayoutComentario.layoutParams = lp
        val etcomentario = EditText((activity as Activity))
        etcomentario.setPadding(0, 80, 0, 80)
        etcomentario.textSize = 20.0F
        etcomentario.hint = "Comentario"
        textInputLayoutComentario.addView(etcomentario)

        ll.addView(textInputLayoutComentario)

        builder.setView(ll)

        val fecha = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val fechaTXT = sdf.format(fecha)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val comentario = ComentarioPost(0,recurso.id, usuario!!.id,fechaTXT,etcomentario.text.toString())
            insertcomentario(comentario)
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun insertcomentario(comentario: ComentarioPost) {
        viewModel.saveComentario(recurso, comentario).observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                getComentarios()
            }
        })
    }

    private fun getComentarios() {
        viewModel.getComentarios(recurso.id).observe(viewLifecycleOwner, Observer { it ->
            it?.let{
                comentarios = it
                showComentarios()
            }
        })
    }

    private fun showComentarios() {
        adapter.setComentarios(comentarios)
    }

    private fun showPajaro() {
        binding.tvnombredetail.text = recurso.titulo
        Picasso.get().load(recurso.url).into(binding.ivfotodetail)
    }

    private fun initRV() {
        adapter = AdapterComentarios((activity as Activity),R.layout.rowcomentarios)
        rvcomentarios.adapter = adapter
        rvcomentarios.layoutManager = LinearLayoutManager((activity as Activity))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun msg(msg: String) {
        Toast.makeText((activity as Activity), msg, Toast.LENGTH_SHORT).show()
    }
}