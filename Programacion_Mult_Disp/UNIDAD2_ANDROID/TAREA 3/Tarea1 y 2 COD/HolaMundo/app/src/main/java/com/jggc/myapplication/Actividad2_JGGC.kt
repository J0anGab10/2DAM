package com.jggc.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Clase que representa la segunda pantalla de la aplicación
class Actividad2_JGGC : AppCompatActivity() {

    // Se ejecuta al crearse la pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extiende el contenido de borde a borde en la pantalla
        enableEdgeToEdge()

        // Asocia esta clase con su archivo de diseño XML (res/layout/activity_actividad2_jggc.xml)
        setContentView(R.layout.activity_actividad2_jggc)

        // Ajusta los márgenes interiores para evitar que la barra de notificaciones o botones tapen la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Se ejecuta cuando esta pantalla deja de ser visible por completo (por ejemplo, al minimizar la app)
    override fun onStop() {
        super.onStop()
        // Registra en Logcat que hemos entrado en el estado onStop
        Log.d("JGGC_miCicloVida", "Actividad2: estoy en onStop")

        // 1. INTENT EXPLÍCITO: Sabemos exactamente qué clase queremos abrir (ActividadAviso_JGGC)
        val intent = Intent(this, ActividadAviso_JGGC::class.java)

        // 2. ENVÍO DE DATOS: Adjuntamos el texto con la clave "mensaje" que luego leerá ActividadAviso_JGGC
        intent.putExtra("mensaje", "Atención JGGC la Actividad2_JGGC está parada")

        // Lanzamos la pantalla de aviso
        startActivity(intent)
    }

    // Se ejecuta cuando esta pantalla se cierra y se elimina definitivamente de la memoria (al pulsar Atrás)
    override fun onDestroy() {
        super.onDestroy()
        // Registra en Logcat que la actividad se está destruyendo
        Log.d("JGGC_miCicloVida", "Actividad2: estoy en onDestroy")

        // INTENT IMPLÍCITO: No indicamos una clase de nuestra app, sino una acción general (ACTION_VIEW)
        // junto con una dirección web (Uri). Android busca automáticamente qué app sabe abrir webs (Chrome/navegador)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iesinfantaelena.es/"))

        // Abre el navegador del móvil con la web del instituto
        startActivity(intent)
    }
}