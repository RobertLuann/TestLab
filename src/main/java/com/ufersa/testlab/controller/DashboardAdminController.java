package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import com.ufersa.testlab.model.services.UsuarioService;

import java.io.IOException;

public class DashboardAdminController {

    @FXML private StackPane contentArea;
    @FXML private Label usuarioLabel;
    /**
     * Recebe os dados do usuário (Admin) que fez login.
     */
    public void initData(Usuario usuario) {
        usuarioLabel.setText("Usuário: " + usuario.getNome());
    }

    @FXML
    private void handleEditarProvas() {
        System.out.println("Admin: Carregando tela de Edição de Provas...");
        // loadView("/com/ufersa/testlab/views/provas/AdminProvasView.fxml");
    }

    @FXML
    private void handleEditarDisciplinas() {
        System.out.println("Admin: Carregando tela de Edição de Disciplinas...");
        // loadView("/com/ufersa/testlab/views/disciplinas/AdminDisciplinasView.fxml");
    }

    @FXML
    private void handleEditarQuestoes() {
        System.out.println("Admin: Carregando tela de Edição de Questões...");
        // loadView("/com/ufersa/testlab/views/questoes/AdminQuestoesView.fxml");
    }

    @FXML
    private void handleGerarProva() {
        System.out.println("Admin: Botão 'Gerar Prova' clicado.");
    }

    @FXML
    private void handleGerenciarUsuarios() {
        System.out.println("Admin: Carregando tela de Gerenciamento de Usuários...");
        loadView("/com/ufersa/testlab/views/GerenciarUsuariosView.fxml");
    }

    @FXML
    private void handleEditarUsuario() {
        System.out.println("Admin: Carregando tela de Edição de Usuário...");
        // loadView("/com/ufersa/testlab/views/usuarios/EdicaoUsuarioView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}