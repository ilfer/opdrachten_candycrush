module be.kuleuven.opdracht6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens be.kuleuven.opdracht6 to javafx.fxml;
    exports be.kuleuven.opdracht6;
}