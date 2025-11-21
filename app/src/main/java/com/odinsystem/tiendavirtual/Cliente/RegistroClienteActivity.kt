package com.odinsystem.tiendavirtual.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.odinsystem.tiendavirtual.Constantes

import com.odinsystem.tiendavirtual.databinding.ActivityRegistroClienteBinding




class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var  progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarC.setOnClickListener {
            validarINformacion()
        }

    }
    private var nombre = ""
    private var correo = ""
    private var password = ""
    private var confirmarPassword = ""


    private fun validarINformacion() {
        nombre = binding.etNombresC.text.toString().trim()
        correo = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        confirmarPassword = binding.etCPassword.text.toString().trim()

        if (nombre.isEmpty()) {
            binding.etNombresC.error = "Ingrese su nombre"
            binding.etNombresC.requestFocus()
        }else if(correo.isEmpty()){
            binding.etEmail.error = "Ingrese su email"
            binding.etEmail.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            binding.etEmail.error = "Ingrese un email valido"
            binding.etEmail.requestFocus()
        }else if(password.isEmpty()) {
            binding.etPassword.error = "Ingrese su contraseña"
            binding.etPassword.requestFocus()
        }else if(password.length < 6){
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            binding.etPassword.requestFocus()
        }else if(confirmarPassword.isEmpty()){
            binding.etCPassword.error = "Confirme su contraseña"
            binding.etCPassword.requestFocus()
        }else if(password != confirmarPassword){
            binding.etCPassword.error = "Las contraseñas no coinciden"
            binding.etCPassword.requestFocus()
        }else{
            registrarCliente()

        }
    }

    private fun registrarCliente() {
        progressDialog.setMessage("Registrando cliente")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener {
                insetarInfoDB()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo registrar debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
        private fun insetarInfoDB() {

            progressDialog.setMessage("Registrando cliente")
            val uid = firebaseAuth.uid
            val nombresC = nombre
            val emailC = correo
            val tiempoRegistro = Constantes().obtenerTiempoD()

            val datosClientes = HashMap<String, Any>()


            datosClientes ["uid"] = "$uid"
            datosClientes ["nombresC"] = "$nombresC"
            datosClientes ["emailC"] = "$emailC"
            datosClientes ["tiempoRegistro"] = "$tiempoRegistro"
            datosClientes ["imagen"] = ""
            datosClientes ["tipoUsuario"] = "Cliente"


            val reference = FirebaseDatabase.getInstance().getReference("Clientes")
            reference.child(uid!!)
                .setValue(datosClientes)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    startActivity(
                        Intent(
                            this@RegistroClienteActivity,
                            MainActivityCliente::class.java
                        )
                    )
                    finishAffinity()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        "No se pudo registrar debido a ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
        }

}


