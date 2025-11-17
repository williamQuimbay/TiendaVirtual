package com.odinsystem.tiendavirtual.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odinsystem.tiendavirtual.Modelos.ModeloImagenSeleccionada
import com.odinsystem.tiendavirtual.R
import com.odinsystem.tiendavirtual.databinding.ItemImagenesSelecionadasBinding

class AdaptadorImagenSeleccionada
    (
    private val context: Context,
    private val ImagenesSelecionadasArrayList : ArrayList<ModeloImagenSeleccionada>
): RecyclerView.Adapter<AdaptadorImagenSeleccionada.holderImagenesSeleccionadas>(){
    private lateinit var binding: ItemImagenesSelecionadasBinding
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): holderImagenesSeleccionadas {

        binding = ItemImagenesSelecionadasBinding.inflate(LayoutInflater.from(context),parent,false)
        return holderImagenesSeleccionadas(binding.root)

    }

    override fun getItemCount(): Int {

        return ImagenesSelecionadasArrayList.size

    }

    override fun onBindViewHolder(holder: holderImagenesSeleccionadas, position: Int) {
        val modelo = ImagenesSelecionadasArrayList[position]
        val imagenUri = modelo.imgUri

            // leyendo las imagenes
        try {
            Glide.with(context)
                .load(imagenUri)
                .placeholder(R.drawable.item_imagen)
                .into(holder.item_img)

        }catch (e:Exception){

        }

        //Evento para borrar la imagen

        holder.btn_borrar.setOnClickListener {
            ImagenesSelecionadasArrayList.remove(modelo)
            notifyDataSetChanged()

        }


        }


    inner class holderImagenesSeleccionadas(itemView: View) : RecyclerView.ViewHolder(itemView){

        var item_img = binding.itemImagen
        var btn_borrar = binding.borrarItem

    }

}