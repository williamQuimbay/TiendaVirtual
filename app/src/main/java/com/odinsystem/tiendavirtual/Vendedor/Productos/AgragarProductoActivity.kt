package com.odinsystem.tiendavirtual.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.odinsystem.tiendavirtual.databinding.ActivityAgragarProductoBinding
import com.odinsystem.tiendavirtual.databinding.ActivityLoginClienteBinding

class AgragarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgragarProductoBinding
    private var imagenUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgragarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imgAgregarProducto.setOnClickListener {
            selecionarImg()
        }

    }

    private fun selecionarImg(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .createIntent { intent ->
                resultadoImg.launch(intent)

            }

    }
    private val  resultadoImg =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if (resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imagenUri = data!!.data
                binding.imgAgregarProducto.setImageURI(imagenUri )
            }else{
                Toast.makeText(this, "Imagen no seleccionada", Toast.LENGTH_SHORT).show()
            }

        }
}