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

    // --- Componentes FXML ---
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

    // --- DAOs ---
    private DisciplinaDAO disciplinaDAO;
    private QuestaoDAO questaoDAO;
    private ProvaDAO provaDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.disciplinaDAO = new DisciplinaDAO();
        this.questaoDAO = new QuestaoDAO();
        this.provaDAO = new ProvaDAO();

        this.spinners = List.of(dificuldade1Spinner, dificuldade2Spinner, dificuldade3Spinner, dificuldade4Spinner, dificuldade5Spinner);

        // ===== CORREÇÃO PRINCIPAL AQUI =====
        // Agora chamamos o método com o nome que existe no seu DAO: "listarDisciplinas()"
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
        // --- Esta parte inicial, de obter os dados da tela, permanece a mesma ---
        String titulo = tituloTextField.getText();
        Disciplina disciplina = disciplinaComboBox.getValue();

        if (titulo.isBlank() || disciplina == null) {
            exibirAlerta("Erro", "Título e Disciplina são obrigatórios.");
            return;
        }

        // --- A lógica para buscar questões aleatórias também permanece a mesma ---
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

        // --- INÍCIO DAS MODIFICAÇÕES E LÓGICA FINAL ---

        // 1. Informa o usuário sobre questões faltantes, mas continua o processo
        if (!erros.isEmpty()) {
            exibirAlerta("Aviso de Questões Faltantes", erros.toString());
        }

        // 2. Para a execução se, no final, nenhuma questão foi selecionada
        if (questoesDaProva.isEmpty()) {
            exibirAlerta("Erro", "Nenhuma questão foi encontrada com os critérios. A prova não pode ser gerada.");
            return;
        }

        Collections.shuffle(questoesDaProva);

        // 3. Cria o objeto Prova com os dados atualizados
        Prova novaProva = new Prova(titulo, questoesDaProva, disciplina);

        // 4. LÓGICA PRINCIPAL: Decide se vai ATUALIZAR ou CADASTRAR
        if (provaParaEditar != null) {
            // Se 'provaParaEditar' existe, estamos no modo de edição.
            // Definimos o ID da prova original para que o JPA saiba qual registro atualizar.
            novaProva.setId(provaParaEditar.getId());
            provaDAO.atualizarProva(novaProva);
            exibirAlerta("Sucesso", "Prova '" + novaProva.getTitulo() + "' atualizada com sucesso!");
        } else {
            // Se 'provaParaEditar' for nulo, estamos criando uma prova nova.
            provaDAO.cadastrarProva(novaProva);
            exibirAlerta("Sucesso", "Prova '" + novaProva.getTitulo() + "' gerada com sucesso!");
        }

        fecharJanela(); // Fecha a janela modal
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

        // 1. Preenche os campos de Título e Disciplina (como já fazia)
        tituloTextField.setText(prova.getTitulo());
        disciplinaComboBox.setValue(prova.getDisciplina());

        // Opcional: Manter a disciplina desabilitada para evitar inconsistências na edição
        disciplinaComboBox.setDisable(true);

        // 2. Nova Lógica: Contar as questões existentes por dificuldade
        //    Cria um mapa para armazenar a contagem: Dificuldade -> Quantidade
        Map<Long, Integer> contagemPorDificuldade = new HashMap<>();
        for (Questao q : prova.getQuestoes()) {
            long dificuldade = q.getDificuldade();
            // O método 'merge' incrementa o contador para a dificuldade encontrada
            contagemPorDificuldade.merge(dificuldade, 1, Integer::sum);
        }

        // 3. Preenche os Spinners com os valores que acabamos de contar
        //    Este loop assume que sua lista 'spinners' está ordenada (spinner de dificuldade 1, 2, 3...)
        for (int i = 0; i < spinners.size(); i++) {
            long dificuldadeAtual = i + 1L;

            // Pega a contagem do mapa. Se não houver questões daquela dificuldade, usa 0.
            int quantidade = contagemPorDificuldade.getOrDefault(dificuldadeAtual, 0);

            // Define o valor no Spinner correspondente
            spinners.get(i).getValueFactory().setValue(quantidade);
        }
    }
}