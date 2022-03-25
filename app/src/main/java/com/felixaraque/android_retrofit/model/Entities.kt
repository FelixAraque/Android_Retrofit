package com.felixaraque.android_retrofit.model

import java.io.Serializable

data class Zona(var id:Long=0, var nombre:String="",
                var localizacion:String="",
                var formaciones_principales:String="",
                var presentacion:String="",
                var geom_lat:Double=0.0,
                var geom_lon:Double=0.0
):Serializable

data class Recurso(var id:Long=0,
                   var zona:Long=0,
                   var titulo:String="",
                   var url:String=""
):Serializable

data class Usuario(var id:Int=0,
                   var nick:String="",
                   var pass:String=""
): Serializable

data class Comentario(var id:Long=0,
                      var recurso:String="",
                      var nick:String="",
                      var fecha:String="",
                      var comentario:String=""
)

data class ComentarioPost(var id:Long=0,
                      var recurso:Long=0,
                      var usuario:Int=0,
                      var fecha:String="",
                      var comentario:String=""
)

data class Respuesta(var zonas: List<Zona>,
                     var usuario: Usuario,
                     var recursos: List<Recurso>,
                     var comentarios: List<Comentario>,
                     var comentario: ComentarioPost

)