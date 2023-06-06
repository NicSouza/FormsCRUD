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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author nicol
 */
public class CRUDControllerVeiculo implements Initializable{
    
    @FXML
    private Button formLugar_btn;

    @FXML
    private Button formPessoa_btn;

    @FXML
    private Button formVeiculo_btn;

    @FXML
    private Button formViagem_btn;

    @FXML
    private TextField veiculo_Cor;

    @FXML
    private Button veiculo_DeletarBtn;

    @FXML
    private TextField veiculo_Modelo;

    @FXML
    private TextField veiculo_Placa;

    @FXML
    private ComboBox<?> veiculo_Tipo;

    @FXML
    private Button veiculo_addBtn;

    @FXML
    private TableColumn<veiculoForm, String> veiculo_colunaCor;

    @FXML
    private TableColumn<veiculoForm, String> veiculo_colunaModelo;

    @FXML
    private TableColumn<veiculoForm, Integer> veiculo_colunaPlaca;

    @FXML
    private TableColumn<veiculoForm, String> veiculo_colunaTipo;

    @FXML
    private Button veiculo_editarBtn;

    @FXML
    private Button veiculo_limparBtn;

    @FXML
    private TableView<veiculoForm> veiculo_tableView;
        
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    public void veiculoAddBtn() {

        connect = database.connect();

        try {

            if (veiculo_Placa.getText().isEmpty()
                    || veiculo_Tipo.getSelectionModel().getSelectedItem() == null
                    || veiculo_Modelo.getText().isEmpty()
                    || veiculo_Cor.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
            } else {
                String checkData = "SELECT placa FROM veiculo WHERE placa = "
                        + veiculo_Placa.getText();

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("A placa: " + veiculo_Placa.getText() + " já foi adicionado");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO veiculo (placa, tipo, modelo, cor)"
                            + "VALUES(?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, veiculo_Placa.getText());
                    prepare.setString(2, (String) veiculo_Tipo.getSelectionModel().getSelectedItem());
                    prepare.setString(3, veiculo_Modelo.getText());
                    prepare.setString(4, veiculo_Cor.getText());


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados adicionados!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    veiculoShowData();

                    veiculoClearBtn();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void veiculoUpdateBtn() {

        connect = database.connect();

        try {

            if (veiculo_Placa.getText().isEmpty()) {
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
                    String updateData = "UPDATE veiculo SET "
                            + "tipo = ?, "
                            + "modelo = ?, "
                            + "cor = ? "
                            + "WHERE placa = ?";

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, (String) veiculo_Tipo.getSelectionModel().getSelectedItem());
                    prepare.setString(2, veiculo_Modelo.getText());
                    prepare.setString(3, veiculo_Cor.getText());
                    prepare.setString(4, veiculo_Placa.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Dados alterados com sucesso!");
                    alert.showAndWait();

                    veiculoShowData();
                    veiculoClearBtn();
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

    public void veiculoDeleteBtn() {
        connect = database.connect();

        try {
            if (veiculo_Placa.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe a placa para excluir o registro.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Você tem certeza que deseja excluir os dados?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String deleteData = "DELETE FROM veiculo WHERE placa = ?";

                    prepare = connect.prepareStatement(deleteData);
                    prepare.setString(1, veiculo_Placa.getText());

                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro excluído com sucesso!");
                        alert.showAndWait();

                        veiculoShowData();
                        veiculoClearBtn();
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

    public void veiculoClearBtn() {
        veiculo_Placa.setText("");
        veiculo_Tipo.getSelectionModel().clearSelection();
        veiculo_Modelo.setText("");
        veiculo_Cor.setText("");

    }

    private String[] tipoList = {"Carro", "Moto", "Avião"};

    public void veiculoTipoList() {
        List<String> tList = new ArrayList<>();

        for (String data : tipoList) {
            tList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(tList);
        veiculo_Tipo.setItems(listData);
    }

    public ObservableList<veiculoForm> veiculoListData() {

        ObservableList<veiculoForm> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM veiculo";

        connect = database.connect();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            veiculoForm vData;

            while (result.next()) {
                vData = new veiculoForm(result.getInt("placa"),
                        result.getString("tipo"), result.getString("modelo"),
                        result.getString("cor"));

                listData.add(vData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<veiculoForm> veiculoForm;

    public void veiculoShowData() {
        veiculoForm = veiculoListData();

        veiculo_colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        veiculo_colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        veiculo_colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        veiculo_colunaCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        veiculo_tableView.setItems(veiculoForm);
    }

    public void veiculoSelectData() {
        veiculoForm vData = veiculo_tableView.getSelectionModel().getSelectedItem();
        int num = veiculo_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        veiculo_Placa.setText(String.valueOf(vData.getPlaca()));
        veiculo_Modelo.setText(String.valueOf(vData.getModelo()));
        veiculo_Cor.setText(String.valueOf(vData.getCor()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        veiculoTipoList();

        veiculoShowData();

    }
}
