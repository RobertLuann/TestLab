package com.ufersa.testlab.controller.usuarios;

import com.ufersa.testlab.model.entities.Usuario;
import com.ufersa.testlab.model.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarUsuarioController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button salvarButton;

    private final UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioParaEditar;

    public void initData(Usuario usuario) {
        this.usuarioParaEditar = usuario;
        nomeField.setText(usuario.getNome());
        emailField.setText(usuario.getEmail());
    }

    @FXML
    void handleSalvarButtonAction(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String novaSenha = senhaField.getText();

        if (nome.isBlank() || email.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Erro de Validação", "Nome e Email são obrigatórios.");
            return;
        }

        usuarioParaEditar.setNome(nome);
        usuarioParaEditar.setEmail(email);

        if (!novaSenha.isBlank()) {
            if (novaSenha.length() < 6) {
                showAlert(Alert.AlertType.WARNING, "Erro de Validação", "A nova senha deve ter no mínimo 6 caracteres.");
                return;
            }
            usuarioParaEditar.setSenha(novaSenha);
        }

        try {
            usuarioService.atualizarUsuario(usuarioParaEditar.getId(), usuarioParaEditar.getNome(), usuarioParaEditar.getEmail(), usuarioParaEditar.getSenha());
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário atualizado com sucesso!");
            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Atualizar", e.getMessage());
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
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}