package com.grl.comunidadesespaa.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grl.comunidadesespaa.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Comunidades autónomas"

        binding.campo.hint = intent.getStringExtra("name")
        binding.foto.setImageResource(intent.getIntExtra("img", 0))

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnChange.setOnClickListener {
            val intent = Intent()
            val cajetin = binding.campo.text.toString()
            if (cajetin != "") {
                intent.putExtra("nombre", cajetin)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}