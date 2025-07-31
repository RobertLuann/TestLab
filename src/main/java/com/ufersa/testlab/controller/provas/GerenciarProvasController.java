package com.ufersa.testlab.controller.provas;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.services.GeradorProvaPdfService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GerenciarProvasController {

    @FXML private TableView<Prova> tabelaProvas;
    @FXML private TableColumn<Prova, String> colunaTitulo;
    @FXML private TableColumn<Prova, String> colunaDisciplina;
    @FXML private TableColumn<Prova, Integer> colunaQtdQuestoes;
    @FXML private TableColumn<Prova, String> colunaSemestre;
    @FXML private TableColumn<Prova, Void> colunaAcoes;

    private ProvaDAO provaDAO = new ProvaDAO();

    @FXML
    public void initialize() {
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestreCriacao"));

        colunaDisciplina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDisciplina().getNome())
        );
        colunaQtdQuestoes.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuestoes().size()).asObject()
        );

        colunaTitulo.prefWidthProperty().bind(tabelaProvas.widthProperty().multiply(0.35)); // 35%
        colunaDisciplina.prefWidthProperty().bind(tabelaProvas.widthProperty().multiply(0.25)); // 25%
        colunaQtdQuestoes.prefWidthProperty().bind(tabelaProvas.widthProperty().multiply(0.10)); // 10%
        colunaSemestre.prefWidthProperty().bind(tabelaProvas.widthProperty().multiply(0.10)); // 10%
        colunaAcoes.prefWidthProperty().bind(tabelaProvas.widthProperty().multiply(0.20)); // 20% para as ações

        configurarColunaAcoes();
        carregarProvas();
    }

    public void carregarProvas() {
        tabelaProvas.setItems(FXCollections.observableArrayList(provaDAO.listarTodas()));
    }

    private void configurarColunaAcoes() {
        colunaAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final Button btnPdf = new Button("PDF");
            private final HBox pane = new HBox(5, btnEditar, btnExcluir, btnPdf);

            {
                btnEditar.getStyleClass().addAll("action-button", "edit-button");
                btnExcluir.getStyleClass().addAll("action-button", "remove-button");
                btnPdf.getStyleClass().addAll("action-button", "pdf-button");


                btnEditar.setOnAction(event -> {
                    Prova prova = getTableView().getItems().get(getIndex());
                    abrirJanelaDeEdicao(prova);
                });
                btnExcluir.setOnAction(event -> handleExcluir(getTableView().getItems().get(getIndex())));
                btnPdf.setOnAction(event -> handleGerarPdf(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void abrirJanelaDeEdicao(Prova prova) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/provas/EditarProvaView.fxml"));
            Parent root = loader.load();
            EditarProvaController controller = loader.getController();
            controller.initData(prova);

            Stage stage = new Stage();
            stage.setTitle("Editar Prova");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaProvas.getScene().getWindow());
            stage.showAndWait();
            carregarProvas();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void handleExcluir(Prova prova) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Remover a prova '" + prova.getTitulo() + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            provaDAO.deletarProva(prova);
            carregarProvas();
        }
    }

    private void handleGerarPdf(Prova prova) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar PDF da Prova");
        fileChooser.setInitialFileName(prova.getTitulo().replaceAll("\\s+", "_") + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(tabelaProvas.getScene().getWindow());

        if (file != null) {
            try {
                GeradorProvaPdfService gerador = new GeradorProvaPdfService();
                gerador.gerarProva(prova, file.toString());
                new Alert(Alert.AlertType.INFORMATION, "PDF gerado com sucesso!").showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao gerar o PDF.").showAndWait();
            }
        }
    }
}