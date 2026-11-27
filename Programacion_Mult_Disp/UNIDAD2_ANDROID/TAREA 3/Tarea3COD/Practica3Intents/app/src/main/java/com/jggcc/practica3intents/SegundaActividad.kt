package com.jggcc.practica3intents

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegundaActividad : AppCompatActivity() {
    companion object {
        const val CLAVE_NOMBRE = "NOMBRE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segunda_actividad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editNombre = findViewById<EditText>(R.id.editNombre)
        val btnDevolver = findViewById<Button>(R.id.btnDevolver)

        btnDevolver.setOnClickListener {
            val respuesta = Intent()
            respuesta.putExtra(CLAVE_NOMBRE, editNombre.text.toString())

            setResult(RESULT_OK, respuesta)
            finish()
        }
    }
}
