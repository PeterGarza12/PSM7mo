package com.psm.horrorg

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
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
import com.psm.horrorg.Model.Chapters
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.chapter
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.activity_sinopsis_book.*
import java.io.File
import java.io.FileOutputStream

class ReadActivity: AppCompatActivity() {


    private val STORAGE_CODE: Int = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        val tvTitle = findViewById<TextView>(R.id.textView3)
        val tvBody = findViewById<TextView>(R.id.textView14)
        val tvImage = findViewById<ImageView>(R.id.imageView8)

        tvTitle.text = chapter.getTitle()
        tvBody.text = chapter.getBody()
        tvImage.setImageBitmap(chapter.getimgArray())

        txt_chapter_pdf.setOnClickListener(View.OnClickListener {


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
        var titulo = TextPaint()
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
        canvas.drawText(chapter.getTitle(),10f,150f,titulo)
        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        descripcion.textSize=14f

        var arrayDescripcion = chapter.getBody().split(",")
        var y = 200f // la alttura por la que deseamos [intar para no sobreescribir nuestro texto

        for(item in arrayDescripcion){
            canvas.drawText(item,10f,y,descripcion)
            y+= 15
        }
        pdfDocument.finishPage(pagina1)
        val holi = Environment.getExternalStorageState()
        Toast.makeText(this,holi, Toast.LENGTH_LONG).show()

        val file = File(Environment.getExternalStorageDirectory().toString(), Libro.getTitle() + " Capítulo: "+ chapter.getTitle()+".pdf")

        try{
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this,"Se descargo el pdf", Toast.LENGTH_LONG).show()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        pdfDocument.close()

    }

}