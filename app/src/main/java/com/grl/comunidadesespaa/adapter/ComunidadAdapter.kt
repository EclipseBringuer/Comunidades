package com.grl.comunidadesespaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grl.comunidadesespaa.model.Comunidad
import com.grl.comunidadesespaa.R
import com.grl.comunidadesespaa.utils.ComunidadDiffUtil

class ComunidadAdapter(
    private var listaComunidades: List<Comunidad>,
    private val onClickListener: (Comunidad) -> Unit
) :
    RecyclerView.Adapter<ComunidadViewHolder>() {

    fun updateList(newList: List<Comunidad>) {
        val comunidadDiff = ComunidadDiffUtil(listaComunidades, newList)
        val result = DiffUtil.calculateDiff(comunidadDiff)
        listaComunidades = newList
        result.dispatchUpdatesTo(this)
    }

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