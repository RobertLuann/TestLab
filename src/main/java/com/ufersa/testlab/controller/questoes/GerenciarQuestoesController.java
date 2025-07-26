package com.ufersa.testlab.controller.questoes;

import com.ufersa.testlab.model.entities.Alternativa;
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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    @FXML private VBox detailsPane;

    private final QuestaoService questaoService = new QuestaoService();

    @FXML
    public void initialize() {
        colunaEnunciado.setCellValueFactory(new PropertyValueFactory<>("enunciado"));

        colunaTipo.setCellValueFactory(cellData -> {
            Questao questao = cellData.getValue();
            String tipo = (questao instanceof QuestaoMultiplaEscolha) ? "Múltipla Escolha" : "Dissertativa";
            return new SimpleStringProperty(tipo);
        });

        tabelaQuestoes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showQuestaoDetails(newValue)
        );

        colunaEnunciado.prefWidthProperty().bind(tabelaQuestoes.widthProperty().multiply(0.60));
        colunaTipo.prefWidthProperty().bind(tabelaQuestoes.widthProperty().multiply(0.15));
        colunaEditar.prefWidthProperty().bind(tabelaQuestoes.widthProperty().multiply(0.12));
        colunaRemover.prefWidthProperty().bind(tabelaQuestoes.widthProperty().multiply(0.12));

        carregarQuestoes();

        carregarQuestoes();
        // configurarBotaoEditar();
        // configurarBotaoRemover();
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

            carregarQuestoes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showQuestaoDetails(Questao questao) {
        detailsPane.getChildren().clear();

        String cssPath = getClass().getResource("/com/ufersa/testlab/styles/detalhes-questao-style.css").toExternalForm();
        detailsPane.getStylesheets().add(cssPath);

        if (questao != null) {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(8);

            Label keyCodigo = new Label("Código:");
            keyCodigo.getStyleClass().add("details-key");
            grid.add(keyCodigo, 0, 0);
            grid.add(new Label(questao.getCodigo()), 1, 0);

            Label keyDisciplina = new Label("Disciplina:");
            keyDisciplina.getStyleClass().add("details-key");
            grid.add(keyDisciplina, 0, 1);
            grid.add(new Label(questao.getDisciplina()), 1, 1);

            Label keyAssunto = new Label("Assunto:");
            keyAssunto.getStyleClass().add("details-key");
            grid.add(keyAssunto, 0, 2);
            grid.add(new Label(questao.getAssunto()), 1, 2);

            Label keyDificuldade = new Label("Dificuldade:");
            keyDificuldade.getStyleClass().add("details-key");
            grid.add(keyDificuldade, 0, 3);
            grid.add(new Label(String.valueOf(questao.getDificuldade())), 1, 3);

            detailsPane.getChildren().add(grid);

            if (questao instanceof QuestaoDissertativa) {
                QuestaoDissertativa qd = (QuestaoDissertativa) questao;

                Label subheader = new Label("Gabarito");
                subheader.getStyleClass().add("details-subheader");

                Label resposta = new Label(qd.getResposta());
                resposta.setWrapText(true);

                detailsPane.getChildren().addAll(new Separator(), subheader, resposta);

            } else if (questao instanceof QuestaoMultiplaEscolha qme) {

                Label subheader = new Label("Alternativas");
                subheader.getStyleClass().add("details-subheader");

                VBox alternativasVBox = new VBox(5);

                for (int i = 0; i < qme.getAlternativas().size(); i++) {
                    Alternativa alt = qme.getAlternativas().get(i);
                    String prefixo = (char) ('A' + i) + ") ";
                    Label altLabel = new Label(prefixo + alt.getTexto());

                    if (alt.getCorreta()) {
                        altLabel.getStyleClass().add("correct-alternative");
                    }
                    alternativasVBox.getChildren().add(altLabel);
                }

                detailsPane.getChildren().addAll(new Separator(), subheader, alternativasVBox);
            }
        } else {
            Label prompt = new Label("Selecione uma questão na tabela para ver os detalhes.");
            prompt.getStyleClass().add("details-prompt");
            detailsPane.getChildren().add(prompt);
        }
    }
}