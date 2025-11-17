package com.odinsystem.tiendavirtual.Vendedor

import android.R.attr.id
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.odinsystem.tiendavirtual.R
import com.odinsystem.tiendavirtual.SelecionarTipoActivity
import com.odinsystem.tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.odinsystem.tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.odinsystem.tiendavirtual.Vendedor.nav_Fragments_Vendedor.FragmentCategoriaV
import com.odinsystem.tiendavirtual.Vendedor.nav_Fragments_Vendedor.FragmentInicioV
import com.odinsystem.tiendavirtual.Vendedor.nav_Fragments_Vendedor.FragmentMiTiendaV
import com.odinsystem.tiendavirtual.Vendedor.nav_Fragments_Vendedor.FragmentReseniaV
import com.odinsystem.tiendavirtual.databinding.ActivityMainVendedorBinding

class MainActivityVendedor : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainVendedorBinding

    private var firebaseAuth: FirebaseAuth? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)



        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigatorView.setNavigationItemSelectedListener(this)


        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer

        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioV())
        binding.navigatorView.setCheckedItem(R.id.op_inicio_v)

    }

    private fun cerrarSession(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, SelecionarTipoActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "Has cerrada sesion", Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
//        si el ususario nbo ha iniciado session
        if (firebaseAuth!!.currentUser == null) {
            startActivity(Intent(applicationContext, SelecionarTipoActivity::class.java))
            Toast.makeText(applicationContext, "Vendedor no registrado o no loggeado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_inicio_v -> {
                replaceFragment(FragmentInicioV())
            }

            R.id.op_mi_tienda_v -> {
                replaceFragment(FragmentMiTiendaV())
            }

            R.id.op_categorias_v->{
             replaceFragment(FragmentCategoriaV())
            }

            R.id.op_resenia_v -> {
                replaceFragment(FragmentReseniaV())
            }
            R.id.op_cerrar_sesion_v ->{
               cerrarSession()
            }
            R.id.op_mis_productos ->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v ->{
                replaceFragment(FragmentOrdenesV())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
