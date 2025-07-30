package com.ufersa.testlab.controller.disciplinas;

import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class GerenciarDisciplinasController {

    @FXML private TableView<Disciplina> tabelaDisciplinas;
    @FXML private TableColumn<Disciplina, String> colunaCodigo;
    @FXML private TableColumn<Disciplina, String> colunaNome;
    @FXML private TableColumn<Disciplina, String> colunaAssuntos;
    @FXML private TableColumn<Disciplina, Void> colunaEditar;
    @FXML private TableColumn<Disciplina, Void> colunaRemover;

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @FXML
    public void initialize() {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Concatena a lista de assuntos para exibição
        colunaAssuntos.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.join(", ", cellData.getValue().getAssuntos()))
        );

        configurarBotaoEditar();
        configurarBotaoRemover();
        carregarDisciplinas();
    }

    private void carregarDisciplinas() {
        tabelaDisciplinas.setItems(FXCollections.observableArrayList(disciplinaDAO.listarDisciplinas()));
    }

    @FXML
    private void handleCadastrar() {
        abrirFormularioDisciplina(null);
    }

    private void configurarBotaoEditar() {
        colunaEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            {
                btnEditar.getStyleClass().addAll("action-button", "edit-button");
                btnEditar.setOnAction(event -> {
                    Disciplina disciplina = getTableView().getItems().get(getIndex());
                    abrirFormularioDisciplina(disciplina);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEditar);
            }
        });
    }

    private void configurarBotaoRemover() {
        colunaRemover.setCellFactory(param -> new TableCell<>() {
            private final Button btnRemover = new Button("Remover");
            {
                btnRemover.getStyleClass().addAll("action-button", "remove-button");
                btnRemover.setOnAction(event -> {
                    Disciplina disciplina = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação");
                    alert.setHeaderText("Remover '" + disciplina.getNome() + "'?");
                    alert.setContentText("Esta ação não pode ser desfeita.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        disciplinaDAO.deletarDisciplina(disciplina);
                        carregarDisciplinas();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemover);
            }
        });
    }

    private void abrirFormularioDisciplina(Disciplina disciplina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/disciplinas/FormularioDisciplinaView.fxml"));
            Parent root = loader.load();

            FormularioDisciplinaController controller = loader.getController();
            if (disciplina != null) {
                controller.initData(disciplina);

            }

            Stage stage = new Stage();
            stage.setTitle(disciplina == null ? "Cadastrar Disciplina" : "Editar Disciplina");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaDisciplinas.getScene().getWindow());
            stage.showAndWait();

            carregarDisciplinas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}