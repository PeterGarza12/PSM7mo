package com.psm.horrorg

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Db.dbBooks
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_createbook.*

class BookActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var spinnerSelected: String ="Suspenso"

    var spinner: Spinner? = null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createbook)


        btn_create_upload.setOnClickListener(this)


        spinner = findViewById(R.id.sp_create_category)
        radioGroup = findViewById(R.id.rg_create_question)

        // get selected radio button from radioGroup
        // get selected radio button from radioGroup



    }

    override fun onClick(v: View){
        if(v!!.id == R.id.btn_create_upload){
            insertBook(v)
        }
    }

    private fun insertBook(v: View){
        var canInsert = true
        if(this.et_create_title.text.toString()=="" || this.et_create_sinopsis.text.toString()==""){
            Toast.makeText(this,"Favor de llenar los campos", Toast.LENGTH_LONG).show()
            canInsert = false
        }
        var isRadio: Boolean = checkRadioButton(v)

        var DBBooks = dbBooks(this@BookActivity)

        spinner?.onItemSelectedListener = this

        val selectedId: Int = radioGroup!!.checkedRadioButtonId

        // find the radiobutton by returned id

        // find the radiobutton by returned id
        if(selectedId!=-1){
            radioButton = findViewById<View>(selectedId) as RadioButton
            Toast.makeText(this, radioButton!!.text.toString(), Toast.LENGTH_LONG).show()
        }


        if(canInsert){
            var id:Long = DBBooks.insertBook(Usuario.getId(), this.et_create_title.text.toString(), this.et_create_sinopsis.text.toString(), 1, 1, "Hola", "Prueba")
        }
        else{
            Toast.makeText(this,"Favor de llenar los campos", Toast.LENGTH_LONG).show()
        }





    }

    private fun checkRadioButton(view: View): Boolean {
        var flag = false
        if(view is RadioButton){
            val checked = view.isChecked
            when (view.getId()){
                R.id.rb_create_yes ->
                    if(checked){
                        flag = true
                    }
                R.id.rb_create_no ->
                    if(checked){
                        flag = false
                    }
            }
        }
        return flag
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            spinnerSelected = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

