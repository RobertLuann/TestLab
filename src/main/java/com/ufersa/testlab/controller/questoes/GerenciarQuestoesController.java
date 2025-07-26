package com.ufersa.testlab.controller.questoes;

import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.model.entities.QuestaoDissertativa;
import com.ufersa.testlab.model.services.QuestaoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GerenciarQuestoesController {

    @FXML private TableView<Questao> tabelaQuestoes;
    @FXML private TableColumn<Questao, String> colunaEnunciado;
    @FXML private TableColumn<Questao, String> colunaTipo;
    @FXML private TableColumn<Questao, Void> colunaEditar;
    @FXML private TableColumn<Questao, Void> colunaRemover;

    private final QuestaoService questaoService = new QuestaoService();

    @FXML
    public void initialize() {
        colunaEnunciado.setCellValueFactory(new PropertyValueFactory<>("enunciado"));

        // Lógica para exibir "Dissertativa" ou "Optativa"
        colunaTipo.setCellValueFactory(cellData -> {
            Questao questao = cellData.getValue();
            String tipo = (questao instanceof QuestaoMultiplaEscolha) ? "Múltipla Escolha" : "Dissertativa";
            return new SimpleStringProperty(tipo);
        });

        carregarQuestoes();
        // configurarBotaoEditar(); // Faremos a seguir
        // configurarBotaoRemover(); // Faremos a seguir
    }

    private void carregarQuestoes() {
        List<Questao> questoes = questaoService.listarQuestoes();
        tabelaQuestoes.setItems(FXCollections.observableArrayList(questoes));
    }

    @FXML
    private void handleCadastrar() {
        try {
            Parent cadastroRoot = FXMLLoader.load(getClass().getResource("/com/ufersa/testlab/views/questoes/CadastroQuestaoView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Nova Questão");
            stage.setScene(new Scene(cadastroRoot));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaQuestoes.getScene().getWindow());
            stage.showAndWait();

            // Atualiza a tabela após o cadastro
            carregarQuestoes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}