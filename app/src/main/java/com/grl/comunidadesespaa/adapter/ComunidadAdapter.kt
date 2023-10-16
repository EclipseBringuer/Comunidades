package com.grl.comunidadesespaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grl.comunidadesespaa.Comunidad
import com.grl.comunidadesespaa.R

class ComunidadAdapter(
    private val listaComunidades: List<Comunidad>,
    private val onClickListener: (Comunidad) -> Unit
) :
    RecyclerView.Adapter<ComunidadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComunidadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ComunidadViewHolder(layoutInflater.inflate(R.layout.item_comunidad, parent, false))
    }

    override fun getItemCount(): Int = listaComunidades.size

    override fun onBindViewHolder(holder: ComunidadViewHolder, position: Int) {
        val item = listaComunidades[position]
        holder.render(item, onClickListener)
    }
}