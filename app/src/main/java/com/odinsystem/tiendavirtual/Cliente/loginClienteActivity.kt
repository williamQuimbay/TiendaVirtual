package com.odinsystem.tiendavirtual.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.odinsystem.tiendavirtual.Vendedor.MainActivityVendedor
import com.odinsystem.tiendavirtual.databinding.ActivityLoginClienteBinding

class loginClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var  progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLoginC.setOnClickListener {
            validarDatos()

        }

        binding.tvRegistrarC.setOnClickListener {
            startActivity(Intent(this@loginClienteActivity, RegistroClienteActivity::class.java))
        }

    }
    private var correo = ""
    private var password = ""

    private fun validarDatos() {
        correo = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        if (correo.isEmpty()){
            binding.etEmail.error = "Ingrese su correo"
            binding.etEmail.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.etEmail.error = "Ingrese un correo valido"
        }else if (password.isEmpty()){
            binding.etPassword.error = "Ingrese su contraseña"
            binding.etPassword.requestFocus()
        }else{
            loginVendedor()
        }

    }
    private fun loginVendedor() {
        progressDialog.setMessage("Iniciando sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finishAffinity()
                Toast.makeText(this, "Bienvenido(a)", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "No se pudo iniciar sesion debido a ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
}


