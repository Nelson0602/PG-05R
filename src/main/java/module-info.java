module pg05r {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;

    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    opens testing to javafx.fxml;

    exports model;
    exports controller;
    exports util;
    exports model.LinkedList;
    opens model.LinkedList to javafx.fxml;
    exports model.queue;
    opens model.queue to javafx.fxml;
    exports testing;
}