package com.elbuengusto;

import com.elbuengusto.componentes.LoginComponente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class HelloController {

    @FXML private AnchorPane vistaHome;
    @FXML private LoginComponente vistaLoginComponente;
    @FXML private Button btnIrLogin;
    @FXML private HBox boxPerfilUsuario;
    @FXML private Label lblNombreUsuario;
    @FXML private Label lblBienvenido;

    @FXML
    public void initialize() {
        // 4 y 6. Captura del evento principal emitido por el componente autónomo
        vistaLoginComponente.setOnConfirmarAction(event -> {
            String usuario = vistaLoginComponente.getUsuarioIngresado();

            // Impresión obligatoria en la terminal/consola
            System.out.println("==================================================");
            System.out.println("[VENTANA PRINCIPAL - EL BUEN GUSTO]");
            System.out.println("Acción principal recibida desde LoginComponente.");
            System.out.println("Sesión iniciada correctamente para el usuario: " + usuario);
            System.out.println("==================================================");

            // Pasamos de la Pantalla 2 (Login) a la Pantalla 3 (Home con Raúl + dinosaurio + ¡Bienvenido!)
            lblNombreUsuario.setText(usuario);
            btnIrLogin.setVisible(false);
            boxPerfilUsuario.setVisible(true);
            lblBienvenido.setVisible(true);

            vistaLoginComponente.setVisible(false);
            vistaHome.setVisible(true);
        });
    }

    // Al pulsar el botón redondo "INICIAR SESIÓN" de la Pantalla 1, abre la Pantalla 2 (LoginComponente)
    @FXML
    private void mostrarPantallaLogin() {
        vistaHome.setVisible(false);
        vistaLoginComponente.setVisible(true);
    }
}