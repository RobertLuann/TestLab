package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.dao.QuestaoDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Questao;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class GerarProvaController implements Initializable {

    @FXML private TextField tituloTextField;
    @FXML private ComboBox<Disciplina> disciplinaComboBox;
    @FXML private Label totalQuestoesLabel;
    @FXML private Spinner<Integer> dificuldade1Spinner;
    @FXML private Spinner<Integer> dificuldade2Spinner;
    @FXML private Spinner<Integer> dificuldade3Spinner;
    @FXML private Spinner<Integer> dificuldade4Spinner;
    @FXML private Spinner<Integer> dificuldade5Spinner;
    private List<Spinner<Integer>> spinners;
    private Prova provaParaEditar;

    private DisciplinaDAO disciplinaDAO;
    private QuestaoDAO questaoDAO;
    private ProvaDAO provaDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.disciplinaDAO = new DisciplinaDAO();
        this.questaoDAO = new QuestaoDAO();
        this.provaDAO = new ProvaDAO();

        this.spinners = List.of(dificuldade1Spinner, dificuldade2Spinner, dificuldade3Spinner, dificuldade4Spinner, dificuldade5Spinner);

        disciplinaComboBox.setItems(FXCollections.observableArrayList(disciplinaDAO.listarDisciplinas()));

        configurarDisplayDisciplina();
        configurarSpinners();
    }

    private void configurarDisplayDisciplina() {
        Callback<ListView<Disciplina>, ListCell<Disciplina>> cellFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Disciplina disciplina, boolean empty) {
                super.updateItem(disciplina, empty);
                setText(empty ? "" : disciplina.getNome());
            }
        };

        disciplinaComboBox.setCellFactory(cellFactory);
        disciplinaComboBox.setButtonCell(cellFactory.call(null));
    }

    private void configurarSpinners() {
        ChangeListener<Integer> totalListener = (obs, oldValue, newValue) -> atualizarTotalQuestoes();
        for (Spinner<Integer> spinner : spinners) {
            SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
            spinner.setValueFactory(factory);
            spinner.valueProperty().addListener(totalListener);
        }
        atualizarTotalQuestoes();
    }

    private void atualizarTotalQuestoes() {
        int total = spinners.stream().mapToInt(Spinner::getValue).sum();
        totalQuestoesLabel.setText(String.valueOf(total));
    }

    @FXML
    void handleGerarProvaAction(ActionEvent event) {
        String titulo = tituloTextField.getText();
        Disciplina disciplina = disciplinaComboBox.getValue();

        if (titulo.isBlank() || disciplina == null) {
            exibirAlerta("Erro", "Título e Disciplina são obrigatórios.");
            return;
        }

        List<Questao> questoesDaProva = new ArrayList<>();
        StringBuilder erros = new StringBuilder();

        for (int i = 0; i < spinners.size(); i++) {
            int dificuldade = i + 1;
            int quantidadeDesejada = spinners.get(i).getValue();

            if (quantidadeDesejada > 0) {
                List<Questao> encontradas = questaoDAO.buscarQuestoesAleatorias(disciplina, dificuldade, quantidadeDesejada);
                if (encontradas.size() < quantidadeDesejada) {
                    erros.append("Dificuldade ").append(dificuldade).append(": encontradas apenas ")
                            .append(encontradas.size()).append(" de ").append(quantidadeDesejada).append(".\n");
                }
                questoesDaProva.addAll(encontradas);
            }
        }

        if (!erros.isEmpty()) {
            exibirAlerta("Aviso de Questões Faltantes", erros.toString());
        }

        if (questoesDaProva.isEmpty()) {
            exibirAlerta("Erro", "Nenhuma questão foi encontrada com os critérios. A prova não pode ser gerada.");
            return;
        }

        Collections.shuffle(questoesDaProva);

        Prova novaProva = new Prova(titulo, questoesDaProva, disciplina);

        if (provaParaEditar != null) {
            novaProva.setId(provaParaEditar.getId());
            provaDAO.atualizarProva(novaProva);
            exibirAlerta("Sucesso", "Prova '" + novaProva.getTitulo() + "' atualizada com sucesso!");
        } else {
            provaDAO.cadastrarProva(novaProva);
            exibirAlerta("Sucesso", "Prova '" + novaProva.getTitulo() + "' gerada com sucesso!");
        }

        fecharJanela();
    }

    @FXML
    void handleCancelarAction(ActionEvent event) {
        fecharJanela();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela() {
        if (totalQuestoesLabel != null && totalQuestoesLabel.getScene() != null) {
            Stage stage = (Stage) totalQuestoesLabel.getScene().getWindow();
            stage.close();
        }
    }

    public void initData(Prova prova) {
        this.provaParaEditar = prova;

        tituloTextField.setText(prova.getTitulo());
        disciplinaComboBox.setValue(prova.getDisciplina());

        disciplinaComboBox.setDisable(true);

        Map<Long, Integer> contagemPorDificuldade = new HashMap<>();
        for (Questao q : prova.getQuestoes()) {
            long dificuldade = q.getDificuldade();
            contagemPorDificuldade.merge(dificuldade, 1, Integer::sum);
        }

        for (int i = 0; i < spinners.size(); i++) {
            long dificuldadeAtual = i + 1L;

            int quantidade = contagemPorDificuldade.getOrDefault(dificuldadeAtual, 0);

            spinners.get(i).getValueFactory().setValue(quantidade);
        }
    }
}