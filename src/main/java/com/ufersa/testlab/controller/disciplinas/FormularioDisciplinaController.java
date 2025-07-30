package com.ufersa.testlab.controller.disciplinas;

import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormularioDisciplinaController {

    @FXML private Label tituloLabel;
    @FXML private TextField codigoField;
    @FXML private TextField nomeField;
    @FXML private ListView<String> assuntosListView;
    @FXML private TextField novoAssuntoField;
    @FXML private Button salvarButton;

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private Disciplina disciplina;
    private final ObservableList<String> assuntosObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        assuntosListView.setItems(assuntosObservableList);
    }

    public void initData(Disciplina disciplina) {
        this.disciplina = disciplina;
        tituloLabel.setText("Editar Disciplina");
        salvarButton.setText("Salvar Alterações");

        codigoField.setText(disciplina.getCodigo());
        codigoField.setDisable(true);
        nomeField.setText(disciplina.getNome());

        assuntosObservableList.setAll(disciplina.getAssuntos());
    }

    @FXML
    private void handleAdicionarAssunto() {
        String nomeAssunto = novoAssuntoField.getText();
        if (nomeAssunto != null && !nomeAssunto.isBlank()) {
            if (!assuntosObservableList.contains(nomeAssunto.trim())) {
                assuntosObservableList.add(nomeAssunto.trim());
            }
            novoAssuntoField.clear();
        }
    }

    @FXML
    private void handleSalvar() {
        boolean isNew = (this.disciplina == null);

        try {
            if (isNew) {
                // Validação para garantir que o código não está em branco no cadastro
                if (codigoField.getText().isBlank()) {
                    new Alert(Alert.AlertType.WARNING, "O campo 'Código' é obrigatório.").showAndWait();
                    return;
                }
                this.disciplina = new Disciplina();
                this.disciplina.setCodigo(codigoField.getText());
            }

            if (nomeField.getText().isBlank()) {
                new Alert(Alert.AlertType.WARNING, "O campo 'Nome' é obrigatório.").showAndWait();
                return;
            }
            this.disciplina.setNome(nomeField.getText());

            this.disciplina.getAssuntos().clear();
            this.disciplina.getAssuntos().addAll(assuntosObservableList);

            if (isNew) {
                disciplinaDAO.cadastrarDisciplina(this.disciplina);
            } else {
                disciplinaDAO.atualizarDisciplina(this.disciplina);
            }

            closeWindow();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).showAndWait();
        }
    }

    private void closeWindow() {
        ((Stage) salvarButton.getScene().getWindow()).close();
    }
}