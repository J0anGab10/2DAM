package com.jggcc.practica3intents

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val lanzador = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        if (resultado.resultCode == RESULT_OK) {
            val nombre = resultado.data?.getStringExtra(SegundaActividad.CLAVE_NOMBRE)
            txtResultado.text = "Nombre recibido: $nombre"
        } else {
            txtResultado.text = "No se ha devuelto ningún nombre"
        }
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
        // CONECTAR BOTON CREAR ALARMA
        val btnAlarma = findViewById<Button>(R.id.btnAlarma)
        btnAlarma.setOnClickListener {
            crearAlarma(7, 30, "Clase de PMDM")
        }
        // CONECTAR BOTON CREAR TEMPORIZADOR
        val btnTemporizador = findViewById<Button>(R.id.btnTemporizador)
        btnTemporizador.setOnClickListener {
            crearTemporizador(5 * 60, "Descanso")
        }
        // CONECTAR BOTON LLAMAR MARCADOR
        val btnLlamar = findViewById<Button>(R.id.btnLlamar)
        btnLlamar.setOnClickListener {
            abrirMarcador("600123456")
        }
        // BOTON PARA PEDIR RESULTADO
        val btnPedirResultado = findViewById<Button>(R.id.btnPedirResultado)
        btnPedirResultado.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            lanzador.launch(intent)
        }
    }

    // CREAR ALARMA
    private fun crearAlarma(hora: Int, minutos: Int, mensaje: String) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, hora)
            putExtra(AlarmClock.EXTRA_MINUTES, minutos)
            putExtra(AlarmClock.EXTRA_MESSAGE, mensaje)
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No hay ninguna app de reloj", Toast.LENGTH_SHORT).show()
        }
    }

    // CREAR TEMPORIZADOR
    private fun crearTemporizador(segundos: Int, mensaje: String) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_LENGTH, segundos)
            putExtra(AlarmClock.EXTRA_MESSAGE, mensaje)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No hay ninguna app de reloj", Toast.LENGTH_SHORT).show()
        }
    }

    // ABRIR MARCADOR TELEFONO
    private fun abrirMarcador(numero: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$numero")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No hay ninguna app de teléfono", Toast.LENGTH_SHORT).show()
        }
    }
}