package com.grl.comunidadesespaa.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.grl.comunidadesespaa.databinding.ActivityImageBinding
import com.grl.comunidadesespaa.domain.ComunidadDAO

class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.extras!!.getInt("id")
        val miDao = ComunidadDAO()
        val comunidad = miDao.obtenerComunidad(this, id)
        if (comunidad.uri.isNotEmpty()) {
            val uri = Uri.parse(comunidad.uri)
            binding.wholeImage.load(uri)
        } else {
            Toast.makeText(this, "${comunidad.name} no tiene una foto asociada", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
