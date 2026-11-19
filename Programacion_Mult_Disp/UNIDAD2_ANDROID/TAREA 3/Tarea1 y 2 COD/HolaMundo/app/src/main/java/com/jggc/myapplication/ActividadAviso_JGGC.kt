package com.jggc.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Clase que representa la pantalla de aviso (hereda de AppCompatActivity para usar diseño XML)
class ActividadAviso_JGGC : AppCompatActivity() {

    // Se ejecuta automáticamente al abrirse esta pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extiende el diseño de la pantalla de borde a borde (detrás de la barra de estado y navegación)
        enableEdgeToEdge()

        // Carga el diseño visual desde res/layout/activity_actividad_aviso_jggc.xml
        setContentView(R.layout.activity_actividad_aviso_jggc)

        // Añade márgenes automáticos al contenedor principal (R.id.main) para que las barras del sistema no tapen el contenido
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. RECIBIR DATOS: 'intent' es el objeto con el que se abrió esta pantalla.
        // getStringExtra("mensaje") extrae el texto que la pantalla anterior envió bajo la clave "mensaje".
        // (Si no se envió nada, devuelve null).
        val mensaje = intent.getStringExtra("mensaje")

        // 2. MOSTRAR DATOS: Busca el TextView llamado 'tvAviso' en el XML y le cambia el texto (.text) por el mensaje recibido
        findViewById<TextView>(R.id.tvAviso).text = mensaje
    }
}