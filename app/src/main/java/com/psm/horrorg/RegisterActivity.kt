package com.psm.horrorg

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.text.set
import androidx.core.util.PatternsCompat
import com.psm.horrorg.DatePicker.DatePickerFragment
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.Model.User2
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(),View.OnClickListener {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var bytes: ByteArrayOutputStream
    private lateinit var byteImage: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //va a nuestra funcion de mostrar el calendario
        editTextDate.setOnClickListener{showDatePickerDialog()}


        //Se encuentra donde se pondrá la imagen
        imageView = findViewById(R.id.iv_register_profilepic)
        //Se agarra el image button y se le pone un listener que abrirá la galería
        val imageButton = findViewById<ImageView>(R.id.ib_profilepic)
        imageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        btn_Login.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Se selecciona la imgen y se inserta en el imageview
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun showDatePickerDialog() {
        // Creamos un  objeto
        // Hay que mencionar las variables que queremos utilizar para que las agarre
        val datePicker= DatePickerFragment{day, month, year-> onDateSelected(day, month+1, year)}
        datePicker.show(supportFragmentManager,"datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int){
        editTextDate.setText("$day/$month/$year")

    }

    override fun onClick(v: View?) {

        if(v!!.id== R.id.btn_Login){
           showInfo()
        }
    }

    private fun showInfo(){

        val passwordRegex =Pattern.compile(
            "^"+"(?=.*[0-9])" +         //al menos un digito
                    "(?=.*[a-z])" +        //al menos una minuscula
                    "(?=.*[A-Z])" +        //al menos una mayuscula
                    "(?=\\S+$)" +           //sin espacios
                    ".{8,}" +               //al menos 4 caracteres
                    "$"
        )

        val nameRegex = Pattern.compile(
                "^" +               //Obliga que haya coincidencia al inicio de la cadena
                    "([A-ZÀ-ÿ]" +         //Que puede aceptar solo letras en mayúsculas o especiales como acentos
                    "[-,a-z. ']" +        //Continuar con minúsculas
                    "+[ ]*)+" +           //Puede aceptar espacios para separar los nombres
                    "$"                   //Obliga qeu así termine la cadena para que no se cuele algún numero o caracter especial
        )

        //Función para validar que ya haya una imagen seleccionada
        if(!createImage())
        {
            Toast.makeText(this,"Favor de seleccionar una imagen", Toast.LENGTH_LONG).show()
        }
        else if(this.input_username.text.toString() =="" || this.input_password.text.toString()=="" || this.input_password2.text.toString()==""
            || this.input_password2.text.toString()=="" || this.editTextDate.text.toString()=="" || this.input_nombrecompleto.text.toString() == ""){

            Toast.makeText(this,"Favor de llenar los campos",Toast.LENGTH_LONG).show();

        }
        else if(this.input_password.text.toString()!=this.input_password2.text.toString()){

            Toast.makeText(this,"Las contraseñas tienen que ser iguales",Toast.LENGTH_LONG).show();


        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(this.input_correo.text.toString()).matches()){

            Toast.makeText(this,"El Formato del correo electronico es invalido",Toast.LENGTH_LONG).show();

        }
        else if (!passwordRegex.matcher(this.input_password.text.toString()).matches()){

            Toast.makeText(this,"El formato de la contraseña es invalido",Toast.LENGTH_LONG).show();
        }
        else if (!nameRegex.matcher(this.input_nombrecompleto.text.toString()).matches()){

            Toast.makeText(this,"Favor de verificar que esté bien escrito su nombre",Toast.LENGTH_LONG).show();
        }
        else{

            var inserted:Long = 0

            var Dbusers = dbUsers(this@RegisterActivity)

            var unique:Boolean = Dbusers.validarCorreoUnico(this.input_correo.text.toString())

            if(unique){

                inserted = Dbusers.insertarUsuario( this.input_username.text.toString(),
                                                    this.input_password.text.toString(),
                                                    this.editTextDate.text.toString(),
                                                    byteImage,
                                                    this.input_nombrecompleto.text.toString(),
                                                    this.input_correo.text.toString())
                if(inserted>0){

                    //if(registerUser()){ TODO: Mandar también a la api, o mejor al revés, primero validar con la api y luego mandamos a la local

                    Toast.makeText(this,"Usuario creado",Toast.LENGTH_LONG).show();
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)

                    //val intent=Intent(this,DrawerActivity::class.java)
                    //intent.putExtra("user",this.input_username.text.toString())

                }
                else{

                    Toast.makeText(this,"Error al crear el usuario",Toast.LENGTH_LONG).show();

                }
            }
            else{

                Toast.makeText(this,"Ya existe un usario con ese correo",Toast.LENGTH_LONG).show();

            }
        }
    }

    private fun createImage(): Boolean{

        var result = false

        //Si ya hay una imagen seleccionada entra al if
        if(imageUri!=null){

            //Se hacen las conversiones necesarias para pasarla de image uri a un array de bytes para guardarla como blob en la db
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes)

            byteImage = bytes.toByteArray()

            result = true

        }

        return result
    }

    private fun registerUser(): Boolean{

        var success = false

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        val user =  User2(0,
                    this.input_username.text.toString(),
                    this.input_password.text.toString(),
                    this.editTextDate.text.toString(),
                    null
                    )

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.saveUser(user)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"Error",Toast.LENGTH_LONG).show()
                success = true
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@RegisterActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })

        return success
    }


}