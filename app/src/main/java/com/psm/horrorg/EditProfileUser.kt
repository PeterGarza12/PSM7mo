package com.psm.horrorg

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.psm.horrorg.DatePicker.DatePickerEdit
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.User2

import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.profile_user_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern

class EditProfileUser: AppCompatActivity(),View.OnClickListener {

    private lateinit var user: User

    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var ivProfile: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var bytes: ByteArrayOutputStream
    private var byteImage: ByteArray ? = null


    var sUser:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user_edit)



        val txtUsername         = findViewById<TextView>(R.id.txt_edit_username)
        val txtDate             = findViewById<TextView>(R.id.dt_edit_picker)
        val txtPassword         = findViewById<EditText>(R.id.et_edit_pass)
        val txtConfirmPassword  = findViewById<EditText>(R.id.et_edit_passconfirm)
        val txtName             = findViewById<TextView>(R.id.et_edit_name)
        val etUsername          = findViewById<TextView>(R.id.et_edit_username)
        ivProfile               = findViewById(R.id.iv_edit_profile)


        val btnUpdate = findViewById<Button>(R.id.btn_edit_update)

        txtUsername.text = Usuario.getUsername()
        txtDate.text    = Usuario.getDateBirth()
        txtPassword.setText(Usuario.getPassword())
        txtConfirmPassword.setText(Usuario.getPassword())
        txtName.text    = Usuario.getName()
        etUsername.text = Usuario.getUsername()
        ivProfile.setImageBitmap(Usuario.getImage())

        dt_edit_picker.setOnClickListener{showDatePickerDialog()}

        btn_edit_cancel.setOnClickListener {
            val intent=Intent(this,DrawerActivity::class.java)
            startActivity(intent)

        }

        val imageButton = findViewById<ImageView>(R.id.ib_edit_photo)
        imageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        btnUpdate.setOnClickListener(this)
    }
    private fun showDatePickerDialog() {
        // Creamos un  objeto
        // Hay que mencionar las variables que queremos utilizar para que las agarre
        val datePicker= DatePickerEdit{ day, month, year-> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager,"datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int){
        dt_edit_picker.setText("$day/$month/$year")

    }

    override fun onClick(v: View?) {
        if(v!!.id== R.id.btn_edit_update){
            updatingUser()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Se selecciona la imgen y se inserta en el imageview
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            ivProfile.setImageURI(imageUri)
        }
    }


    private fun updatingUser(){

        val passwordRegex = Pattern.compile(
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
/*
* val txtUsername         = findViewById<TextView>(R.id.txt_edit_username)
        val txtDate             = findViewById<TextView>(R.id.dt_edit_picker)
        val txtPassword         = findViewById<EditText>(R.id.et_edit_pass)
        val txtConfirmPassword  = findViewById<EditText>(R.id.et_edit_passconfirm)
        val txtEmail            = findViewById<TextView>(R.id.et_edit_email)
        val txtName             = findViewById<TextView>(R.id.et_edit_name)
        val etUsername          = findViewById<TextView>(R.id.et_edit_username)
        ivProfile               = findViewById(R.id.iv_edit_profile)
* */
        //Función para validar que ya haya una imagen seleccionada

        val isimage = createImage()

        if(this.et_edit_pass.text.toString()=="" || this.et_edit_passconfirm.text.toString()==""
            || this.dt_edit_picker.text.toString()=="" || this.et_edit_name.text.toString() == ""){

            Toast.makeText(this,"Favor de llenar los campos",Toast.LENGTH_LONG).show();

        }
        else if(this.et_edit_pass.text.toString()!=this.et_edit_passconfirm.text.toString()){

            Toast.makeText(this,"Las contraseñas tienen que ser iguales",Toast.LENGTH_LONG).show();


        }
        else if (!passwordRegex.matcher(this.et_edit_pass.text.toString()).matches()){

            Toast.makeText(this,"El formato de la contraseña es invalido",Toast.LENGTH_LONG).show();
        }
        else if (!nameRegex.matcher(this.et_edit_name.text.toString()).matches()){

            Toast.makeText(this,"Favor de verificar que esté bien escrito su nombre",Toast.LENGTH_LONG).show();
        }
        else{

            var inserted = false

            var Dbusers = dbUsers(this@EditProfileUser)

            //updateUser()
            inserted = Dbusers.actualizarUsuario(this.et_edit_username.text.toString(),
                                                this.et_edit_pass.text.toString(),
                                                this.dt_edit_picker.text.toString(),
                                                byteImage,
                                                this.et_edit_name.text.toString(),
                                                Usuario.getId(),
                                                isimage
                                                )
            if(inserted){

                //if(registerUser()){

                    //var iduser = Usuario.getId()

                user = Dbusers.verUser(Usuario.getEmail())

                if(user!=null){

                    Usuario.setUsuario(Usuario.getId(), user.USERNAME, user.PASS, user.BIRTHDAY,user.IMAGE, user.NAME, user.EMAIL)

                }

                Toast.makeText(this,"Usuario actualizado",Toast.LENGTH_LONG).show();

                val intent=Intent(this,DrawerActivity::class.java)
                startActivity(intent)

                //val intent=Intent(this,DrawerActivity::class.java)
                //intent.putExtra("user",this.input_username.text.toString())

            }
            else{

                Toast.makeText(this,"Error al crear el usuario",Toast.LENGTH_LONG).show();

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
        else{

            result = false
        }

        return result
    }

    private fun updateUser(){

        val encodedString:String =  Base64.getEncoder().encodeToString(byteImage)
        val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        val user =  User2(Usuario.getId(),
                    this.et_edit_username.text.toString(),
                    this.et_edit_pass.text.toString(),
                    this.dt_edit_picker.text.toString(),
                    strEncodeImage,
                    this.et_edit_name.text.toString(),
                    Usuario.getEmail()
                    )

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.saveUser(user)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {

                Toast.makeText(this@EditProfileUser,"Error",Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {

                Toast.makeText(this@EditProfileUser,"OK",Toast.LENGTH_LONG).show()

            }

        })

    }

}


