package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button cadastrarButton;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    void handleCadastroButtonAction(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        try {
            usuarioService.cadastrarUsuario(nome, email, senha, false);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado com sucesso! Você já pode fazer o login.");

            closeWindow();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) cadastrarButton.getScene().getWindow();
        stage.close();
    }
}