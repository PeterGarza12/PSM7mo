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
import com.psm.horrorg.Db.dbChapters
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_createbook.*
import java.io.ByteArrayOutputStream


class BookActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var spinnerSelected: String ="Suspenso"

    var spinner: Spinner? = null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton? = null
    var imgid: Int = 0

    var DBBooks = dbBooks(this@BookActivity)
    var dbimg = dbImages(this@BookActivity)

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
        spinner?.onItemSelectedListener = this

        radioGroup = findViewById(R.id.rg_create_question)

        imageView = findViewById(R.id.iv_create_image)
        val imageButton = findViewById<ImageView>(R.id.btnOpenPhotoReel)
        imageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }




    }

    //Función que pone la imagen en el imageview
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    override fun onClick(v: View){
        if(v!!.id == R.id.btn_create_upload){
            createBook(v)
        }
    }

    private fun createBook(v: View){
        var canInsert = true
        if(this.et_create_title.text.toString()=="" || this.et_create_sinopsis.text.toString()==""){
            Toast.makeText(this,"Favor de llenar los campos", Toast.LENGTH_LONG).show()
            canInsert = false
        }



        val selectedId: Int = radioGroup!!.checkedRadioButtonId

        if(selectedId!=-1){
            radioButton = findViewById<View>(selectedId) as RadioButton
            Toast.makeText(this, radioButton!!.text.toString(), Toast.LENGTH_LONG).show()
        }

        var idGenre = 1
        var categoria = ""

        when(spinnerSelected){
            "Suspenso" -> { idGenre = 1
                            categoria = "suspenso"
                            }
            "Criminología" -> { idGenre = 2
                                categoria = "Criminología"
                                }
            "Creepypastas" -> { idGenre = 3
                                categoria = "Creepypastas"
                                }
            else -> {
                idGenre = 4
            }
        }

        imgid = idDeLaImagen()

        if(imgid==0){
            canInsert = false;
            Toast.makeText(this,"Favor de seleccionar una imagen", Toast.LENGTH_LONG).show()
        }

        if(canInsert && radioButton!=null){
            var id:Long = DBBooks.insertBook(Usuario.getId(), this.et_create_title.text.toString(), this.et_create_sinopsis.text.toString(), imgid, idGenre, categoria, "Prueba")
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

    private fun idDeLaImagen(): Int{
        var id = 0

        if(imageUri!=null){
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes)

            byteImage = bytes.toByteArray()

            id = dbimg.insertImage(byteImage)

        }

        return id
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

