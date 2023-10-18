package com.grl.comunidadesespaa

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.grl.comunidadesespaa.adapter.ComunidadAdapter
import com.grl.comunidadesespaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Variable binding para acceder a los elemntos de la vista de forma sencilla
    private lateinit var binding: ActivityMainBinding
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var comunidadAfectada: Comunidad

    //Metodo main que lanza la activity
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        title = "Comunidades autónomas"
        setContentView(binding.root)
        initRecycleView()
        intentLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode== RESULT_OK){
                comunidadAfectada.name=result.data?.extras?.getString("nombre").toString()
                binding.rvComunidad.adapter?.notifyDataSetChanged()
            }
        }
    }

    //Menu contextual de las cardView
    override fun onContextItemSelected(item: MenuItem): Boolean {
        comunidadAfectada = ComunidadProvider.comunidadList[item.groupId]

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
            1->{
                val intent= Intent(this,EditActivity::class.java)
                intent.putExtra("img",comunidadAfectada.image)
                intent.putExtra("name",comunidadAfectada.name)
                intentLauncher.launch(intent)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    //Inicializa el recycle view
    private fun initRecycleView() {
        val manager = LinearLayoutManager(this)
        binding.rvComunidad.layoutManager = manager
        binding.rvComunidad.adapter =
            ComunidadAdapter(ComunidadProvider.llenar()) { onItemSelected(it) }
    }

    //Muestra un snackBar
    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    //Metodo que funciona cuando tocas una cardView
    private fun onItemSelected(comunidad: Comunidad) {
        Toast.makeText(
            this,
            "Yo soy de ${comunidad.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    //Método para inflar el menu de la barra de tareas
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //Hace que el menu de la toolbar funcione dependiendo de la opción seleccionada
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