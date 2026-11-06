package com.elbuengusto.componentes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LoginComponente extends StackPane {

    // 2. ENCAPSULACIÓN Y BLINDAJE DE LA VISTA: Todos los controles son private
    @FXML private Label lblTitulo;
    @FXML private Button btnCrearCuenta;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private Hyperlink linkRecuperar;
    @FXML private Button btnConfirmar;

    // 4. NOTIFICACIÓN DE LA ACCIÓN PRINCIPAL
    private final ObjectProperty<EventHandler<ActionEvent>> onConfirmarAction = new SimpleObjectProperty<>();

    // 1. CONSTRUCCIÓN DESACOPLADA (fx:root)
    public LoginComponente() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/elbuengusto/componentes/login-componente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Error al cargar login-componente.fxml", exception);
        }
    }

    @FXML
    private void initialize() {
        // 3. LÓGICA INTERNA Y REACTIVIDAD (Binding)
        // Deshabilita el botón CONFIRMAR si el correo o la contraseña están vacíos
        btnConfirmar.disableProperty().bind(
                txtCorreo.textProperty().isEmpty()
                        .or(txtPassword.textProperty().isEmpty())
        );

        // Lógica interna aislada de los botones secundarios
        btnCrearCuenta.setOnAction(e -> System.out.println("[LoginComponente] Acción interna: Pulsado 'Crear cuenta'."));
        linkRecuperar.setOnAction(e -> System.out.println("[LoginComponente] Acción interna: Pulsado 'Recuperar contraseña'."));
    }

    @FXML
    private void manejarConfirmacion(ActionEvent event) {
        if (onConfirmarAction.get() != null) {
            onConfirmarAction.get().handle(event);
        }
    }

    // 5. PERSONALIZACIÓN MEDIANTE PROPIEDADES JAVABEAN (Tríadas)
    public StringProperty textoBotonProperty() {
        return btnConfirmar.textProperty();
    }

    public String getTextoBoton() {
        return btnConfirmar.getText();
    }

    public void setTextoBoton(String texto) {
        btnConfirmar.setText(texto);
    }

    public StringProperty tituloProperty() {
        return lblTitulo.textProperty();
    }

    public String getTitulo() {
        return lblTitulo.getText();
    }

    public void setTitulo(String titulo) {
        lblTitulo.setText(titulo);
    }

    // Devuelve el nombre del usuario introducido (o "Raul" si escribe un correo tipo raul@...)
    public String getUsuarioIngresado() {
        String texto = txtCorreo.getText().trim();
        if (texto.contains("@")) {
            String nombre = texto.substring(0, texto.indexOf("@"));
            return nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
        return texto.isEmpty() ? "Raul" : texto;
    }

    public ObjectProperty<EventHandler<ActionEvent>> onConfirmarActionProperty() {
        return onConfirmarAction;
    }

    public EventHandler<ActionEvent> getOnConfirmarAction() {
        return onConfirmarAction.get();
    }

    public void setOnConfirmarAction(EventHandler<ActionEvent> handler) {
        this.onConfirmarAction.set(handler);
    }
}