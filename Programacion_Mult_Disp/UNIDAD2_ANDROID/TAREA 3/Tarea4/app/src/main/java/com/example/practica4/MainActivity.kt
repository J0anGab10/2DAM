package com.example.practica4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent carga la interfaz hecha con Jetpack Compose en lugar de un archivo XML
        setContent {
            MaterialTheme {
                // Surface es el lienzo o fondo base de toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(), // Ocupa todo el ancho y alto de la pantalla
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamamos a nuestra función que controla las pantallas
                    AppNavegacion()
                }
            }
        }
    }
}

// @Composable indica que esta función dibuja elementos visuales en pantalla
@Composable
fun AppNavegacion() {
    // 1. Creamos el "mando a distancia" (navController) que guarda en qué pantalla estamos
    val navController = rememberNavController()

    // 2. NavHost es el contenedor donde van cambiando las pantallas.
    // startDestination = "inicio" indica qué pantalla se abre nada más arrancar la app.
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        // Definimos las rutas (como si fueran URLs internas) y qué pantalla carga cada una
        composable("inicio") {
            PantallaPrincipal(navController)
        }
        composable("pantalla1") {
            Pantalla1(navController)
        }
        composable("pantalla2") {
            Pantalla2(navController)
        }
    }
}

// --- PANTALLA PRINCIPAL ---
@Composable
fun PantallaPrincipal(navController: NavController) {
    // Column coloca los elementos uno debajo del otro en vertical
    Column(
        modifier = Modifier
            .fillMaxSize() // Que la columna ocupe toda la pantalla
            .padding(16.dp), // Margen interior de 16dp por los bordes
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente (en medio)
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
    ) {
        // Texto principal
        Text(
            text = "Bienvenido a mi aplicación - JGGC",
            fontSize = 20.sp, // Tamaño de letra (siempre se usa .sp para textos)
            fontWeight = FontWeight.Bold, // Letra en negrita
            textAlign = TextAlign.Center, // Centra el texto dentro de su caja
            modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho para poder centrarse bien
        )

        // Spacer crea un hueco vacío de separación entre elementos
        Spacer(modifier = Modifier.height(24.dp))

        // Botón 1: al pulsarlo, le decimos al navController que vaya a la ruta "pantalla1"
        Button(onClick = { navController.navigate("pantalla1") }) {
            Text(text = "Ir a la pantalla 1")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón 2: nos lleva a la ruta "pantalla2"
        Button(onClick = { navController.navigate("pantalla2") }) {
            Text(text = "Ir a la pantalla 2")
        }
    }
}

// --- PANTALLA 1 ---
@Composable
fun Pantalla1(navController: NavController) {
    // Necesitamos el "context" (el estado actual de la app) para poder lanzar un Toast
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla 1 - JGGC",
            fontSize = 28.sp, // Letra más grande como pide el enunciado
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                // .clickable convierte el texto en un elemento pulsable
                .clickable {
                    // Muestra el mensaje flotante abajo durante un tiempo corto (LENGTH_SHORT)
                    Toast.makeText(context, "Pantalla 1", Toast.LENGTH_SHORT).show()
                }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // popBackStack() cierra la pantalla actual y vuelve a la anterior (la principal)
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Inicio")
        }
    }
}

// --- PANTALLA 2 ---
@Composable
fun Pantalla2(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla 2 - JGGC",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast.makeText(context, "Pantalla 2", Toast.LENGTH_SHORT).show()
                }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Inicio")
        }
    }
}