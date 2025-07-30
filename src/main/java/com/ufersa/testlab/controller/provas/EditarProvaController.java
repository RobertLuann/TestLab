package com.ufersa.testlab.controller.provas;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Questao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class EditarProvaController {

    @FXML private TextField tituloField;
    @FXML private TextField disciplinaField;
    @FXML private ListView<String> questoesListView;
    @FXML private Button salvarButton;

    private ProvaDAO provaDAO = new ProvaDAO();
    private Prova provaParaEditar;

    public void initData(Prova prova) {
        this.provaParaEditar = prova;
        tituloField.setText(prova.getTitulo());
        disciplinaField.setText(prova.getDisciplina().getNome());

        // Preenche a lista com os enunciados das questões
        questoesListView.setItems(FXCollections.observableArrayList(
                prova.getQuestoes().stream().map(Questao::getEnunciado).collect(Collectors.toList())
        ));
    }

    @FXML
    private void handleSalvar() {
        if (tituloField.getText().isBlank()) {
            new Alert(Alert.AlertType.WARNING, "O título não pode ser vazio.").showAndWait();
            return;
        }

        // Atualiza apenas o título
        provaParaEditar.setTitulo(tituloField.getText());
        provaDAO.atualizarProva(provaParaEditar);

        closeWindow();
    }

    private void closeWindow() {
        ((Stage) salvarButton.getScene().getWindow()).close();
    }
}