package com.psm.horrorg.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.psm.horrorg.BookActivity
import com.psm.horrorg.BookRecycleAdapter
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Data.DEFAULT_ALBUM_POSITION
import com.psm.horrorg.Data.DataManager
import com.psm.horrorg.R
import kotlinx.android.synthetic.main.content_list.*

class HomeFragment : Fragment(R.layout.activity_home), SearchView.OnQueryTextListener {
    private var albumAdapter: BookRecycleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.activity_home, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(activity, BookActivity::class.java)
            intent.putExtra(ALBUM_POSITION, DEFAULT_ALBUM_POSITION)
            startActivity(intent)
        }

        val appContext = requireContext().applicationContext

        rcListAlbum.layoutManager = LinearLayoutManager(activity)
        this.albumAdapter = BookRecycleAdapter(appContext, DataManager.albums)
        rcListAlbum.adapter = this.albumAdapter



        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            if(albumAdapter != null) this.albumAdapter?.filter?.filter(newText)//evaluas con el filtrado del adapter
        }
        return false
    }
}