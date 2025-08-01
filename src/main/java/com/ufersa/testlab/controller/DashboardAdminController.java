package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.entities.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.ufersa.testlab.controller.provas.GerenciarProvasController;

import java.io.IOException;

public class DashboardAdminController {

    GerenciarProvasController g = new GerenciarProvasController();
    @FXML private StackPane contentArea;
    @FXML private Label usuarioLabel;
    public void initData(Usuario usuario) {
        usuarioLabel.setText("Usuário: " + usuario.getNome());
    }

    public void initialize() {
        handleGerenciarProvas();
    }

    @FXML
    private void handleGerenciarProvas() {
        System.out.println("Admin: Carregando tela de Edição de Provas...");
        loadView("/com/ufersa/testlab/views/provas/GerenciarProvasView.fxml");
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/GerarProvaView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gerar Nova Prova");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        g.carregarProvas();
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
    @FXML
    private void handleVoltarAction(ActionEvent event) {
        try {
            // 1. Fecha a janela atual (o Dashboard)
            // Pega o Stage (janela) a partir do evento do botão que foi clicado
            Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAtual.close();

            // 2. Carrega e exibe a nova janela de Login
            // ATENÇÃO: Verifique se o caminho para o seu LoginView.fxml está correto!
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/LoginView.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("TestLab - Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            System.err.println("Erro ao tentar voltar para a tela de login.");
            e.printStackTrace();
            // Opcional: Mostrar um alerta de erro para o usuário
            new Alert(Alert.AlertType.ERROR, "Não foi possível carregar a tela de login.").showAndWait();
        }
    }
}