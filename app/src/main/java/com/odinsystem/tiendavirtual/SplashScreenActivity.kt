package com.odinsystem.tiendavirtual

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odinsystem.tiendavirtual.Cliente.MainActivityCliente
import com.odinsystem.tiendavirtual.Vendedor.MainActivityVendedor

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        verBienvenida()

    }

    private fun verBienvenida() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                comprobarTipoUsuario()
            }

        }.start()
    }

    private fun comprobarTipoUsuario() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, SeleccionarTipoActivity::class.java))
        } else {
            val uid = firebaseUser.uid
            val refUsuarios = FirebaseDatabase.getInstance().getReference("Usuarios")
            refUsuarios.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val tipoU = snapshot.child("tipoUsuario").value?.toString()
                        navegarPorTipo(tipoU)
                    } else {
                        // Buscar en Clientes si no está en Usuarios
                        val refClientes = FirebaseDatabase.getInstance().getReference("Clientes")
                        refClientes.child(uid)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val tipoU = snapshot.child("tipoUsuario").value?.toString()
                                    navegarPorTipo(tipoU)
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    mostrarError(error)
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    mostrarError(error)
                }
            })
        }
    }

    private fun navegarPorTipo(tipoU: String?) {
        when (tipoU?.lowercase()) {
            "cliente" -> startActivity(Intent(this, MainActivityCliente::class.java))
            "vendedor" -> startActivity(Intent(this, MainActivityVendedor::class.java))
            else -> Toast.makeText(
                this,
                "Tipo de usuario no reconocido: $tipoU",
                Toast.LENGTH_SHORT
            ).show()
        }
        finishAffinity()
    }

    private fun mostrarError(error: DatabaseError) {
        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
    }
}
