package com.psm.horrorg.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Model.Chapters
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.chapter
import com.psm.horrorg.R
import com.psm.horrorg.ReadActivity
import com.psm.horrorg.SinopsisBookActivity


class AdaptadorCapitulos(private val listaCapitulos: MutableList<Chapters>, val context: Context):
    RecyclerView.Adapter<AdaptadorCapitulos.GroupViewholder>(){
    inner class GroupViewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var capituloPosition = 0
        fun asignarInformacion(chapters: Chapters){
            var ivBook = itemView.findViewById<ImageView>(R.id.iv_capitulo)
            val tvCapituloName = itemView.findViewById<TextView>(R.id.tv_nombre_capitulo)

            val contenedorCapitulos = itemView.findViewById<LinearLayout>(R.id.contenedor_Capitulos)

            ivBook.setImageBitmap(chapters.imgArray)
            tvCapituloName.text = chapters.strTitle

            val params = contenedorCapitulos.layoutParams

            val newParams = FrameLayout.LayoutParams(
                params.width,
                params.height
            )

            contenedorCapitulos.layoutParams = newParams


        }
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.frame->{
                    chapter.setCapitulo(listaCapitulos[capituloPosition].id, listaCapitulos[capituloPosition].bookId, listaCapitulos[capituloPosition].strTitle,
                        listaCapitulos[capituloPosition].strBody, listaCapitulos[capituloPosition].imgArray)

                    Toast.makeText(context, Libro.getDescription(), Toast.LENGTH_LONG).show();
                    val activityIntent = Intent(context, ReadActivity::class.java)
                    context.startActivity(activityIntent)
                }
            }
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