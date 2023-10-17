package com.grl.comunidadesespaa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        title = "Comunidades autónomas"
        val nombre = intent.getStringExtra("name")
        val imagen = intent.getIntExtra("img",0)
        val textview = findViewById<TextView>(R.id.campo)
        val cancelar = findViewById<Button>(R.id.btnCancel)
        val cambiar = findViewById<Button>(R.id.btnChange)
        val img = findViewById<ImageView>(R.id.foto)
        textview.hint = nombre
        img.setImageResource(imagen)

        cancelar.setOnClickListener{
            finish()
        }
        cambiar.setOnClickListener{
            val intent = Intent()
            val cajetin = textview.text.toString()
            if(cajetin!=""){
            intent.putExtra("nombre",cajetin)
            setResult(RESULT_OK,intent)
            }
            finish()
        }
    }
}