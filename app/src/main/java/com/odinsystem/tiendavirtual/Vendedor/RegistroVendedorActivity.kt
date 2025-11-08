package com.odinsystem.tiendavirtual.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.odinsystem.tiendavirtual.Constantes
import com.odinsystem.tiendavirtual.databinding.ActivityRegistroVendedorBinding
import kotlin.jvm.java

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnRegistrarV.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombre = ""
    private var correo = ""
    private var password = ""
    private var Cpassword = ""

    private fun validarInformacion() {
        nombre = binding.etNombreV.text.toString().trim()
        correo = binding.etEmailV.text.toString().trim()
        password = binding.etCpasswordV.text.toString().trim()
        Cpassword = binding.etCpasswordV.text.toString().trim()
        if (nombre.isEmpty()) {
            binding.etNombreV.error = "Ingrese nombre"
            binding.etNombreV.requestFocus()
        } else if (correo.isEmpty()) {
            binding.etEmailV.error = "Ingrese correo"
            binding.etEmailV.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            binding.etNombreV.error = "Correo no valido"
            binding.etNombreV.requestFocus()
        } else if (password.isEmpty()) {
            binding.etCpasswordV.error = "Ingrese contraseña"
            binding.etCpasswordV.requestFocus()
        } else if (password.length < 6) {
            binding.etCpasswordV.error = "Contraseña debe tener al menos 6 caracteres"
            binding.etCpasswordV.requestFocus()
        } else if (Cpassword.isEmpty()) {
            binding.etCpasswordV.error = "Confirme contraseña"
            binding.etCpasswordV.requestFocus()
        } else if (password != Cpassword) {
            binding.etCpasswordV.error = "Contraseñas no coinciden"
            binding.etCpasswordV.requestFocus()
            } else
                registrarVendedor()
    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando cuenta en linea...")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "No se pudo crear el usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    private fun insertarInfoBD() {
        progressDialog.setMessage("Guardando informacion...")
        val uidDB = firebaseAuth.uid
        val nombreBD =nombre
        val correoBD = correo
        val tipoUsusario = "Vendedor"
        val tiempoBD = Constantes().obtenerTiempoD()

        val datosVendedor = HashMap<String, Any>()

        datosVendedor["uid"] = "$uidDB"
        datosVendedor["nombre"] = "$nombreBD"
        datosVendedor["correo"] = "$correoBD"
        datosVendedor["tipoUsuario"] = "Vendedor"
        datosVendedor["tiempo"] = "$tiempoBD"

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidDB!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "FAllo el regisdtro en la Bd debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }




    }
}



