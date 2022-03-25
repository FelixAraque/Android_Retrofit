package com.felixaraque.android_retrofit.api

import com.felixaraque.android_retrofit.model.*

class MainRepository {
    val service = WebAccess.avesService

    suspend fun getZonas(): List<Zona> {
        val webResponse = service.getZonas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.zonas
        }
        return emptyList()
    }

    suspend fun getUserByNickAndPass(usuario: Usuario): Usuario? {
        var usuarioresponse:Usuario? = null
        val webResponse = service.getUserByNickAndPass(usuario.nick,usuario.pass).await()
        if (webResponse.isSuccessful) {
            usuarioresponse = webResponse.body()!!.usuario
        }
        return usuarioresponse
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        var usuarioresponse:Usuario? = null
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            usuarioresponse = webResponse.body()!!.usuario
        }
        return usuarioresponse
    }

    suspend fun getRecursos(idzona: Long): List<Recurso> {
        val webResponse = service.getRecursos(idzona).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.recursos
        }
        return emptyList()
    }

    suspend fun getComentarios(idrecurso: Long): List<Comentario> {
        val webResponse = service.getComentarios(idrecurso).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentarios
        }
        return emptyList()
    }

    suspend fun saveComentario(recurso: Recurso, comentario: ComentarioPost): ComentarioPost? {
        var puntoresponse:ComentarioPost? = null
        val webResponse = service.saveComentario(recurso.id, comentario).await()
        if (webResponse.isSuccessful) {
            puntoresponse = webResponse.body()!!.comentario
        }
        return puntoresponse
    }
}