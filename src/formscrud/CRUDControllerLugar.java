package formscrud;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author nicol
 */
public class CRUDControllerLugar implements Initializable {
    
    @FXML
    private Button formLugar_btn;

    @FXML
    private Button formlugar_btn;

    @FXML
    private Button formVeiculo_btn;

    @FXML
    private Button formViagem_btn;

    @FXML
    private ComboBox<?> lugar_Acomodacao;

    @FXML
    private TextField lugar_CNPJ;

    @FXML
    private Button lugar_DeletarBtn;

    @FXML
    private TextField lugar_Endereco;

    @FXML
    private TextField lugar_Local;

    @FXML
    private Button lugar_addBtn;

    @FXML
    private TableColumn<lugarForm, String> lugar_colunaAcomodacao;

    @FXML
    private TableColumn<lugarForm, String> lugar_colunaCNPJ;

    @FXML
    private TableColumn<lugarForm, String> lugar_colunaEndereco;

    @FXML
    private TableColumn<lugarForm, String> lugar_colunaLocal;

    @FXML
    private Button lugar_editarBtn;

    @FXML
    private Button lugar_limparBtn;

    @FXML
    private TableView<lugarForm> lugar_tableView;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    public void lugarAddBtn() {

        connect = database.connect();

        try {

            if (lugar_CNPJ.getText().isEmpty()
                    || lugar_Local.getText().isEmpty()
                    || lugar_Endereco.getText().isEmpty()
                    || lugar_Acomodacao.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
            } else {
                String checkData = "SELECT cnpj FROM lugar WHERE cnpj = "
                        + lugar_CNPJ.getText();

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("O CNPJ: " + lugar_CNPJ.getText() + " já foi adicionado");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO lugar (cnpj, local, endereco, acomodacao)"
                            + "VALUES(?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, lugar_CNPJ.getText());
                    prepare.setString(2, lugar_Local.getText());
                    prepare.setString(3, lugar_Endereco.getText());
                    prepare.setString(4, (String) lugar_Acomodacao.getSelectionModel().getSelectedItem());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados adicionados!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    lugarShowData();

                    lugarClearBtn();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void lugarUpdateBtn() {

        connect = database.connect();

        try {

            if (lugar_CNPJ.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Você tem certeza que deseja editar os dados?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String updateData = "UPDATE lugar SET "
                            + "local = ?, "
                            + "endereco = ?, "
                            + "acomodacao = ? "
                            + "WHERE cnpj = ?";

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, lugar_Local.getText());
                    prepare.setString(2, lugar_Endereco.getText());
                    prepare.setString(3, (String) lugar_Acomodacao.getSelectionModel().getSelectedItem());
                    prepare.setString(4, lugar_CNPJ.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados alterados com sucesso!");
                    alert.showAndWait();

                    lugarShowData();
                    lugarClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
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

    public void lugarDeleteBtn() {
        connect = database.connect();

        try {
            if (lugar_CNPJ.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe o CNPJ para excluir o registro.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Você tem certeza que deseja excluir os dados?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String deleteData = "DELETE FROM lugar WHERE cnpj = ?";

                    prepare = connect.prepareStatement(deleteData);
                    prepare.setString(1, lugar_CNPJ.getText());

                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro excluído com sucesso!");
                        alert.showAndWait();

                        lugarShowData();
                        lugarClearBtn();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro não encontrado.");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
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

    public void lugarClearBtn() {
        lugar_CNPJ.setText("");
        lugar_Local.setText("");
        lugar_Endereco.setText("");
        lugar_Acomodacao.getSelectionModel().clearSelection();

    }

    private String[] acomodacaoList = {"Hotel", "Airbnb"};

    public void lugarAcomodacaoList() {
        List<String> aList = new ArrayList<>();

        for (String data : acomodacaoList) {
            aList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(aList);
        lugar_Acomodacao.setItems(listData);
    }

    public ObservableList<lugarForm> lugarListData() {

        ObservableList<lugarForm> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM lugar";

        connect = database.connect();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            lugarForm lData;

            while (result.next()) {
                lData = new lugarForm(result.getInt("cnpj"),
                        result.getString("local"), result.getString("endereço"),
                        result.getString("acomodacao"));

                listData.add(lData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<lugarForm> lugarForm;

    public void lugarShowData() {
        lugarForm = lugarListData();

        lugar_colunaCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        lugar_colunaLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
        lugar_colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        lugar_colunaAcomodacao.setCellValueFactory(new PropertyValueFactory<>("acomodacao"));

        lugar_tableView.setItems(lugarForm);
    }

    public void lugarSelectData() {
        lugarForm lData = lugar_tableView.getSelectionModel().getSelectedItem();
        int num = lugar_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        lugar_CNPJ.setText(String.valueOf(lData.getCnpj()));
        lugar_Local.setText(String.valueOf(lData.getLocal()));
        lugar_Endereco.setText(String.valueOf(lData.getEndereco()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lugarAcomodacaoList();

        lugarShowData();

    }

}
