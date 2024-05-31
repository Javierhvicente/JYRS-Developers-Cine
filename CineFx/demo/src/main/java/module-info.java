module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires kotlin.result.jvm;
    requires logging.jvm;
    requires kotlinx.serialization.core;


    requires sqlite.driver;
    requires runtime.jvm;
    requires java.sql;
    requires kotlinx.serialization.json;
    requires koin.core.jvm;
    requires open;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;

    opens org.example.demo.routes to javafx.fxml;
    exports org.example.demo.routes;

    opens org.example.demo.view.controllers to javafx.fxml;
    exports org.example.demo.view.controllers;

    opens org.example.demo.usuarios.viewModel to javafx.fxml;
    exports org.example.demo.usuarios.viewModel;

    opens org.example.demo.usuarios.models to javafx.fxml;
    exports org.example.demo.usuarios.models;

    opens org.example.demo.usuarios.services to javafx.fxml;
    exports org.example.demo.usuarios.services;

    opens org.example.demo.usuarios.repositories to javafx.fxml;
    exports org.example.demo.usuarios.repositories;

    opens org.example.demo.productos.models to javafx.fxml;
    exports org.example.demo.productos.models;

    opens org.example.demo.productos.butaca.services to javafx.fxml;
    exports org.example.demo.productos.butaca.services;

    opens org.example.demo.productos.complementos.services to javafx.fxml;
    exports org.example.demo.productos.complementos.services;

    opens org.example.demo.venta.models to javafx.fxml;
    exports org.example.demo.venta.models;

    opens org.example.demo.venta.services to javafx.fxml;
    exports org.example.demo.venta.services;

    opens org.example.demo.storage to javafx.fxml;
    exports org.example.demo.storage;


}