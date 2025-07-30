package com.ufersa.testlab.controller;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Usuario;
// A importação do 'GeradorProvaPdfService' foi removida.
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private Usuario usuarioLogado;
    private ProvaDAO provaDAO;

    private List<Prova> listaCompletaDeProvas = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.provaDAO = new ProvaDAO();

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
                    private final HBox pane = new HBox(10, btnEditar, btnExcluir);

                    {
                        // Define a ação do botão "Editar"
                        btnEditar.setOnAction(event -> {
                            // Pega o objeto 'Prova' da linha em que o botão foi clicado
                            Prova prova = getTableView().getItems().get(getIndex());
                            abrirJanelaDeEdicao(prova);
                        });

                        // Define a ação do botão "Excluir"
                        btnExcluir.setOnAction(event -> {
                            Prova prova = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmação de Exclusão");
                            alert.setHeaderText("Tem certeza que deseja excluir a prova?");
                            alert.setContentText("Prova: " + prova.getTitulo());

                            if (alert.showAndWait().get() == ButtonType.OK) {
                                provaDAO.deletarProva(prova);
                                carregarProvas(); // Recarrega a tabela para mostrar a mudança
                            }
                        });
                    }

                    // Este método é crucial: ele desenha os botões na célula
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            // Se a linha da tabela estiver vazia, não mostra nada
                            setGraphic(null);
                        } else {
                            // Se a linha tiver uma prova, mostra o painel com os botões
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        // Aplica a "fábrica de células" à coluna de ações
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
}