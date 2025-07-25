package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.services.UsuarioService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private ComboBox<String> adminComboBox; // Campo novo
    @FXML private Button cadastrarButton;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        adminComboBox.setItems(FXCollections.observableArrayList("Não", "Sim"));
        adminComboBox.setValue("Não"); // Define "Não" como padrão
    }

    @FXML
    void handleCadastroButtonAction(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        boolean tipoUsuario;
        // Pega o valor do ComboBox
        tipoUsuario = !adminComboBox.getValue().equals("Não");

        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        try {
            // Chama o serviço com o novo parâmetro
            usuarioService.cadastrarUsuario(nome, email, senha, tipoUsuario);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado com sucesso!");
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
        // Pega a janela (Stage) atual a partir de qualquer componente dentro dela, como o botão
        Stage stage = (Stage) cadastrarButton.getScene().getWindow();
        stage.close();
    }
}