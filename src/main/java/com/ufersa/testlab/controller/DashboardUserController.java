package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardUserController {

    @FXML private StackPane contentArea;
    @FXML private Label usuarioLabel;

    public void initData(Usuario usuario) {
        usuarioLabel.setText("Usuário: " + usuario.getNome());
    }

    @FXML
    private void handleEditarProvas() {
        System.out.println("Carregando tela de Edição de Provas...");
        // loadView("/com/ufersa/testlab/views/provas/EditarProvasView.fxml");
    }

    @FXML
    private void handleVerQuestoes() {
        System.out.println("Carregando tela de Visualização de Questões...");
        // loadView("/com/ufersa/testlab/views/questoes/VerQuestoesView.fxml");
    }

    @FXML
    private void handleGerarProva() {
        System.out.println("Botão 'Gerar Prova' clicado.");
        // Adicionar lógica para gerar prova, talvez abrindo uma nova janela (Stage)
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