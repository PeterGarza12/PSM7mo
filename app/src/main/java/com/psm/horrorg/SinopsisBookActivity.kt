package com.psm.horrorg

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_sinopsis_book.*
import java.io.File
import java.io.FileOutputStream


class SinopsisBookActivity : AppCompatActivity() {

    lateinit var background: ImageView
    private val STORAGE_CODE: Int = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sinopsis_book)


        //val gson = Gson()
        //val miLibro = gson.fromJson(intent.getStringExtra("Libro"), Libros::class.java)


        val tvTitle = findViewById<TextView>(R.id.txt_titulo)
        val tvCategory = findViewById<TextView>(R.id.txt_Categoria)
        val tvDescription = findViewById<TextView>(R.id.txt_Descripcion)
        background = findViewById(R.id.iv_sinopsis_background)

        /*tvTitle.text = miLibro.strTitle
        tvCategory.text = miLibro.genre.toString()
        tvDescription.text = miLibro.strDescription
        background.setImageBitmap(miLibro.imgArray)*/

        tvTitle.text = Libro.getTitle()
        tvCategory.text = Libro.getGenre()
        tvDescription.text = Libro.getDescription()
        background.setImageBitmap(Libro.getimgArray())
        background.tag = Libro.getimgArray().toString()


        val btnRead = findViewById<Button>(R.id.btn_Read)
        val btnCreate = findViewById<Button>(R.id.btn_add_chapter)

        //if(miLibro.userId == Usuario.getId()){
        if(Libro.getUserId() == Usuario.getId()){

            btnCreate.visibility = View.VISIBLE
        }
        else{
            btnCreate.visibility = View.GONE
        }

        if(sheckPermission()){
            Toast.makeText(this,"Permiso Aceptado", Toast.LENGTH_LONG)
        }else{
            requestPermissions()
        }

        btnRead.setOnClickListener {
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
        }
        btnCreate.setOnClickListener{
            val intent = Intent(this, CreateChapterActivity::class.java)
            startActivity(intent)
        }
        txt_Download.setOnClickListener(View.OnClickListener {

           
            //we need to handle runtime permission for devices with marshmallow and above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                //system OS >= Marshmallow(6.0), check permission is enabled or not
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not granted, request it
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    //permission already granted, call savePdf() method
                    generarPdf()
                }
            }
            else{
                //system OS < marshmallow, call savePdf() method
                generarPdf()
            }
            
            
           
        })
    }
    fun generarPdf(){
        //inicializas la variable para el pdf
        var pdfDocument = PdfDocument()
        // inicializas la variable para dibujar la informacion en el pdf
        var paint = Paint()
        var titulo =TextPaint()
        var categoria= TextPaint()
        var descripcion= TextPaint()

        //configuraciones , caracteristicas que queremos dentro del builder
        var  paginaInfo = PdfDocument.PageInfo.Builder(815,1054,1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)
        var canvas = pagina1.canvas

        //val drawableId: Int = background.tag.toString().toInt()


        //var bitmap= BitmapFactory.decodeResource(resources,drawableId)
        //var bitmapEscala= Bitmap.createScaledBitmap(bitmap,80,80,false)
        //canvas.drawBitmap(bitmapEscala,368f,20f,paint)

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)) //titulo en negritas
        titulo.textSize=20f
        canvas.drawText(Libro.getTitle(),10f,150f,titulo)
        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        descripcion.textSize=14f

        var arrayDescripcion = Libro.getDescription().split(".")
        var y = 200f // la alttura por la que deseamos [intar para no sobreescribir nuestro texto

        for(item in arrayDescripcion){
            canvas.drawText(item,10f,150f,descripcion)
            y+= 15
        }
        pdfDocument.finishPage(pagina1)
        val holi = Environment.getExternalStorageState()
        Toast.makeText(this,holi, Toast.LENGTH_LONG).show()

        val file =File(Environment.getExternalStorageDirectory().toString(),"Archivo.pdf")

        try{
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this,"Se descargo el pdf", Toast.LENGTH_LONG).show()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        pdfDocument.close()

    }
    private fun  sheckPermission():Boolean{
        val permission1 = ContextCompat.checkSelfPermission(applicationContext,WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(applicationContext,READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE ),200)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==200){
            if(grantResults.size>0){
                val writeStorage = grantResults[0]== PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1]== PackageManager.PERMISSION_GRANTED

                if(writeStorage && readStorage){
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG)
                }else{
                    Toast.makeText(this, "Permiso rechazado", Toast.LENGTH_LONG)
                    finish()
                }

            }
        }

    }
}