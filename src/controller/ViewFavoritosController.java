package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controller.exibicao.IconeFavoritoController;
import database_connection.DBConexao;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import votebem_Main.Cadastro;
import votebem_Main.Login;

public class ViewFavoritosController implements Initializable {

    //<editor-fold desc="ATRIBUTOS @FXML">
    @FXML
    private Label lblVoltar;

    @FXML
    private TilePane areaIconesFavoritos;

    @FXML
    private JFXButton btnCriarConta, btnEntrar;

    @FXML
    private AnchorPane pnlSemFavoritos, pnlErroLogin;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgSearch, imgVoltar;

    @FXML
    private ScrollPane pnlFavoritos;

    @FXML
    private StackPane areaExibicao;

    Parent paginaPerfil;
    ArrayList<Node> iconesFavoritos = new ArrayList<>();

    Image imgVoltarPreechido = new Image("/modelo/icones/Back_Arrow_Preech_40px.png");
    Image imgVoltarDefault = new Image("/modelo/icones/Back_Arrow_Transp_40px.png");

    //ATRIBUTOS DE CONEXÃO COM O BANCO 
    private static Connection conexao = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    //</editor-fold>
    //
    //Instancia desta classe
    private static ViewFavoritosController instance;

    public ViewFavoritosController() {
        instance = this;
    }

    public static ViewFavoritosController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verificarLogin();

