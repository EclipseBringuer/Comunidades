package com.grl.comunidadesespaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grl.comunidadesespaa.databinding.ActivityEditBinding
import com.grl.comunidadesespaa.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        binding = ActivityEditBinding.inflate(layoutInflater)
        title = "Comunidades autónomas"
        val nombre = intent.getStringExtra("name")
        val imagen = intent.getIntExtra("image",0)
        val textview = binding.campo
        val cancelar = binding.btnCancel
        val cambiar = binding.btnChange
        val img = binding.foto
        textview.hint = nombre
        img.setImageResource(imagen)

        cancelar.setOnClickListener{
            finish()
        }
        cambiar.setOnClickListener{

        }
    }
}