package com.felixaraque.android_retrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.model.Zona

class AdapterZonas(val context: Context,
                   val layout: Int
                    ) : RecyclerView.Adapter<AdapterZonas.ViewHolder>() {

    private var dataList: List<Zona> = emptyList()

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

    internal fun setZonas(zonas: List<Zona>) {
        this.dataList = zonas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Zona){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvnombrezona = itemView.findViewById(R.id.tvnombrezona) as TextView
            val tvlocalizacionzona = itemView.findViewById(R.id.tvlocalizacionzona) as TextView


            tvnombrezona.text = dataItem.nombre
            tvlocalizacionzona.text = dataItem.localizacion

            itemView.tag = dataItem

        }

    }
}