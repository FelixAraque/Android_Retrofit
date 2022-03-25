package com.felixaraque.android_retrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.model.Comentario

class AdapterComentarios(val context: Context,
                         val layout: Int
                    ) : RecyclerView.Adapter<AdapterComentarios.ViewHolder>() {

    private var dataList: List<Comentario> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComentarios(comentarios: List<Comentario>) {
        this.dataList = comentarios
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comentario){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvfechacomentario = itemView.findViewById(R.id.tvfechacomentario) as TextView
            val tvtextocomentario = itemView.findViewById(R.id.tvtextocomentario) as TextView
            val tvusuariocomentario = itemView.findViewById(R.id.tvusuariocomentario) as TextView


            tvfechacomentario.text = dataItem.fecha
            tvtextocomentario.text = dataItem.comentario
            tvusuariocomentario.text = dataItem.nick

            itemView.tag = dataItem

        }

    }
}