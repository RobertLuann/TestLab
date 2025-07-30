package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardAdminController {

    @FXML private StackPane contentArea;
    @FXML private Label usuarioLabel;
    public void initData(Usuario usuario) {
        usuarioLabel.setText("Usuário: " + usuario.getNome());
    }

    @FXML
    private void handleGerenciarProvas() {
        System.out.println("Admin: Carregando tela de Edição de Provas...");
        // loadView("/com/ufersa/testlab/views/provas/AdminProvasView.fxml");
    }

    @FXML
    private void handleGerenciarDisciplinas() {
        System.out.println("Admin: Carregando tela de Edição de Disciplinas...");
        loadView("/com/ufersa/testlab/views/disciplinas/GerenciarDisciplinasView.fxml");
    }

    @FXML
    private void handleGerenciarQuestoes() {
        System.out.println("Admin: Carregando tela de Gerenciar Questões...");
        loadView("/com/ufersa/testlab/views/questoes/GerenciarQuestoesView.fxml");
    }

    @FXML
    private void handleGerarProva() {
        System.out.println("Admin: Botão 'Gerar Prova' clicado.");
    }

    @FXML
    private void handleGerenciarUsuarios() {
        System.out.println("Admin: Carregando tela de Gerenciamento de Usuários...");
        loadView("/com/ufersa/testlab/views/usuarios/GerenciarUsuariosView.fxml");
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