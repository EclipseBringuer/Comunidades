package com.grl.comunidadesespaa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.grl.comunidadesespaa.adapter.ComunidadAdapter
import com.grl.comunidadesespaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        title = "Comunidades autónomas"
        setContentView(binding.root)
        initRecycleView()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        lateinit var comunidadAfectada: Comunidad
        comunidadAfectada = ComunidadProvider.comunidadList[item.itemId]
        when (item.itemId) {
            0 -> {
                val alert =
                    AlertDialog.Builder(this).setTitle("Eliminar ${comunidadAfectada.name}")
                        .setMessage(
                            "¿Estas seguro de que quieres eliminar ${comunidadAfectada.name}?"
                        )
                        .setNeutralButton("Cerrar", null).setPositiveButton(
                            "Aceptar"
                        ) { _, _ ->
                            display("Se ha eliminado ${comunidadAfectada.name}")
                            ComunidadProvider.comunidadList.removeAt(item.groupId)
                            binding.rvComunidad.adapter?.notifyItemRemoved(item.groupId)
                            binding.rvComunidad.adapter?.notifyItemRangeChanged(
                                item.groupId,
                                ComunidadProvider.comunidadList.size
                            )
                            binding.rvComunidad.adapter =
                                ComunidadAdapter(ComunidadProvider.comunidadList) { comunidad ->
                                    onItemSelected(comunidad)
                                }
                        }.create()
                alert.show()
            }

            else -> return super.onContextItemSelected(item)
        }



        return true
    }

    private fun initRecycleView() {
        val manager = LinearLayoutManager(this)
        binding.rvComunidad.layoutManager = manager
        binding.rvComunidad.adapter =
            ComunidadAdapter(ComunidadProvider.llenar()) { onItemSelected(it) }
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun onItemSelected(comunidad: Comunidad) {
        Toast.makeText(
            this,
            "Yo soy de ${comunidad.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.recargar) {
            binding.rvComunidad.adapter =
                ComunidadAdapter(ComunidadProvider.llenar()) { onItemSelected(it) }
            binding.rvComunidad.adapter?.notifyDataSetChanged()
        } else if (id == R.id.limpiar) {
            ComunidadProvider.comunidadList.clear()
            binding.rvComunidad.adapter?.notifyDataSetChanged()
        }

        return super.onOptionsItemSelected(item)
    }
}