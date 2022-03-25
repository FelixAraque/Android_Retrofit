package com.felixaraque.android_retrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.felixaraque.android_retrofit.api.MainRepository
import com.felixaraque.android_retrofit.model.*

class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getZonas(): MutableLiveData<List<Zona>> {
        val zonas = MutableLiveData<List<Zona>>()
        GlobalScope.launch(Dispatchers.Main) {
            zonas.value = repository.getZonas()
        }
        return zonas
    }

    fun getUserByNickAndPass(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            usuarioresponse.value = repository.getUserByNickAndPass(usuario)
        }
        return usuarioresponse
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            usuarioresponse.value = repository.saveUsuario(usuario)
        }
        return usuarioresponse
    }

    fun getRecursos(idzona: Long): MutableLiveData<List<Recurso>> {
        val pajaros = MutableLiveData<List<Recurso>>()
        GlobalScope.launch(Dispatchers.Main) {
            pajaros.value = repository.getRecursos(idzona)
        }
        return pajaros
    }

    fun getComentarios(idrecurso: Long): MutableLiveData<List<Comentario>> {
        val comentarios = MutableLiveData<List<Comentario>>()
        GlobalScope.launch(Dispatchers.Main) {
            comentarios.value = repository.getComentarios(idrecurso)
        }
        return comentarios
    }

    fun saveComentario(recurso: Recurso, comentario: ComentarioPost):MutableLiveData<ComentarioPost> {
        val comentarioresponse= MutableLiveData<ComentarioPost>()
        GlobalScope.launch(Dispatchers.Main) {
            comentarioresponse.value = repository.saveComentario(recurso, comentario)
        }
        return comentarioresponse
    }
}