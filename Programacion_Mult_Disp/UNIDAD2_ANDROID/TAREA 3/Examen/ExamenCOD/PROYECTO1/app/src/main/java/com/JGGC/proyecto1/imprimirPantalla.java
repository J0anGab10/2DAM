package com.JGGC.proyecto1;

import static androidx.core.content.ContextCompat.startActivity;
import static java.sql.DriverManager.println;

import android.content.ActivityNotFoundException;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class imprimirPantalla {
    public imprimirPantalla(int i, @NotNull String string) {
    }

    public void imprimirPantalla(int numero, String texto) {
        println(texto + numero);
    }
}
