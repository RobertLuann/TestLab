package com.ufersa.testlab.controller.questoes;

import com.ufersa.testlab.model.entities.Alternativa;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.entities.QuestaoDissertativa;
import com.ufersa.testlab.model.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.model.services.QuestaoService;
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

public class EditarQuestaoController {

    // Campos FXML (iguais aos do CadastroController)
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
    @FXML private Button salvarButton;

    private Questao questaoParaEditar;
    private ToggleGroup alternativasToggleGroup;
    private final QuestaoService questaoService = new QuestaoService();

    @FXML
    public void initialize() {
        alternativasToggleGroup = new ToggleGroup();
    }

    public void initData(Questao questao) {
        this.questaoParaEditar = questao;
        preencherFormulario();
    }

    private void preencherFormulario() {
        codigoField.setText(questaoParaEditar.getCodigo());
        enunciadoArea.setText(questaoParaEditar.getEnunciado());
        disciplinaField.setText(questaoParaEditar.getDisciplina());
        assuntoField.setText(questaoParaEditar.getAssunto());
        dificuldadeSlider.setValue(questaoParaEditar.getDificuldade());

        tipoQuestaoComboBox.setDisable(true);

        if (questaoParaEditar instanceof QuestaoDissertativa qd) {
            tipoQuestaoComboBox.setValue("DISSERTATIVA");
            respostaDissertativaArea.setText(qd.getResposta());
            dissertativaPane.setVisible(true);
            dissertativaPane.setManaged(true);
        } else if (questaoParaEditar instanceof QuestaoMultiplaEscolha qme) {
            tipoQuestaoComboBox.setValue("MULTIPLA_ESCOLHA");
            multiplaEscolhaPane.setVisible(true);
            multiplaEscolhaPane.setManaged(true);

            alternativasContainer.getChildren().clear();
            for (Alternativa alt : qme.getAlternativas()) {
                adicionarAlternativa(alt.getTexto(), alt.getCorreta());
            }
        }
    }

    @FXML
    void handleAdicionarAlternativa(ActionEvent event) {
        adicionarAlternativa("", false);
    }

    private void adicionarAlternativa(String texto, boolean isCorreta) {
        HBox alternativaBox = new HBox(10);
        alternativaBox.setAlignment(Pos.CENTER_LEFT);
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(alternativasToggleGroup);
        radioButton.setSelected(isCorreta);
        TextField textField = new TextField(texto);
        HBox.setHgrow(textField, Priority.ALWAYS);
        Button btnRemover = new Button("X");
        btnRemover.getStyleClass().add("remove-button-small");
        btnRemover.setOnAction(e -> alternativasContainer.getChildren().remove(alternativaBox));
        alternativaBox.getChildren().addAll(radioButton, textField, btnRemover);
        alternativasContainer.getChildren().add(alternativaBox);
    }

    @FXML
    void handleSalvarAlteracoes(ActionEvent event) {
        questaoParaEditar.setEnunciado(enunciadoArea.getText());
        questaoParaEditar.setCodigoDisciplina(disciplinaField.getText());
        questaoParaEditar.setAssunto(assuntoField.getText());
        questaoParaEditar.setDificuldade((long) dificuldadeSlider.getValue());

        if (questaoParaEditar instanceof QuestaoDissertativa qd) {
            qd.setResposta(respostaDissertativaArea.getText());
        } else if (questaoParaEditar instanceof QuestaoMultiplaEscolha qme) {
            List<Alternativa> novasAlternativas = new ArrayList<>();
            long novoGabarito = -1;

            for (int i = 0; i < alternativasContainer.getChildren().size(); i++) {
                HBox hbox = (HBox) alternativasContainer.getChildren().get(i);
                TextField tf = (TextField) hbox.getChildren().get(1);
                RadioButton rb = (RadioButton) hbox.getChildren().get(0);

                Alternativa alt = new Alternativa();
                alt.setTexto(tf.getText());
                alt.setCorreta(rb.isSelected());
                if (rb.isSelected()) {
                    novoGabarito = i;
                }
                novasAlternativas.add(alt);
            }
            qme.setAlternativas(novasAlternativas);
            qme.setGabarito(novoGabarito);
        }

        try {
            if (questaoParaEditar instanceof QuestaoDissertativa qd) {
                questaoService.atualizarQuestaoDissertativa(qd.getCodigo(), qd.getEnunciado(), qd.getDisciplina(), qd.getAssunto(), qd.getDificuldade(), qd.getResposta());
            } else if (questaoParaEditar instanceof QuestaoMultiplaEscolha qme) {
                questaoService.atualizarQuestaoMultiplaEscolha(qme.getCodigo(), qme.getEnunciado(), qme.getDisciplina(), qme.getAssunto(), qme.getDificuldade(), qme.getAlternativas(), qme.getGabarito());
            }

            Stage stage = (Stage) salvarButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}