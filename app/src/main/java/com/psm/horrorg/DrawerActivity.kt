package com.psm.horrorg

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_main.*

class DrawerActivity : AppCompatActivity() {

    private lateinit var nombreUsuario: String

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

        val miNav = findViewById<NavigationView>(R.id.nav)
        val miDrawer = findViewById<DrawerLayout>(R.id.drawer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, miDrawer, toolbar, R.string.app_name, R.string.app_name)
        miDrawer.addDrawerListener(toggle)
        toggle.syncState()


        //nombreUsuario = intent.getStringExtra("username") ?: "sin_nombre"



        miNav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.opc_Perfil -> {
                    cambiarFragmento(ProfileUser(), "ProfileUser")
                }

                R.id.opc_mislibros -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
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