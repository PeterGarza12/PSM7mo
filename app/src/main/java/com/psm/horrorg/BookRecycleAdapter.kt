package com.psm.horrorg

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Data.Album
import com.psm.horrorg.Utilities.ImageUtilities

class BookRecycleAdapter (val context: Context, var albums:List<Album>): RecyclerView.Adapter<BookRecycleAdapter.ViewHolder>(),
    Filterable {

    private val layoutInflater = LayoutInflater.from(context)
    private val fullAlbums = ArrayList<Album>(albums)

    //se hace cargo de los graficos
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val txtTitle = itemView?.findViewById<TextView>(R.id.txtTitle)
        val txtDescription = itemView?.findViewById<EditText>(R.id.txtDescription)
        val imgAlbumCard = itemView?.findViewById<ImageView>(R.id.imgBookCard)
        var albumPosition: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            when (v!!.id) {
                R.id.idFrameLayoutCard -> {
                    //Lanzamos el intent para abrir el detalle
                    val activityIntent = Intent(context, SinopsisBookActivity::class.java)
                    activityIntent.putExtra(ALBUM_POSITION, this.albumPosition)
                    context.startActivity(activityIntent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //que vista le daremos al new holfer para que la llene
        val itemView = this.layoutInflater.inflate(R.layout.item_book_list, parent, false)
        return ViewHolder(itemView)
    }

    //cuantos elementos tenemos para dibujar
    override fun getItemCount(): Int = this.albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //llenar el view que vamos a utilizar

        val album = this.albums[position]
        holder.txtTitle?.text ?: album.strTitle

        holder.txtDescription?.setText(album.strDescription)
        holder.albumPosition = position
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))

        if (album.imgArray == null) {
            holder.imgAlbumCard?.setImageResource(album.intIdImage!!)
        } else {
            holder.imgAlbumCard?.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                //ejecuta un hilo para mostrar los datos

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values = if (charSequence == null || charSequence.isEmpty()) {

                    fullAlbums

                } else {
                    val queryString = charSequence?.toString()?.toLowerCase()



                    albums.filter { album ->

                        album.strTitle!!.toLowerCase()
                            .contains(queryString.toString()) || album.strDescription!!.toLowerCase()
                            .contains(queryString.toString())
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                //Muestra los cambios del filtrado

                albums = results?.values as List<Album>
                notifyDataSetChanged() //se actualiza y dibuja los elementos filtrados
            }

        }
    }
}