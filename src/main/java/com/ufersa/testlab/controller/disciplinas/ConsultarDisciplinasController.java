package com.ufersa.testlab.controller.disciplinas;

import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultarDisciplinasController {

    @FXML private TableView<Disciplina> tabelaDisciplinas;
    @FXML private TableColumn<Disciplina, String> colunaCodigo;
    @FXML private TableColumn<Disciplina, String> colunaNome;
    @FXML private TableColumn<Disciplina, String> colunaAssuntos;

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @FXML
    public void initialize() {
        // Configura as colunas para exibir os dados da disciplina
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Concatena a lista de assuntos para exibição em uma única string
        colunaAssuntos.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.join(", ", cellData.getValue().getAssuntos()))
        );

        // Carrega as disciplinas do banco de dados na tabela
        carregarDisciplinas();
    }

    /**
     * Busca a lista de disciplinas no banco de dados e a exibe na tabela.
     */
    private void carregarDisciplinas() {
        tabelaDisciplinas.setItems(FXCollections.observableArrayList(disciplinaDAO.listarDisciplinas()));
    }
}