package com.grl.comunidadesespaa.adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grl.comunidadesespaa.model.Comunidad
import com.grl.comunidadesespaa.databinding.ItemComunidadBinding

class ComunidadViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener {

    private var binding = ItemComunidadBinding.bind(view)
    private lateinit var comunidad: Comunidad

    //Da los valores y propiedades de la comunidad a la item-comunidad.xml
    fun render(comunidad: Comunidad, onClickListener: (Comunidad) -> Unit) {
        this.comunidad = comunidad
        binding.name.text = comunidad.name
        binding.img.setImageResource(comunidad.image)
        itemView.setOnClickListener {
            onClickListener(comunidad)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    //Crea el menu contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(comunidad.name)
        menu.add(this.adapterPosition, 0, 0, "Eliminar")
        menu.add(this.adapterPosition, 1, 1, "Editar")
        menu.add(this.adapterPosition, 2, 2, "Abrir con Google Maps")
        menu.add(this.adapterPosition, 3, 3, "Abrir con OpenStreet")
    }
}