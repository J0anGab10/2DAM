package com.JGGC.proyecto1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JGGC.proyecto1.imprimirPantalla

class MainActivity : AppCompatActivity() {

    private val lanzador = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Pantalla1 = findViewById<Button>(R.id.P1)
        Pantalla1.setOnClickListener {
            imprimirPantalla(10, "PANTALLA 1: Hola, JGGC me ha indicado el valor: ")
        }

        val Pantalla2 = findViewById<Button>(R.id.P2)
        Pantalla2.setOnClickListener {

        }


    }
}