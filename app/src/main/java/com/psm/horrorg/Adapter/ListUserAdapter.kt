package com.psm.horrorg.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.psm.horrorg.Model.User

class ListUserAdapter(internal var activity: Activity,
                      internal var lstPerson:List<User>,
                      internal var edt_id:EditText,
                      internal var edt_name:EditText,
                      internal var edt_password:EditText,
                      internal var edt_date:EditText):BaseAdapter() {

    internal var inflater:LayoutInflater

    init{
        inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
    /*override fun getCount(): Int {
       return  lstPerson.size
    }

    override fun getItem(position: Int): Any {
        return lstPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return  lstPerson[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        //TODO
      return null
    }*/
}