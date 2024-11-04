module com.example.cronometromejoradoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cronometromejorado to javafx.fxml;
    exports com.example.cronometromejorado;
}