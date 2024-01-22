package com.grl.comunidadesespaa.activities

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
import com.grl.comunidadesespaa.model.Comunidad
import com.grl.comunidadesespaa.R
import com.grl.comunidadesespaa.adapter.ComunidadAdapter
import com.grl.comunidadesespaa.databinding.ActivityMainBinding
import com.grl.comunidadesespaa.domain.ComunidadDAO

class MainActivity : AppCompatActivity() {

    //Variable binding para acceder a los elemntos de la vista de forma sencilla
    private lateinit var binding: ActivityMainBinding
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var comunidadAfectada: Comunidad
    private lateinit var listaComunidades: List<Comunidad>
    private lateinit var miDAO: ComunidadDAO
    private lateinit var adapter: ComunidadAdapter

    //Metodo main que lanza la activity
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        title = "Comunidades autónomas"
        setContentView(binding.root)
        initRecycleView()
        intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                comunidadAfectada.name = result.data?.extras?.getString("nombre").toString()
                miDAO.actualizarBBDD(this, comunidadAfectada)
                binding.rvComunidad.adapter?.notifyDataSetChanged()
            }
        }
    }

    //Menu contextual de las cardView
    override fun onContextItemSelected(item: MenuItem): Boolean {
        comunidadAfectada = listaComunidades[item.groupId]
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
                            miDAO.eliminarComunidad(this, comunidadAfectada)
                            listaComunidades = listaComunidades.minus(comunidadAfectada)
                            adapter.updateList(listaComunidades)
                            binding.rvComunidad.adapter =
                                ComunidadAdapter(listaComunidades) { comunidad ->
                                    onItemSelected(comunidad)
                                }
                        }.create()
                alert.show()
            }

            1 -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("img", comunidadAfectada.image)
                intent.putExtra("name", comunidadAfectada.name)
                intentLauncher.launch(intent)
            }

            2 -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("posicion", item.groupId)
                startActivity(intent)
            }

            3 -> {
                val intent = Intent(this, OpenStreetActivity::class.java)
                intent.putExtra("posicion", item.groupId)
                startActivity(intent)
            }

            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    //Inicializa el recycle view
    private fun initRecycleView() {
        miDAO = ComunidadDAO()
        listaComunidades = miDAO.cargarLista(this)
        val manager = LinearLayoutManager(this)
        binding.rvComunidad.layoutManager = manager
        adapter = ComunidadAdapter(listaComunidades) { comunidad -> onItemSelected(comunidad) }
        binding.rvComunidad.adapter = adapter
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

    //Hace que el menu de la barra de tareas funcione dependiendo de la opción seleccionada
    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.recargar) {
            listaComunidades = miDAO.cargarLista(this)
            adapter.updateList(listaComunidades)
        } else if (id == R.id.limpiar) {
            listaComunidades = listOf()
            adapter.updateList(listaComunidades)
        } else if (id == R.id.logout) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("logout", true)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}