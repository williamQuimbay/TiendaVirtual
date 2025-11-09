package com.odinsystem.tiendavirtual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.odinsystem.tiendavirtual.Cliente.loginClienteActivity
import com.odinsystem.tiendavirtual.Vendedor.LoginVendedorActivity
import com.odinsystem.tiendavirtual.databinding.ActivitySelecionarTipoBinding

class SelecionarTipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelecionarTipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecionarTipoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tipoVendedor.setOnClickListener {
            startActivity(Intent(this@SelecionarTipoActivity, LoginVendedorActivity::class.java))
        }
        binding.tipoClinete.setOnClickListener {
            startActivity(Intent(this@SelecionarTipoActivity, loginClienteActivity::class.java))
        }
    }
}