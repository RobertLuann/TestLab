package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Usuario;
// A importação do 'GeradorProvaPdfService' foi removida.
import com.ufersa.testlab.model.services.GeradorProvaPdfService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.BorderPane;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardUserController implements Initializable {

    @FXML private Label usuarioLabel;
    @FXML private TextField searchBar;
    @FXML private TableView<Prova> provasTableView;
    @FXML private TableColumn<Prova, String> tituloColumn;
    @FXML private TableColumn<Prova, String> qtdQuestoesColumn;
    @FXML private TableColumn<Prova, String> disciplinaColumn;
    @FXML private TableColumn<Prova, String> dataCriacaoColumn;
    @FXML private TableColumn<Prova, Void> acoesColumn;
    @FXML private BorderPane mainPane;
    @FXML private VBox contentArea;

    private Usuario usuarioLogado;
    private ProvaDAO provaDAO;
    private List<Node> telaInicialNodes; // "Memória" da tela inicial para evitar loops
    private boolean isTabelaInicializada = false;
    private List<Prova> listaCompletaDeProvas = new ArrayList<>();
    private List<Node> telaInicialMinhasProvas;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.provaDAO = new ProvaDAO();
        this.telaInicialMinhasProvas = new ArrayList<>(contentArea.getChildren());
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        qtdQuestoesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuestoes().size())));
        disciplinaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisciplina().getNome()));
        dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("semestreCriacao"));

        configurarColunaAcoes();
        carregarProvas();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabela(newValue);
        });
    }

    private void carregarProvas() {
        try {
            this.listaCompletaDeProvas = provaDAO.listarTodas();
            provasTableView.setItems(FXCollections.observableArrayList(this.listaCompletaDeProvas));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filtrarTabela(String textoBusca) {
        if (textoBusca == null || textoBusca.isEmpty()) {
            provasTableView.setItems(FXCollections.observableArrayList(this.listaCompletaDeProvas));
            return;
        }

        String filtroLowerCase = textoBusca.toLowerCase();

        List<Prova> listaFiltrada = this.listaCompletaDeProvas.stream()
                .filter(prova -> {
                    boolean disciplinaMatch = prova.getDisciplina().getNome().toLowerCase().contains(filtroLowerCase);
                    boolean semestreMatch = prova.getSemestreCriacao().toLowerCase().contains(filtroLowerCase);
                    return disciplinaMatch || semestreMatch;
                })
                .collect(Collectors.toList());

        provasTableView.setItems(FXCollections.observableArrayList(listaFiltrada));
    }

    private void configurarColunaAcoes() {
        Callback<TableColumn<Prova, Void>, TableCell<Prova, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Prova, Void> call(final TableColumn<Prova, Void> param) {
                final TableCell<Prova, Void> cell = new TableCell<>() {

                    private final Button btnEditar = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");

                    // 1. CRIAMOS O NOVO BOTÃO AQUI
                    private final Button btnPdf = new Button("Gerar PDF");

                    // 2. ADICIONAMOS O BOTÃO AO PAINEL QUE OS ORGANIZA
                    private final HBox pane = new HBox(10, btnEditar, btnExcluir, btnPdf);

                    {
                        // Ação do botão Editar (código que você já tem)
                        btnEditar.setOnAction(event -> {
                            Prova prova = getTableView().getItems().get(getIndex());
                            abrirJanelaDeEdicao(prova);
                        });

                        // Ação do botão Excluir (código que você já tem)
                        btnExcluir.setOnAction(event -> {
                            Prova prova = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmação de Exclusão");
                            alert.setHeaderText("Tem certeza que deseja excluir a prova?");
                            alert.setContentText("Prova: " + prova.getTitulo());

                            if (alert.showAndWait().get() == ButtonType.OK) {
                                provaDAO.deletarProva(prova);
                                carregarProvas(); // Recarrega a tabela
                            }
                        });

                        // 3. ADICIONAMOS A LÓGICA PARA O NOVO BOTÃO "GERAR PDF"
                        btnPdf.setOnAction(event -> {
                            Prova provaSelecionada = getTableView().getItems().get(getIndex());

                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Salvar PDF da Prova");
                            fileChooser.setInitialFileName(provaSelecionada.getTitulo().replaceAll("\\s+", "_") + ".pdf");
                            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                            File file = fileChooser.showSaveDialog(new Stage());

                            if (file != null) {
                                try {
                                    // ANTES, a chamada era assim:
                                    // GeradorProvaPdfService gerador = new GeradorProvaPdfService();
                                    // gerador.gerarProva(provaSelecionada, file.getAbsolutePath());

                                    // AGORA, a chamada passa o usuário logado e é estática (mais limpa):
                                    GeradorProvaPdfService gerador = new GeradorProvaPdfService();
                                    gerador.gerarProva(provaSelecionada, file.getAbsolutePath());

                                    new Alert(Alert.AlertType.INFORMATION, "PDF da prova gerado com sucesso!").showAndWait();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    new Alert(Alert.AlertType.ERROR, "Erro ao gerar o PDF da prova.").showAndWait();
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        acoesColumn.setCellFactory(cellFactory);
    }
    private void abrirJanelaDeEdicao(Prova prova) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/GerarProvaView.fxml"));
            Parent root = loader.load();

            GerarProvaController controller = loader.getController();
            controller.initData(prova);

            Stage stage = new Stage();
            stage.setTitle("Editar Prova");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            carregarProvas();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            usuarioLabel.setText("Usuário: " + usuario.getNome());
        } else {
            usuarioLabel.setText("Usuário: Não identificado");
        }
    }

    @FXML
    void handleEditarProvas(ActionEvent event) {
        System.out.println("Botão 'Ver Disciplinas' foi clicado!");
    }

    @FXML
    void handleVerQuestoes(ActionEvent event) {
        System.out.println("Botão 'Ver Questões' foi clicado!");
    }

    @FXML
    void handleCriarNovaProva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/GerarProvaView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gerar Nova Prova");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            carregarProvas();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGerarPdfAction(ActionEvent event) {
        Prova provaSelecionada = provasTableView.getSelectionModel().getSelectedItem();
        if (provaSelecionada == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione uma prova na tabela.").showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar PDF da Prova");
        fileChooser.setInitialFileName(provaSelecionada.getTitulo().replaceAll("\\s+", "_") + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                GeradorProvaPdfService gerador = new GeradorProvaPdfService();
                gerador.gerarProva(provaSelecionada, file.getAbsolutePath());

                new Alert(Alert.AlertType.INFORMATION, "PDF da prova gerado com sucesso!").showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao gerar o PDF da prova.").showAndWait();
            }
        }
    }

    @FXML
    private void handleConsultarQuestoes() {
        try {
            // Carrega a view de questões
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/questoes/ConsultarQuestoesView.fxml")); // Verifique este caminho
            Parent questoesRoot = loader.load();

            // **Padrão correto: Limpa a área de conteúdo e adiciona a nova tela**
            contentArea.getChildren().clear();
            contentArea.getChildren().add(questoesRoot);

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de consulta de questões:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltarAction(ActionEvent event) {
        try {
            // 1. Fecha a janela atual (o Dashboard)
            Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAtual.close();

            // 2. Carrega e exibe a nova janela de Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/LoginView.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("TestLab - Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            System.err.println("Erro ao tentar voltar para a tela de login.");
            e.printStackTrace();
            // Opcional: Mostrar um alerta de erro para o usuário
            new Alert(Alert.AlertType.ERROR, "Não foi possível carregar a tela de login.").showAndWait();
        }
    }

    @FXML
    private void handleConsultarDisciplinas() {
        try {
            // 1. Carrega o arquivo FXML da tela de consulta de disciplinas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/disciplinas/ConsultarDisciplinasView.fxml"));
            Parent consultarDisciplinasRoot = loader.load();

            // 2. Limpa o conteúdo atual da área principal
            contentArea.getChildren().clear();

            // 3. Adiciona a nova tela (de consulta) na área principal
            contentArea.getChildren().add(consultarDisciplinasRoot);

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de consulta de disciplinas:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMinhasProvas() {
        // PASSO 2: RESTAURAR A TELA
        // Simplesmente coloca os componentes originais de volta no contentArea.
        // Rápido, eficiente e sem loops.
        contentArea.getChildren().setAll(telaInicialMinhasProvas);
    }

    private void inicializarTabelaMinhasProvas() {
        // Esta verificação garante que a configuração ocorra apenas uma vez.
        if (isTabelaInicializada) {
            return;
        }

        // Garante que a tabela não seja nula antes de tentar usá-la.
        if (provasTableView != null) {
            // 1. Configurar as colunas
            // ATENÇÃO: Os nomes "titulo", etc., devem corresponder aos atributos da sua classe Prova
            tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            // ... configure as outras colunas aqui ...

            // 2. Carregar os dados do banco de dados
            // ProvaService provaService = new ProvaService();
            // List<Prova> provasDoBanco = provaService.listarTodas();
            // provasTableView.setItems(FXCollections.observableArrayList(provasDoBanco));

            System.out.println("Tabela de provas configurada e dados carregados.");

            isTabelaInicializada = true; // Marca a tabela como inicializada
        } else {
            System.err.println("ERRO CRÍTICO: 'provasTableView' é nulo. Verifique o fx:id no seu arquivo FXML.");
        }
    }

}