package com.odinsystem.tiendavirtual.Vendedor.nav_Fragments_Vendedor

import android.R.attr.data
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.odinsystem.tiendavirtual.databinding.FragmentCategoriaVBinding



class FragmentCategoriaV : Fragment() {
    private lateinit var  binding: FragmentCategoriaVBinding
    private lateinit var  mContext: Context
    private lateinit var  progressDialog: ProgressDialog
    private var imageUri : Uri?=null



    override fun onAttach(context: Context){
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriaVBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(mContext)
        progressDialog.setTitle("Cargando")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.imgCategorias.setOnClickListener {
            seleccionarImg()
        }



        binding.btnAgregarCategoria.setOnClickListener {
            validarInfo()
        }

        return binding.root

    }
    private fun FragmentCategoriaV.seleccionarImg() {
        ImagePicker.with(requireActivity())
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultadoImg.launch(intent)
            }
    }

    private val resultadoImg =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imageUri = data!!.data
                binding.imgCategorias.setImageURI(imageUri)
            }else{
                Toast.makeText(mContext, "Acccion Cancelado", Toast.LENGTH_SHORT).show()
            }

}

    private var categorias = ""
    private fun validarInfo() {

        categorias = binding.etCategoria.text.toString().trim()
        if(categorias.isEmpty()){
            Toast.makeText(mContext, "Ingrese una categoria", Toast.LENGTH_SHORT).show()
        }else{
            agragarCatBD()
        }

    }

    private fun agragarCatBD() {

        progressDialog.setMessage("Agregarndo Categoria....")
        progressDialog.show()


        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        val KeyId = ref.push().key

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "${KeyId}"
        hashMap["categoria"] = "${categorias}"

        ref.child(KeyId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(mContext, "Categoria Agregada", Toast.LENGTH_SHORT).show()
                binding.etCategoria.setText("")
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(mContext, "Fallo al agregar debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}


