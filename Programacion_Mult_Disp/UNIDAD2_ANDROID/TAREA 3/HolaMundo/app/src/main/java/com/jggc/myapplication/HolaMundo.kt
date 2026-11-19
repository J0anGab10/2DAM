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

class HolaMundo : AppCompatActivity() {
    companion object {
        private const val TAG = "JGGC_miCicloVida"
    }
    private var miEstado_JGGC: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvHolaMundo = findViewById<TextView>(R.id.tvHolaMundo)
        tvHolaMundo.setOnClickListener {
            Toast.makeText(this, "JGGC: has hecho clic en el texto", Toast.LENGTH_SHORT).show()
        }

        miEstado_JGGC++
        Log.d(TAG, "estoy en onCreate")
    }
    override fun onStart() {
        super.onStart()
        miEstado_JGGC++
        Log.d(TAG, "estoy en onStart")
    }

    override fun onResume() {
        super.onResume()
        miEstado_JGGC++
        Log.d(TAG, "estoy en onResume")
    }

    override fun onPause() {
        super.onPause()
        miEstado_JGGC--
        Log.d(TAG, "estoy en onPause")
    }

    override fun onStop() {
        super.onStop()
        miEstado_JGGC--
        Log.d(TAG, "estoy en onStop")
    }

    override fun onRestart() {
        super.onRestart()
        // No se modifica porque a continuación hacer onStart
        Log.d(TAG, "estoy en onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        miEstado_JGGC--
        Log.d(TAG, "estoy en onDestroy")

        val intent = Intent("com.jggc.myapplication.ABRIR_ACTIVIDAD2")
        startActivity(intent)
    }
}