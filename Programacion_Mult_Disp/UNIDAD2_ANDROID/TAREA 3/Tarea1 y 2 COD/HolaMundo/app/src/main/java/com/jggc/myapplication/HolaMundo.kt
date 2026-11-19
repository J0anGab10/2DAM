package com.jggc.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// AppCompatActivity es la clase base para crear una pantalla tradicional en Android (usando archivos XML)
class HolaMundo : AppCompatActivity() {

    // companion object equivale a los miembros 'static' en Java: variables constantes compartidas
    companion object {
        // TAG es la etiqueta que usaremos para filtrar nuestros mensajes en la consola Logcat
        private const val TAG = "JGGC_miCicloVida"
    }

    // Variable entera para llevar un contador numérico de en qué fase del ciclo de vida estamos
    private var miEstado_JGGC: Int = 0

    // 1. onCreate: Se ejecuta UNA SOLA VEZ al crear la pantalla por primera vez en memoria
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la app ocupe toda la pantalla de borde a borde (detrás de la barra de hora/batería)
        enableEdgeToEdge()

        // Conecta este archivo Kotlin con su diseño visual XML (res/layout/activity_main.xml)
        setContentView(R.layout.activity_main)

        // Ajusta los márgenes (padding) automáticamente para que la barra superior e inferior del móvil no tapen tu diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Busca el TextView del XML por su ID para poder manipularlo desde Kotlin
        val tvHolaMundo = findViewById<TextView>(R.id.tvHolaMundo)

        // Le asigna un evento de clic: cuando el usuario toca el texto, salta un mensaje flotante (Toast)
        tvHolaMundo.setOnClickListener {
            Toast.makeText(this, "JGGC: has hecho clic en el texto", Toast.LENGTH_SHORT).show()
        }

        miEstado_JGGC++ // Suma 1 al contador de estado
        // Log.d imprime un mensaje de depuración (Debug) en la pestaña Logcat de Android Studio
        Log.d(TAG, "estoy en onCreate")
    }

    // 2. onStart: La pantalla ya está creada y empieza a hacerse visible para el usuario
    override fun onStart() {
        super.onStart()
        miEstado_JGGC++
        Log.d(TAG, "estoy en onStart")
    }

    // 3. onResume: La pantalla ya está en primer plano y el usuario puede tocar botones e interactuar con ella
    override fun onResume() {
        super.onResume()
        miEstado_JGGC++
        Log.d(TAG, "estoy en onResume")
    }

    // 4. onPause: La pantalla pierde el foco (por ejemplo, sale un diálogo encima o abres la multitarea)
    override fun onPause() {
        super.onPause()
        miEstado_JGGC-- // Resta 1 al contador porque empezamos a salir de la pantalla
        Log.d(TAG, "estoy en onPause")
    }

    // 5. onStop: La pantalla deja de verse por completo (pulsas el botón Inicio o cambias de app)
    override fun onStop() {
        super.onStop()
        miEstado_JGGC--
        Log.d(TAG, "estoy en onStop")
    }

    // 6. onRestart: Se ejecuta si vuelves a abrir la app cuando estaba parada en segundo plano (antes de volver a onStart)
    override fun onRestart() {
        super.onRestart()
        // No se modifica porque a continuación hace onStart
        Log.d(TAG, "estoy en onRestart")
    }

    // 7. onDestroy: La pantalla se cierra definitivamente y se borra de la memoria (pulsando Atrás o cerrando la app)
    override fun onDestroy() {
        super.onDestroy()
        miEstado_JGGC--
        Log.d(TAG, "estoy en onDestroy")

        // Crea un Intent implícito usando una acción personalizada definida en el AndroidManifest.xml
        val intent = Intent("com.jggc.myapplication.ABRIR_ACTIVIDAD2")
        // Lanza la segunda pantalla (Actividad2_JGGC) justo cuando esta se destruye
        startActivity(intent)
    }
}