package com.ufersa.testlab;

import com.ufersa.testlab.seeder.DBSeeder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/ufersa/testlab/views/LoginView.fxml")));

        primaryStage.setTitle("Teste JavaFX");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Seeder
        System.out.println("Iniciando a aplicação. Executando o DBSeeder...");
        DBSeeder seeder = new DBSeeder();
        seeder.run();
        // JavaFX
        launch(args);
    }
}