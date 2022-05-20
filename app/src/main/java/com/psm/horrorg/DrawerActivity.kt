package com.psm.horrorg

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.psm.horrorg.Fragments.HomeFragment
import com.psm.horrorg.Fragments.ProfileUser
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*

class DrawerActivity : AppCompatActivity() {

    private lateinit var nombreUsuario: String
    private lateinit var picdrawer: ImageView

    fun cambiarFragmento(fragmentoNuevo: Fragment, tag: String){

        val fragmentoAnterior = supportFragmentManager.findFragmentByTag(tag)
        if(fragmentoAnterior == null){

            supportFragmentManager.beginTransaction().replace(R.id.contenedor, fragmentoNuevo).commit()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)
        val extras = intent.extras

        val miNav = findViewById<NavigationView>(R.id.nav_drawer_header)
        val miDrawer = findViewById<DrawerLayout>(R.id.drawer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)



        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, miDrawer, toolbar, R.string.app_name, R.string.app_name)
        miDrawer.addDrawerListener(toggle)
        toggle.syncState()


        val header = miNav.getHeaderView(0)
        picdrawer = header.findViewById(R.id.iv_drawer_perfil)
        val namedrawer = header.findViewById<TextView>(R.id.tv_drawer_name)

        namedrawer.text = Usuario.getUsername()
        picdrawer.setImageBitmap(Usuario.getImage())



        //nombreUsuario = intent.getStringExtra("username") ?: "sin_nombre"

        cambiarFragmento(HomeFragment(), "HomeFragment")

        miNav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.opc_Perfil -> {
                    cambiarFragmento(ProfileUser(), "ProfileUser")
                }

                R.id.opc_mislibros -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    //cambiarFragmento(HomeFragment(), "HomeFragment")
                }

                else -> {
                    Toast.makeText(this, "Seleccionaste algo", Toast.LENGTH_SHORT).show()
                }

            }

            miDrawer.closeDrawer(GravityCompat.START)

            true
        }

    }
}