package com.felixaraque.android_retrofit.api

import kotlinx.coroutines.Deferred
import com.felixaraque.android_retrofit.model.ComentarioPost
import com.felixaraque.android_retrofit.model.Respuesta
import com.felixaraque.android_retrofit.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface AvesService {
    @GET("zonas")
    fun getZonas(): Deferred<Response<Respuesta>>

    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>

    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @GET("zona/{idzona}/recursos")
    fun getRecursos(@Path("idzona") idzona: Long): Deferred<Response<Respuesta>>

    @GET("recurso/{idrecurso}/comentarios")
    fun getComentarios(@Path("idrecurso") idrecurso: Long): Deferred<Response<Respuesta>>

    @POST("recurso/{idrecurso}/comentario")
    fun saveComentario(@Path("idrecurso") idrecurso: Long,
                   @Body comentario: ComentarioPost): Deferred<Response<Respuesta>>

}
