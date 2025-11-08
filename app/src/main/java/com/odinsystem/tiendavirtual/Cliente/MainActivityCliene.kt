package com.odinsystem.tiendavirtual.Cliente

import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.odinsystem.tiendavirtual.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesC
import com.odinsystem.tiendavirtual.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendaC
import com.odinsystem.tiendavirtual.Cliente.Nav_Fragments_Cliente.FragmentInicioC
import com.odinsystem.tiendavirtual.R
import com.odinsystem.tiendavirtual.databinding.ActivityMainClienteBinding

class MainActivityCliene : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainClienteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )


        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(FragmentInicioC())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.op_inicio_c -> {
                replaceFragment(FragmentInicioC())
            }
            R.id.op_mi_perfil_c ->{
                replaceFragment(FragmentInicioC())
            }
            R.id.op_cerrar_sesion_c ->{
                Toast.makeText(applicationContext, "Cerrar Sesión", Toast.LENGTH_SHORT).show()
            }
            R.id.op_mi_tienda_c ->{
                replaceFragment(FragmentTiendaC())
            }
            R.id.op_mis_ordenes_c ->{
                replaceFragment(FragmentMisOrdenesC())

            }

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}