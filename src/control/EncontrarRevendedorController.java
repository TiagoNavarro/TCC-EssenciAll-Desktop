package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Produto;
import model.Revende;
import model.Usuario;
import model.jdbc.RevendeDAO;
import model.jdbc.UsuarioDAO;

public class EncontrarRevendedorController implements Initializable {

    @FXML
    private ImageView imgFundo;

    @FXML
    private ImageView imgPesquisa;

    @FXML
    private ImageView imgPerfilRevendedor;

    @FXML
    private ImageView imgRevendedores;

    @FXML
    private TableColumn<Usuario, String> clNome;

    @FXML
    private JFXTextField textPesquisa;

    @FXML
    private ImageView imgCart;

    @FXML
    private ImageView imgConta1;

    @FXML
    private ImageView imgLogout;

    @FXML
    private JFXComboBox<String> cbMarcas;

    @FXML
    private TableView<Usuario> tbRevendedor;
    
    @FXML
    private JFXButton btnSair;
    
    @FXML
    private JFXButton btnRevendedores;
    
    @FXML
    private Label labelNome;
    
    @FXML
    private JFXButton btnConta;
    
    @FXML
    private JFXButton btnPedido;
    
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    
    private void iniciaImagem() {
        imgFundo.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\perfumecapa.jpg"));
        imgPesquisa.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\pesquisa.png"));
        imgPerfilRevendedor.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\usuario\\" + usuarios.get(Usuario.getUsuarioLogado()).getLogin()));
        imgConta1.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\ContaBranco.png"));
        imgLogout.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\sairBranco.png"));
        imgRevendedores.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\revendedoresBranco.png"));
        imgCart.setImage(new Image("file:///" + System.getProperty("user.dir") + "\\src\\imagens\\PedidosBranco.png"));
    }
    
    private void acaoBotoes() {
        btnSair.setOnMouseEntered(event -> {
            imgLogout.setScaleX(1.1);
            imgLogout.setScaleY(1.1);
        });
        btnSair.setOnMouseExited(event -> {
            imgLogout.setScaleX(1.0);
            imgLogout.setScaleY(1.0);
        });
        btnSair.setTooltip(new Tooltip("Sair"));
        btnRevendedores.setOnMouseEntered(event -> {
            imgRevendedores.setScaleX(1.1);
            imgRevendedores.setScaleY(1.1);
        });
        btnRevendedores.setOnMouseExited(event -> {
            imgRevendedores.setScaleX(1.0);
            imgRevendedores.setScaleY(1.0);
        });
        btnRevendedores.setTooltip(new Tooltip("Revendedores"));
        btnConta.setOnMouseEntered(event -> {
            imgConta1.setScaleX(1.1);
            imgConta1.setScaleY(1.1);
        });
        btnConta.setOnMouseExited(event -> {
            imgConta1.setScaleX(1.0);
            imgConta1.setScaleY(1.0);
        });
        btnConta.setTooltip(new Tooltip("Editar Perfil"));
        btnPedido.setOnMouseEntered(event -> {
            imgCart.setScaleX(1.1);
            imgCart.setScaleY(1.1);
        });
        btnPedido.setOnMouseExited(event -> {
            imgCart.setScaleX(1.0);
            imgCart.setScaleY(1.0);
        });
        btnPedido.setTooltip(new Tooltip("Pedidos"));
    }
    
    private void iniciaComboBox() {
        ObservableList<String> marcas = FXCollections.observableArrayList();
        marcas.add("Eudora");
        marcas.add("Hinode");
        marcas.add("Jequiti");
        marcas.add("Laqua");
        marcas.add("Mary Kay");
        marcas.add("Natura");
        marcas.add("Boticario");
        marcas.add("UP");
        marcas.add("Avon");
        cbMarcas.setItems(marcas);
    }
    
    private void iniciaTabela() {
        clNome.setCellValueFactory(new PropertyValueFactory("login"));
    }
    
    private void atualizaComboBox() {
        cbMarcas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                RevendeDAO revendeDAO = new RevendeDAO();
                usuarios = usuarioDAO.selectUsuario();
                ObservableList<Revende> revende = revendeDAO.selectRevende();
                ObservableList<Usuario> usuariosSelecionados = FXCollections.observableArrayList();
                switch(newValue) {
                    case "Eudora":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isEudora()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Hinode":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isHinode()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Jequiti":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isJequiti()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Laqua":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isLaqua()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Mary Kay":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isMary()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Natura":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isNatura()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Boticario":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isBoticario()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "UP":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isUp()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    case "Avon":
                        for (int i = 0; i < revende.size(); i++) {
                            if (revende.get(i).isAvon()) {
                                for (int j = 0; j < usuarios.size(); j++) {
                                    if (usuarios.get(j).getId_usuario() == revende.get(i).getId_usuario()) {
                                        usuariosSelecionados.add(usuarios.get(j));
                                    }
                                }
                            }
                        }
                        tbRevendedor.setItems(usuariosSelecionados);
                        tbRevendedor.refresh();
                    break;
                    default:
                        System.out.println("Erro no switch");
                }
            }
            
        });
    }
    
    private void pesquisaTabela(){
        textPesquisa.setOnKeyReleased(event -> {
            //atualizaTabela();
            ObservableList<Usuario> novoUsuarios = FXCollections.observableArrayList();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getLogin().toLowerCase().contains(textPesquisa.getText().toLowerCase())) {
                    novoUsuarios.add(usuarios.get(i));
                }
            }
            tbRevendedor.setItems(novoUsuarios);
        });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarios = usuarioDAO.selectUsuario();
        iniciaImagem();
        acaoBotoes();
        iniciaTabela();
        iniciaComboBox();
        atualizaComboBox();
        labelNome.setText(usuarios.get(Usuario.getUsuarioLogado()).getLogin());
        labelNome.autosize();
        tbRevendedor.setPlaceholder(new Label("Não há revendedores"));
    }
    
}
