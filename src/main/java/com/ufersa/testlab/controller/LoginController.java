package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.services.UsuarioService;
import com.ufersa.testlab.model.entities.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink cadastroLink;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String email = usernameField.getText();
        String senha = passwordField.getText();

        if (email.isBlank() || senha.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Campos Vazios", "Por favor, preencha o usuário e a senha.");
            return;
        }

        Usuario usuarioAutenticado = usuarioService.autenticar(email, senha);

        if (usuarioAutenticado != null) {
            System.out.println("Login bem-sucedido para: " + usuarioAutenticado.getNome());
            navigateToDashboard(event, usuarioAutenticado);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro de Login", "Email ou senha inválidos.");
        }
    }

    @FXML
    void handleCadastroLinkAction(ActionEvent event) throws IOException {
        Parent cadastroRoot = FXMLLoader.load(getClass().getResource("/com/ufersa/testlab/views/CadastroView.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Novo Usuário");
        stage.setScene(new Scene(cadastroRoot));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cadastroLink.getScene().getWindow());

        stage.showAndWait();
    }

    private void navigateToDashboard(ActionEvent event, Usuario usuarioAutenticado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/DashboardView.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController controller = loader.getController();

            controller.initData(usuarioAutenticado);

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(dashboardRoot));
            currentStage.setTitle("Dashboard - TestLab");
            currentStage.setResizable(true);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro de Navegação", "Não foi possível carregar o dashboard.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}