        lblVoltar.setVisible(false);
        imgVoltar.setOnMouseEntered(event -> {
            lblVoltar.setVisible(true);
            imgVoltar.setImage(imgVoltarPreechido);
        });
        imgVoltar.setOnMouseExited(event -> {
            lblVoltar.setVisible(false);
            imgVoltar.setImage(imgVoltarDefault);
        });
    }

    public void verificarLogin() {
        //this.login = ViewPrincipalController.getInstance().getLogin();
        if ((ViewPrincipalController.getInstance().getLogin() == true)
                && (ViewPrincipalController.getInstance().getSeguindo() != false)) {
            pnlFavoritos.toFront();
        } else if ((ViewPrincipalController.getInstance().getLogin() == true)
                && (ViewPrincipalController.getInstance().getSeguindo() == false)) {
            pnlSemFavoritos.toFront();
        } else {
            pnlErroLogin.toFront();
            ViewPrincipalController.getInstance().setSeguindo(false);
            areaIconesFavoritos.getChildren().clear();
            iconesFavoritos.clear();
        }
    }

    public void adicionarIconesFilhos(Node icone, boolean otherClass) {
        if (otherClass == true) {
            salvarFavoritoBD(icone.getId());
        }

        iconesFavoritos.add(icone);
        System.out.println("ID icone Favorito: " + icone.getId());

        areaIconesFavoritos.getChildren().add(icone);
        pnlFavoritos.toFront();
        pnlFavoritos.setVisible(true);

        ViewPrincipalController.getInstance().setSeguindo(true);
        System.out.println("Adicionou um \"Icone de Favorito\" como Filho!");
    }

    public void removerFavorito(String idFavorito) {
        for (Node iconeFav : iconesFavoritos) {
            //Node icone = iconesFavoritos.get(i);

            if (iconeFav.getId().equals(idFavorito)) {
                exluirFavoritoBD(iconeFav.getId());

                areaIconesFavoritos.getChildren().remove(iconeFav);
                iconesFavoritos.remove(iconeFav);

                break;
            }
        }
        if (iconesFavoritos.isEmpty()) {
            ViewPrincipalController.getInstance().setSeguindo(false);
            pnlSemFavoritos.toFront();
        }
    }

    //AÇÕES NO BANCO
    //Adiciona um "Favorito" no Banco
    private void salvarFavoritoBD(String idPol) {

        conexao = DBConexao.getConexao();

        System.out.println("Usuario: " + ViewPrincipalController.getInstance().getIdUser());
        System.out.println("Político: " + idPol);

        int idUser = ViewPrincipalController.getInstance().getIdUser();
        int idPolitico = Integer.parseInt(idPol);

        try {
            stmt = conexao.prepareStatement("INSERT INTO vote_bem.favoritos (fk_usuario, fk_politico) VALUES (?,?);");
            stmt.setInt(1, idUser);
            stmt.setInt(2, idPolitico);

            stmt.execute();

        } catch (SQLException e) {
            System.err.println("Não foi possível inserir um Favorito no banco!");
            System.err.println("ERRO: " + e);
        }

    }

    //Consulta no Banco os "Favoritos"
    public void consultaFavoritoBD() {
        conexao = DBConexao.getConexao();
        int idUser = ViewPrincipalController.getInstance().getIdUser();
        Node iconeFavorito;

        try {
            String sql = "SELECT favoritos.fk_politico, politico.nome, politico.n_politico, cargo.nome, partido.sigla FROM favoritos "
                    + "JOIN usuario on favoritos.fk_usuario = usuario.pk_usuario "
                    + "JOIN politico on favoritos.fk_politico = politico.pk_politico "
                    + "JOIN cargo on politico.fk_cargo = cargo.pk_cargo "
                    + "JOIN partido on politico.fk_partido = partido.pk_partido "
                    + "WHERE favoritos.fk_usuario = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);

            rs = stmt.executeQuery();

            while (rs.next()) {
                try {
                    //-> Segue para o método "Initialize" da classe "IconeFavoritoController"
                    iconeFavorito = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconeFavorito.fxml"));
                    System.out.println("Criou um \"Icone de Favorito\"!");

                    //Seta um id do Icone
                    iconeFavorito.setId("" + rs.getInt("fk_politico"));

                    IconeFavoritoController.getInstance().setDados(rs.getInt("fk_politico"));

                    adicionarIconesFilhos(iconeFavorito, false);
                } catch (IOException e) {
                    System.err.println("Não foi possível carregar um \"Icone Favorito\"");
                    System.err.println("ERRO: " + e);
                }
            }

        } catch (SQLException e) {
            System.err.println("Não foi possível consultar um Favorito no banco!");
            System.err.println("ERRO: " + e);
        }
    }

    //Exclui um "Favorito" do Banco
    private void exluirFavoritoBD(String idPol) {
        conexao = DBConexao.getConexao();

        System.out.println("Usuario: " + ViewPrincipalController.getInstance().getIdUser());
        System.out.println("Político: " + idPol);

        int idUser = ViewPrincipalController.getInstance().getIdUser();
        int idPolitico = Integer.parseInt(idPol);

        try {
            stmt = conexao.prepareStatement("DELETE FROM vote_bem.favoritos WHERE fk_usuario=? and fk_politico=?;");
            stmt.setInt(1, idUser);
            stmt.setInt(2, idPolitico);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Não foi possível remover um Favorito no banco!");
            System.err.println("ERRO: " + e);
        }
    }

    public boolean verificarFavorito(String idFavorito) {
        //Node icone = null;
        boolean contain = false;

        for (Node iconeFav : iconesFavoritos) {
            if (iconeFav.getId().equals(idFavorito)) {
                //icone = iconeFav;
                contain = iconesFavoritos.contains(iconeFav);
                break;
            }
        }
        return contain;
    }

    @FXML
    private void btnLoginAction(ActionEvent event) {
        //if (login == false) {
        Login log = new Login();//Cria Login

        Effect gaussian = new GaussianBlur(6);
        ViewPrincipalController.getInstance().setEffectDisable(gaussian, true); //Aplica efeito

        //Principal.getPrograma().close(); //Fecha tela Principal
        //fecha();//Fecha tela Principal
        try {
            log.start(new Stage());//Abre tela Login
        } catch (Exception ex) {
            //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Não foi possível abrir a \"Tela de Login\"!");
            System.err.println("Erro: " + ex);
        }
        //}
    }

    @FXML
    private void btnCriaContaAction(ActionEvent event) {
        //if (login == false) {
        Cadastro cad = new Cadastro();//Cria Login

        Effect gaussian = new GaussianBlur(6);
        ViewPrincipalController.getInstance().setEffectDisable(gaussian, true); //Aplica efeito

        //Principal.getPrograma().close(); //Fecha tela Principal
        //fecha();//Fecha tela Principal
        try {
            cad.start(new Stage());//Abre tela Login
        } catch (Exception ex) {
            //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Não foi possível abrir a \"Tela de Cadastro\"!");
            System.err.println("Erro: " + ex);
        }
        //}
    }

    @FXML
    private void textSearchClicked(MouseEvent event) {
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text("DESCULPE"));
        conteudo.setBody(new Text("Esta opção ainda não está funcionando para esta página.\n\n"
                + "*Por ser uma versão Beta, o programa ainda não está completo!"));

        JFXDialog msgBusca = new JFXDialog(areaExibicao, conteudo, JFXDialog.DialogTransition.TOP);

        JFXButton btnOk = new JFXButton("OK");

        btnOk.setOnAction(evt -> {
            msgBusca.close();
            pnlSemFavoritos.setEffect(null);
            pnlErroLogin.setEffect(null);
            pnlFavoritos.setEffect(null);
        });

        conteudo.setActions(btnOk);

        msgBusca.setOnDialogClosed(handler -> {
            pnlSemFavoritos.setEffect(null);
            pnlErroLogin.setEffect(null);
            pnlFavoritos.setEffect(null);

        });

        msgBusca.show();
        Effect gaussian = new GaussianBlur(4);
        pnlSemFavoritos.setEffect(gaussian);
        pnlErroLogin.setEffect(gaussian);
        pnlFavoritos.setEffect(gaussian);

    }
}
