package com.grl.comunidadesespaa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun initRecycleView() {
        val manager = LinearLayoutManager(this)
        binding.rvComunidad.layoutManager = manager
        binding.rvComunidad.adapter =
            ComunidadAdapter(ComunidadProvider.llenar()) { onItemSelected(it) }
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
        } else if(id == R.id.limpiar){
            ComunidadProvider.comunidadList.clear()
            binding.rvComunidad.adapter?.notifyDataSetChanged()
        }

        return super.onOptionsItemSelected(item)
    }
}