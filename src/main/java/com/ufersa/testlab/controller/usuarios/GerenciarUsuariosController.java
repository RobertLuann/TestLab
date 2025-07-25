package com.ufersa.testlab.controller.usuarios;

import com.ufersa.testlab.model.entities.Funcionario;
import com.ufersa.testlab.model.entities.Gerente;
import com.ufersa.testlab.model.entities.Usuario;
import com.ufersa.testlab.model.services.UsuarioService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GerenciarUsuariosController {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, String> colunaNome;
    @FXML private TableColumn<Usuario, String> colunaEmail;
    @FXML private TableColumn<Usuario, String> colunaTipo;
    @FXML private TableColumn<Usuario, Void> colunaEditar;
    @FXML private TableColumn<Usuario, Void> colunaRemover;

    private final UsuarioService usuarioService = new UsuarioService();
    private ObservableList<Usuario> usuariosNaTabela;

    @FXML
    public void initialize() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colunaTipo.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            String tipoUsuario;

            if (usuario instanceof Gerente) {
                tipoUsuario = "Gerente";
            } else if (usuario instanceof Funcionario) {
                tipoUsuario = "Funcionário";
            } else {
                tipoUsuario = "N/D"; // Caso haja outros tipos não definidos
            }

            return new SimpleStringProperty(tipoUsuario);
        });

        configurarBotaoEditar();
        configurarBotaoRemover();

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        List<Usuario> usuariosDoBanco = usuarioService.listarUsuarios();
        usuariosNaTabela = FXCollections.observableArrayList(usuariosDoBanco);
        tabelaUsuarios.setItems(usuariosNaTabela);
    }

    private void configurarBotaoEditar() {
        colunaEditar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.getStyleClass().addAll("action-button", "edit-button");
                btnEditar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/usuarios/EditarUsuarioView.fxml"));
                        Parent root = loader.load();

                        EditarUsuarioController controller = loader.getController();
                        controller.initData(usuario);

                        Stage stage = new Stage();
                        stage.setTitle("Editar Usuário");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tabelaUsuarios.getScene().getWindow());
                        stage.showAndWait();

                        carregarUsuarios();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });
    }

    private void configurarBotaoRemover() {
        colunaRemover.setCellFactory(param -> new TableCell<>() {
            private final Button btnRemover = new Button("Remover");

            {
                btnRemover.getStyleClass().addAll("action-button", "remove-button");

                btnRemover.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação de Remoção");
                    alert.setHeaderText("Você tem certeza que deseja remover o usuário?");
                    alert.setContentText(usuario.getNome() + " (" + usuario.getEmail() + ")");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        usuarioService.deletarUsuario(usuario.getId());
                        usuariosNaTabela.remove(usuario);
                        System.out.println("Usuário removido: " + usuario.getNome());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnRemover);
                }
            }
        });
    }

    @FXML
    void handleCadastrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ufersa/testlab/views/usuarios/CadastroView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Novo Usuário");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaUsuarios.getScene().getWindow());
            stage.showAndWait();

            carregarUsuarios();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}