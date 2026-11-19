package com.jggc.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Actividad2_JGGC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividad2_jggc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("JGGC_miCicloVida", "Actividad2: estoy en onStop")

        val intent = Intent(this, ActividadAviso_JGGC::class.java)
        intent.putExtra("mensaje", "Atención JGGC la Actividad2_JGGC está parada")
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("JGGC_miCicloVida", "Actividad2: estoy en onDestroy")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iesinfantaelena.es/"))
        startActivity(intent)
    }
}