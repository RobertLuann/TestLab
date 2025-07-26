package com.ufersa.testlab.controller.questoes;

import com.ufersa.testlab.model.entities.Alternativa;
import com.ufersa.testlab.model.services.QuestaoService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CadastroQuestaoController {

    // Campos FXML
    @FXML private ComboBox<String> tipoQuestaoComboBox;
    @FXML private TextField codigoField;
    @FXML private TextArea enunciadoArea;
    @FXML private TextField disciplinaField;
    @FXML private TextField assuntoField;
    @FXML private Slider dificuldadeSlider;
    @FXML private VBox dissertativaPane;
    @FXML private TextArea respostaDissertativaArea;
    @FXML private VBox multiplaEscolhaPane;
    @FXML private VBox alternativasContainer;

    private ToggleGroup alternativasToggleGroup;
    private QuestaoService questaoService = new QuestaoService();

    @FXML
    public void initialize() {
        // Inicializa o ComboBox de seleção de tipo
        tipoQuestaoComboBox.setItems(FXCollections.observableArrayList("DISSERTATIVA", "MULTIPLA_ESCOLHA"));
        alternativasToggleGroup = new ToggleGroup();

        // Adiciona um listener para mostrar/esconder seções
        tipoQuestaoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            atualizarVisibilidadeFormulario(newValue);
        });

        // Estado inicial
        atualizarVisibilidadeFormulario(null);
    }

    private void atualizarVisibilidadeFormulario(String tipoSelecionado) {
        boolean dissertativaVisivel = "DISSERTATIVA".equals(tipoSelecionado);
        boolean multiplaEscolhaVisivel = "MULTIPLA_ESCOLHA".equals(tipoSelecionado);

        dissertativaPane.setVisible(dissertativaVisivel);
        dissertativaPane.setManaged(dissertativaVisivel); // 'managed' remove o espaço do elemento quando invisível

        multiplaEscolhaPane.setVisible(multiplaEscolhaVisivel);
        multiplaEscolhaPane.setManaged(multiplaEscolhaVisivel);
    }

    @FXML
    void handleAdicionarAlternativa(ActionEvent event) {
        HBox alternativaBox = new HBox(10);
        alternativaBox.setAlignment(Pos.CENTER_LEFT);

        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(alternativasToggleGroup);

        TextField textField = new TextField();
        HBox.setHgrow(textField, Priority.ALWAYS); // Faz o textfield crescer

        Button btnRemover = new Button("X");
        btnRemover.getStyleClass().add("remove-button-small");
        btnRemover.setOnAction(e -> alternativasContainer.getChildren().remove(alternativaBox));

        // Guarda a referência ao radio button no próprio textfield para uso posterior
        textField.setUserData(radioButton);

        alternativaBox.getChildren().addAll(radioButton, textField, btnRemover);
        alternativasContainer.getChildren().add(alternativaBox);
    }

    @FXML
    void handleSalvarQuestao(ActionEvent event) {
        // Validações básicas (pode adicionar mais)
        String tipo = tipoQuestaoComboBox.getValue();
        if (tipo == null) {
            // mostrar alerta
            return;
        }

        String codigo = codigoField.getText();
        String enunciado = enunciadoArea.getText();
        String disciplina = disciplinaField.getText();
        String assunto = assuntoField.getText();
        Long dificuldade = (long) dificuldadeSlider.getValue();

        try {
            if ("DISSERTATIVA".equals(tipo)) {
                String resposta = respostaDissertativaArea.getText();
                questaoService.cadastrarQuestaoDissertativa(codigo, enunciado, disciplina, assunto, dificuldade, resposta);
            } else if ("MULTIPLA_ESCOLHA".equals(tipo)) {
                List<Alternativa> alternativas = new ArrayList<>();
                long gabaritoIndex = -1;

                // Itera sobre os HBox dentro do container de alternativas
                for (int i = 0; i < alternativasContainer.getChildren().size(); i++) {
                    HBox hbox = (HBox) alternativasContainer.getChildren().get(i);
                    TextField tf = (TextField) hbox.getChildren().get(1); // O TextField é o segundo elemento
                    RadioButton rb = (RadioButton) hbox.getChildren().get(0); // O RadioButton é o primeiro

                    Alternativa alt = new Alternativa();
                    alt.setTexto(tf.getText());
                    alt.setCorreta(rb.isSelected());

                    if (rb.isSelected()) {
                        gabaritoIndex = i;
                    }
                    alternativas.add(alt);
                }

                if (gabaritoIndex == -1) {
                    // mostrar alerta de que precisa selecionar uma alternativa correta
                    return;
                }

                questaoService.cadastrarQuestaoMultiplaEscolha(codigo, enunciado, disciplina, assunto, dificuldade, alternativas, gabaritoIndex);
            }

            // Mostrar alerta de sucesso e fechar a janela
            Stage stage = (Stage) codigoField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            // Mostrar alerta de erro
            e.printStackTrace();
        }
    }
}