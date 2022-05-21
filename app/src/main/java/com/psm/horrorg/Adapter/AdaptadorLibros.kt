package com.psm.horrorg.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.Libro.getTitle
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.chapter.getTitle
import com.psm.horrorg.R
import com.psm.horrorg.SinopsisBookActivity
import java.util.stream.Stream

class AdaptadorLibros(private var listaLibros: MutableList<Libros>, val context: Context):
    RecyclerView.Adapter<AdaptadorLibros.GroupViewholder>(), Filterable {

    var dbImg = dbImages(this.context)

    private val fullLibros = ArrayList<Libros>(listaLibros)

    inner class GroupViewholder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var libroPosition = 0
        fun asignarInformacion(libros: Libros) {
            var ivBook = itemView.findViewById<ImageView>(R.id.iv_libro)
            val tvBookName = itemView.findViewById<TextView>(R.id.tv_nombre_libro)
            val tvCategoryBook = itemView.findViewById<TextView>(R.id.tv_categoria_libro)
            val tvDescriptionBook = itemView.findViewById<TextView>(R.id.tv_descripcion_libro)
            val contenedorLibros = itemView.findViewById<LinearLayout>(R.id.contenedor_libros)



            ivBook.setImageBitmap(libros.imgArray)
            tvBookName.text = libros.strTitle
            tvCategoryBook.text = libros.genre
            tvDescriptionBook.text = libros.strDescription
            val params = contenedorLibros.layoutParams
            val newParams = FrameLayout.LayoutParams(
                params.width,
                params.height
            )

            contenedorLibros.layoutParams = newParams

        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.framegroup -> {
                    //TODO("Lanzar el activity del libro")
                    Libro.setLibro(
                        listaLibros[libroPosition].libroId,
                        listaLibros[libroPosition].userId,
                        listaLibros[libroPosition].strTitle.toString(),
                        listaLibros[libroPosition].strDescription.toString(),
                        listaLibros[libroPosition].intIdImage,
                        listaLibros[libroPosition].genre.toString(),
                        listaLibros[libroPosition].imgArray
                    )
                    //val gson = Gson()
                    Toast.makeText(context, Libro.getDescription(), Toast.LENGTH_LONG).show();
                    val activityIntent = Intent(context, SinopsisBookActivity::class.java)
                    //activityIntent.putExtra("Libro", gson.toJson(listaLibros[libroPosition]))
                    context.startActivity(activityIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewholder {
        val miView = LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false)
        return GroupViewholder(miView)
    }

    override fun onBindViewHolder(holder: GroupViewholder, position: Int) {
        holder.libroPosition = position
        holder.asignarInformacion(listaLibros[position])

    }

    override fun getItemCount(): Int = listaLibros.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                //ejecuta un hilo para mostrar los datos
                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values = if (charSequence == null || charSequence.isEmpty()) {

                    fullLibros
                } else {
                    val queryString = charSequence?.toString()?.toLowerCase()



                    listaLibros.filter { listaLibros ->
                        listaLibros.strTitle!!.toLowerCase()
                            .contains(queryString.toString()) || listaLibros.strDescription!!.toLowerCase()
                            .contains(queryString.toString()) || listaLibros.libroId!!.toString().toLowerCase()
                            .contains(queryString.toString())
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                //Muestra los cambios del filtrado
                listaLibros = (results?.values as List<Libros>).toMutableList()

                notifyDataSetChanged() //se actualiza y dibuja los elementos filtrados
            }


        }
    }
}
