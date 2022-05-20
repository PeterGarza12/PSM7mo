package com.psm.horrorg.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Model.Chapters
import com.psm.horrorg.R


class AdaptadorCapitulos(private val listaCapitulos: MutableList<Chapters>, val context: Context):
    RecyclerView.Adapter<AdaptadorCapitulos.GroupViewholder>(){
    inner class GroupViewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var capituloPosition = 0
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorCapitulos.GroupViewholder {
        val miView = LayoutInflater.from(parent.context).inflate(R.layout.item_charapter, parent, false)
        return GroupViewholder(miView)
    }

    override fun onBindViewHolder(holder: AdaptadorCapitulos.GroupViewholder, position: Int) {
        holder.capituloPosition = position
        holder.asignarInformacion(listaCapitulos[position])
    }

    override fun getItemCount(): Int = listaCapitulos.size

}