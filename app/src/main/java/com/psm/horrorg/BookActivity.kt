package com.psm.horrorg

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Db.dbBooks
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_createbook.*
import java.io.ByteArrayOutputStream


class BookActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var spinnerSelected: String ="Suspenso"

    var spinner: Spinner? = null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton? = null
    var DBBooks = dbBooks(this@BookActivity)

    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var imageView: ImageView
    lateinit var bitmap: Bitmap
    lateinit var bytes: ByteArrayOutputStream
    lateinit var byteImage: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createbook)


        btn_create_upload.setOnClickListener(this)


        spinner = findViewById(R.id.sp_create_category)
        radioGroup = findViewById(R.id.rg_create_question)

        imageView = findViewById(R.id.iv_create_image)
        val imageButton = findViewById<ImageView>(R.id.btnOpenPhotoReel)
        imageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)

            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes)

            byteImage = bytes.toByteArray()

            Toast.makeText(this, radioButton!!.text.toString(), Toast.LENGTH_LONG).show()


        }
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

        spinner?.onItemSelectedListener = this

        val selectedId: Int = radioGroup!!.checkedRadioButtonId

        if(selectedId!=-1){
            radioButton = findViewById<View>(selectedId) as RadioButton
            Toast.makeText(this, radioButton!!.text.toString(), Toast.LENGTH_LONG).show()
        }

        var idGenre = 1;

        when(spinnerSelected){
            "Suspenso" ->  idGenre = 1
            "Criminología" -> idGenre = 2
            "Creepypastas" -> idGenre = 3
            else -> {
                idGenre = 4
            }
        }

        if(canInsert && radioButton!=null){
            var id:Long = DBBooks.insertBook(Usuario.getId(), this.et_create_title.text.toString(), this.et_create_sinopsis.text.toString(), 1, idGenre, "Hola", "Prueba")
            if(id>0){
                Toast.makeText(this, "Libro creado con éxito", Toast.LENGTH_LONG).show()
                val intent = Intent(this,DrawerActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            Toast.makeText(this,"Favor de llenar los campos", Toast.LENGTH_LONG).show()
        }

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

