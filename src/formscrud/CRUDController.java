package formscrud;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author nicol
 */
public class CRUDController implements Initializable {

    @FXML
    private TextField pessoa_CPF;

    @FXML
    private Button pessoa_DeletarBtn;

    @FXML
    private TextField pessoa_Nacionalidade;

    @FXML
    private TextField pessoa_Nome;

    @FXML
    private ComboBox<?> pessoa_Sexo;

    @FXML
    private Button pessoa_addBtn;

    @FXML
    private TableColumn<pessoaForm, String> pessoa_colunaCPF;

    @FXML
    private TableColumn<pessoaForm, String> pessoa_colunaNacionalidade;

    @FXML
    private TableColumn<pessoaForm, String> pessoa_colunaNome;

    @FXML
    private TableColumn<pessoaForm, String> pessoa_colunaSexo;

    @FXML
    private Button pessoa_editarBtn;

    @FXML
    private Button pessoa_limparBtn;

    @FXML
    private TableView<pessoaForm> pessoa_tableView;
    
     @FXML
    private AnchorPane formPessoaFront;

    @FXML
    private Button formPessoa_btn;

    @FXML
    private AnchorPane formVeiculoFront;

    @FXML
    private Button formVeiculo_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    
    public void pessoaAddBtn() {

        connect = database.connect();

        try {

            if (pessoa_CPF.getText().isEmpty()
                    || pessoa_Nome.getText().isEmpty()
                    || pessoa_Nacionalidade.getText().isEmpty()
                    || pessoa_Sexo.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
            } else {
                String checkData = "SELECT cpf FROM pessoa WHERE cpf = "
                        + pessoa_CPF.getText();

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("O CPF: " + pessoa_CPF.getText() + " já foi adicionado");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO pessoa (cpf, nome, nacionalidade, sexo, date)"
                            + "VALUES(?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, pessoa_CPF.getText());
                    prepare.setString(2, pessoa_Nome.getText());
                    prepare.setString(3, pessoa_Nacionalidade.getText());
                    prepare.setString(4, (String) pessoa_Sexo.getSelectionModel().getSelectedItem());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(5, String.valueOf(sqlDate));

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados adicionados!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    pessoaShowData();

                    pessoaClearBtn();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pessoaUpdateBtn() {

        connect = database.connect();

        try {

            if (pessoa_CPF.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Você tem certeza que deseja editar os dados?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String updateData = "UPDATE pessoa SET "
                            + "nome = ?, "
                            + "nacionalidade = ?, "
                            + "sexo = ? "
                            + "WHERE cpf = ?";

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, pessoa_Nome.getText());
                    prepare.setString(2, pessoa_Nacionalidade.getText());
                    prepare.setString(3, (String) pessoa_Sexo.getSelectionModel().getSelectedItem());
                    prepare.setString(4, pessoa_CPF.getText());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados alterados com sucesso!");
                    alert.showAndWait();

                    pessoaShowData();
                    pessoaClearBtn();
                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelado");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pessoaDeleteBtn() {
        connect = database.connect();

        try {
            if (pessoa_CPF.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe o CPF para excluir o registro.");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Você tem certeza que deseja excluir os dados?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String deleteData = "DELETE FROM pessoa WHERE cpf = ?";

                    prepare = connect.prepareStatement(deleteData);
                    prepare.setString(1, pessoa_CPF.getText());

                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro excluído com sucesso!");
                        alert.showAndWait();

                        pessoaShowData();
                        pessoaClearBtn();
                    } else {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro não encontrado.");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelado");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pessoaClearBtn() {
        pessoa_CPF.setText("");
        pessoa_Nome.setText("");
        pessoa_Nacionalidade.setText("");
        pessoa_Sexo.getSelectionModel().clearSelection();

    }

    private String[] sexoList = {"Feminino", "Masculino"};

    public void pessoaSexoList() {
        List<String> sList = new ArrayList<>();

        for (String data : sexoList) {
            sList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(sList);
        pessoa_Sexo.setItems(listData);
    }

    public ObservableList<pessoaForm> pessoaListData() {

        ObservableList<pessoaForm> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM pessoa";

        connect = database.connect();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            pessoaForm pData;

            while (result.next()) {
                pData = new pessoaForm(result.getInt("cpf"),
                        result.getString("nome"), result.getString("nacionalidade"),
                        result.getString("sexo"));

                listData.add(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<pessoaForm> pessoaForm;

    public void pessoaShowData() {
        pessoaForm = pessoaListData();

        pessoa_colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        pessoa_colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        pessoa_colunaNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        pessoa_colunaSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        pessoa_tableView.setItems(pessoaForm);
    }

    public void pessoaSelectData() {
        pessoaForm pData = pessoa_tableView.getSelectionModel().getSelectedItem();
        int num = pessoa_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        pessoa_CPF.setText(String.valueOf(pData.getCpf()));
        pessoa_Nome.setText(String.valueOf(pData.getNome()));
        pessoa_Nacionalidade.setText(String.valueOf(pData.getNacionalidade()));

    }
    
    public void switchForm(ActionEvent event) {
        if (event.getSource() == formPessoa_btn) {
            formPessoaFront.setVisible(true);
            formVeiculoFront.setVisible(false);
        } else if (event.getSource() == formVeiculo_btn) {
            formPessoaFront.setVisible(false);
            formVeiculoFront.setVisible(true);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pessoaSexoList();

        pessoaShowData();
        
        formPessoa_btn.setOnAction(this::switchForm);
        formVeiculo_btn.setOnAction(this::switchForm);

    }

}
