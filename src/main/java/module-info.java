module org.example.pg03r {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens controller to javafx.fxml;
    opens model to javafx.fxml;

    exports model;
    exports controller;
    exports util;
    exports model.LinkedList;
    opens model.LinkedList to javafx.fxml;
    exports model.queue;
    opens model.queue to javafx.fxml;
}