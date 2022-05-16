package com.psm.horrorg

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Db.dbChapters
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R
import kotlinx.android.synthetic.main.activity_addchapter.*
import kotlinx.android.synthetic.main.activity_createbook.*
import kotlinx.android.synthetic.main.activity_sinopsis_book.*
import java.io.ByteArrayOutputStream

class CreateChapterActivity: AppCompatActivity(), View.OnClickListener {

    var imgid: Int = 0

    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var imageView: ImageView
    lateinit var bitmap: Bitmap
    lateinit var bytes: ByteArrayOutputStream
    lateinit var byteImage: ByteArray

    var dbchapter = dbChapters(this@CreateChapterActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addchapter)

        btn_addchapter_upload.setOnClickListener(this)

        imageView = findViewById(R.id.iv_add_chapter)
        val imageButton = findViewById<ImageView>(R.id.btn_addchapter_photo)
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
        if(v!!.id == R.id.btn_addchapter_upload){
            insertChapter(v)
        }
    }

    private fun insertChapter(v: View){
        var canInsert = true
        if(this.et_addchapter_title.text.toString() == "" || this.et_addchapter_body.text.toString()==""){
            Toast.makeText(this,"Favor de llenar los campos", Toast.LENGTH_LONG).show()
            canInsert = false
        }

        var isImage = createImage()
        if(!isImage){
            canInsert = false;
            Toast.makeText(this,"Favor de seleccionar una imagen", Toast.LENGTH_LONG).show()
        }

        var idlibro = Libro.getId()

        if(canInsert){
            var id:Long = dbchapter.insertChapter(this.et_addchapter_title.text.toString(), this.et_addchapter_body.text.toString(), byteImage, Libro.getId())
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

    private fun createImage(): Boolean{

        var result = false

        if(imageUri!=null){
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes)

            byteImage = bytes.toByteArray()

            result = true

        }

        return result
    }




}