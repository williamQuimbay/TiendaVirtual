package com.odinsystem.tiendavirtual.Vendedor.Productos

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odinsystem.tiendavirtual.Adaptadores.AdaptadorImagenSeleccionada
import com.odinsystem.tiendavirtual.Constantes
import com.odinsystem.tiendavirtual.Modelos.ModeloImagenSeleccionada
import com.odinsystem.tiendavirtual.Modelos.modeloCategoria
import com.odinsystem.tiendavirtual.databinding.ActivityAgragarProductoBinding
import com.odinsystem.tiendavirtual.databinding.ActivityLoginClienteBinding

class AgragarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgragarProductoBinding
    private var imagenUri: Uri? = null

    private lateinit var imageneSelecArrayList: ArrayList<ModeloImagenSeleccionada>
    private lateinit var adaptadorImagenSeleccionada: AdaptadorImagenSeleccionada

    private lateinit var categoriaArrayList: ArrayList<modeloCategoria>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgragarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarCategoria()

        imageneSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener {
            selecionarImg()
        }
        binding.Categoria.setOnClickListener {
            selecCategorias()
        }

        cargarImagenes()

    }

    private fun cargarCategoria() {
        categoriaArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val model = ds.getValue(modeloCategoria::class.java)
                    categoriaArrayList.add(model!!)

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")

            }
        })
}


    private var idCat = ""
    private var tituloCat = ""

    private fun  selecCategorias(){
        val categoriasArray = arrayOfNulls<String>(categoriaArrayList.size)
        for (i in categoriaArrayList.indices){
            categoriasArray[i] = categoriaArrayList[i].categoria
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccione una Categoria")
            .setItems(categoriasArray){dialog,which->

                idCat = categoriaArrayList[which].id
                tituloCat = categoriaArrayList[which].categoria
                binding.Categoria.text = tituloCat

            }.show()

    }

    private fun cargarImagenes() {
        adaptadorImagenSeleccionada =AdaptadorImagenSeleccionada(this,imageneSelecArrayList)
        binding.RVImagenes.adapter = adaptadorImagenSeleccionada

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
                val tiempo = "${Constantes().obtenerTiempoD()}"
                val modeloImagenSeleccionada = ModeloImagenSeleccionada(tiempo, imagenUri, null,false)
                imageneSelecArrayList.add(modeloImagenSeleccionada)
                cargarImagenes()

            }else{
                Toast.makeText(this, "Imagen no seleccionada", Toast.LENGTH_SHORT).show()
            }

        }
}