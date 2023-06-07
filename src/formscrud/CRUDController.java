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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author nicol
 */
public class CRUDController implements Initializable {

    @FXML
    private DatePicker viagem_Chegada;

    @FXML
    private Button viagem_DeletarBtn;

    @FXML
    private TextField viagem_Destino;

    @FXML
    private DatePicker viagem_Ida;

    @FXML
    private TextField viagem_Origem;

    @FXML
    private TextField viagem_Passagem;

    @FXML
    private Button viagem_addBtn;

    @FXML
    private TableColumn<viagemForm, String> viagem_colunaChegada;

    @FXML
    private TableColumn<viagemForm, String> viagem_colunaDestino;

    @FXML
    private TableColumn<viagemForm, String> viagem_colunaIda;

    @FXML
    private TableColumn<viagemForm, String> viagem_colunaOrigem;

    @FXML
    private TableColumn<viagemForm, String> viagem_colunaPassagem;

    @FXML
    private Button viagem_editarBtn;

    @FXML
    private Button viagem_limparBtn;

    @FXML
    private TableView<viagemForm> viagem_tableView;
    
    @FXML
    private AnchorPane ViagemFrontForm;
    
    @FXML
    private AnchorPane LugarFrontForm;

    @FXML
    private AnchorPane PessoaFrontForm;

    @FXML
    private AnchorPane VeiculoFrontForm;
    
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
    private Button formPessoa_btn;

    @FXML
    private Button formVeiculo_btn;
    
    @FXML
    private Button formLugar_btn;

    @FXML
    private Button formlugar_btn;

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

    //PESSOA
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
    
    // VEICULO
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

    private String[] tipoList = {"Ônibus", "Avião"};

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
    
    // LUGAR
    
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
                        result.getString("local"), result.getString("endereco"),
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

    // VIAGEM

    //TROCA DE FORMS
   public void switchForm(ActionEvent event){
        
        if(event.getSource() == formPessoa_btn){
            PessoaFrontForm.setVisible(true);
            VeiculoFrontForm.setVisible(false);
            LugarFrontForm.setVisible(false);
            ViagemFrontForm.setVisible(false);
        
        }else if(event.getSource() == formVeiculo_btn){
            PessoaFrontForm.setVisible(false);
            VeiculoFrontForm.setVisible(true);
            LugarFrontForm.setVisible(false);
            ViagemFrontForm.setVisible(false);
        }else if(event.getSource() == formLugar_btn){
            PessoaFrontForm.setVisible(false);
            VeiculoFrontForm.setVisible(false);
            LugarFrontForm.setVisible(true); 
            ViagemFrontForm.setVisible(false);
        }else if(event.getSource() == formViagem_btn){
            PessoaFrontForm.setVisible(false);
            VeiculoFrontForm.setVisible(false);
            LugarFrontForm.setVisible(false); 
            ViagemFrontForm.setVisible(true);
        }
    }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pessoaSexoList();

        pessoaShowData();
        
        veiculoTipoList();

        veiculoShowData();
        
        lugarAcomodacaoList();

        lugarShowData();
        

    }

}
