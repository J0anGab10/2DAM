module com.elbuengusto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.elbuengusto to javafx.fxml;
    opens com.elbuengusto.componentes to javafx.fxml;

    exports com.elbuengusto;
    exports com.elbuengusto.componentes;
}