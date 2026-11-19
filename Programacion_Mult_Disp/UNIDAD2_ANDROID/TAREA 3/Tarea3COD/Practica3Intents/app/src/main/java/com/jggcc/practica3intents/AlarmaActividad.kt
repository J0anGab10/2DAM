package com.jggcc.practica3intents

import android.os.Bundle
import android.provider.AlarmClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AlarmaActividad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarma_actividad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val hora = intent.getIntExtra(AlarmClock.EXTRA_HOUR, -1)
        val minutos = intent.getIntExtra(AlarmClock.EXTRA_MINUTES, -1)
        val mensaje = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE) ?: "(sin nombre)"

        val txtInfo = findViewById<TextView>(R.id.txtInfo)
        txtInfo.text = "Petición de alarma recibida\n\nHora: $hora:$minutos\nNombre: $mensaje\n\nNo se ha creado ninguna alarma."

        Toast.makeText(this, "Petición de alarma capturada", Toast.LENGTH_LONG).show()
    }
}