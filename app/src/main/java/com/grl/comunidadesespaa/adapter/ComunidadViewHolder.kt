package com.grl.comunidadesespaa.adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grl.comunidadesespaa.Comunidad
import com.grl.comunidadesespaa.databinding.ItemComunidadBinding

class ComunidadViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener {

    private var binding = ItemComunidadBinding.bind(view)
    private lateinit var comunidad: Comunidad

    fun render(comunidad: Comunidad, onClickListener: (Comunidad) -> Unit) {
        this.comunidad = comunidad
        binding.name.text = comunidad.name
        binding.img.setImageResource(comunidad.image)
        itemView.setOnClickListener {
            onClickListener(comunidad)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(comunidad.name)
        menu.add(this.adapterPosition, 0, 0, "Eliminar")
    }
}