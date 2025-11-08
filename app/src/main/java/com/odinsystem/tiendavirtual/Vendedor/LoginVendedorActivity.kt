package com.odinsystem.tiendavirtual.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.odinsystem.tiendavirtual.databinding.ActivityLoginBinding


class LoginVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLoginV.setOnClickListener {
            validarInfo()
        }

        binding.tvRegistrarV.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroVendedorActivity::class.java))
        }
    }

    private var correo = ""
    private var password = ""
    private fun validarInfo(){
        correo = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()

        if (correo.isEmpty()){
            binding.etEmailV.error = "Ingrese su correo"
            binding.etEmailV.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.etEmailV.error = "Ingrese un correo valido"
        }else if (password.isEmpty()){
            binding.etPasswordV.error = "Ingrese su contraseña"
            binding.etPasswordV.requestFocus()
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