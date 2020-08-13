package controller;

import modelo.Politico;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controller.exibicao.IconeFavoritoController;
import controller.exibicao.ProposicaoController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import database_connection.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import votebem_Main.Login;

public class ViewPerfilController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Label lblNomeUrna, lblNumero, lblSiglaPartido, lblCargo,
            lblErroDetalhes, lblErro, lblVoltar;
    @FXML
    private Circle imgPerfil;
    @FXML
    private Group lblInfo;
    @FXML
    private VBox postTimeLine;
    @FXML
    private ImageView imgCapa, imgSearch, imgVoltar, imgInfoPost;
    @FXML
    private ScrollPane scrollExibicao;
    @FXML
    private StackPane areaErros;
    @FXML
    private AnchorPane pnlPerfil, pnlErro500, pnlErro404, pnlSobre;
    @FXML
    private Pane postFim;
    @FXML
    private JFXButton btnSeguir, btnSobre;
    @FXML
    private TextField txtSearch;
    @FXML
    private StackPane perfil;

    //Ideia é utilizar para colcoar nas postagens de proposições
    private String nomeIniciativa;

    //Referencia da Classe
    private static ViewPerfilController instance;
    //Paginas que serão criadas a partir desta
    private Parent paginaFavorito, iconeFavorito, estePerfil;

    private final Effect shadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(187, 187, 187), 5, 0.2, 0, 0);
    private final Effect gaussian = new GaussianBlur(4.2);

    private int idPagina;
    private boolean clickDetalhesError = false, checkCliqueSobre = false;
    private ArrayList<Node> proposições = new ArrayList<>();

    Image imgVoltarPreechido = new Image("/modelo/icones/Back_Arrow_Preech_40px.png");
    Image imgVoltarDefault = new Image("/modelo/icones/Back_Arrow_Transp_40px.png");

    //ATRIBUTOS DE CONEXÃO COM O BANCO 
    private static Connection conexao = null;
    //public static DBConexao conn;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    //</editor-fold>
    //
    //Construtor
    public ViewPerfilController() {
        instance = this;
    }

    public static ViewPerfilController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imgCapa.setEffect(gaussian);
        //Organizar telas ao iniciar
        areaErros.toBack();
        areaErros.setVisible(false);
        pnlSobre.setVisible(false);
        pnlSobre.setEffect(shadow);

        postTimeLine.toFront();

        lblVoltar.setVisible(false);
        lblErro.setVisible(false);
        lblInfo.setVisible(false);

        mouseEventsActions();

    }

    private void verificarSeguindo() {
        if (ViewFavoritosController.getInstance().verificarFavorito("" + this.idPagina) == true) {
            btnSeguir.setText("SEGUINDO");
            btnSeguir.getStyleClass().add("buttonSeguirSelected");
        }

    }

    private void mouseEventsActions() {
        imgVoltar.setOnMouseEntered(event -> {
            lblVoltar.setVisible(true);
            imgVoltar.setImage(imgVoltarPreechido);
        });
        imgVoltar.setOnMouseExited(event -> {
            lblVoltar.setVisible(false);
            imgVoltar.setImage(imgVoltarDefault);
        });

        lblErroDetalhes.setOnMouseClicked((event) -> {

            if (clickDetalhesError == false) {
                lblErro.setVisible(true);
                clickDetalhesError = true;
            } else {
                lblErro.setVisible(false);
                clickDetalhesError = false;
            }
        });

        imgInfoPost.setOnMouseClicked(event -> {
            Image img = new Image("/modelo/icones/Info_40px.png");
            imgInfoPost.setImage(img);
            lblInfo.setVisible(true);
        });

    }

    //Buscar no Banco
    public void carregarPolitico(int idPolitico) {

        this.idPagina = idPolitico;
        proposições = new ArrayList<>();
        int cont = 0;
        try {
            for (Politico po : PoliticoDAO.getPolitico(idPolitico)) {

                lblCargo.setText(po.getCargoPolitico().toUpperCase());
                lblNomeUrna.setText(po.getNomePolitico());
                lblNumero.setText("" + po.getNumeroPolitico());
                lblSiglaPartido.setText(po.getPartidoPolitico());

                Image fotoPolitico;
                String formato = null;

                verificarSeguindo();
                carregarImagemCapa(po);

                //<editor-fold desc="CARREGAR FOTO DE PERFIL/CAPA">
                OUTER:
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            formato = ".jpg";
                            break;
                        case 1:
                            formato = ".JPG";
                            break;
                        case 2:
                            formato = ".png";
                            break;
                    }
                    try {
                        switch (po.getCargoPolitico()) {
                            case "Prefeito":
                            case "Vereador":
                                fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/" + po.getCidadePolitico() + "/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                                imgPerfil.setFill(new ImagePattern(fotoPolitico));
                                break OUTER;
                            case "Governador":
                            case "Senador":
                            case "Deputado Estadual":
                            case "Deputado Federal":
                                fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/00_" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                                imgPerfil.setFill(new ImagePattern(fotoPolitico));
                                break OUTER;
                            case "Presidente":
                                fotoPolitico = new Image("/modelo/fotospoliticos/Brasil/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                                imgPerfil.setFill(new ImagePattern(fotoPolitico));
                                break OUTER;
                            default:
                                break;
                        }
                    } catch (IllegalArgumentException e) {
                        //if (i == 2) {
                        System.err.println("Foto: \"" + po.getNomePolitico() + "\". Não encontrada!");
                        System.err.println("ERRO: " + e);

                        //Carrega Imagem padrão para a foto
                        Image fotoPadrao = new Image("/modelo/fotospoliticos/PerfilMale.png");
                        imgPerfil.setFill(new ImagePattern(fotoPadrao));
                        //}
                    }
                }

                //</editor-fold>
                //
                //<editor-fold desc="CARREGAR PROPOSIÇÕES">
                carregarProposicoes(idPolitico);

                //</editor-fold>
            }
        } catch (NullPointerException e) {
            System.err.println("Não foi possível carregar um Perfíl do Político!");
            System.err.println("ERRO: " + e);
            //pnlErro404.toFront();
            //lblErro.setText("Error code: #404 Not Found. " + e.getMessage() + ".");
            //areaErros.toFront();
        }

    }

    private void carregarProposicoes(int idPolitico) {
        conexao = DBConexao.getConexao();
        int cont = 0;
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MMMM/yyyy");
        try {
            String sql = "SELECT proposicao.codigo, politico.nome, proposicao.tipo, proposicao.ementa, proposicao.situacao, proposicao.envio FROM vote_bem.proposicao "
                    + "INNER JOIN vote_bem.politico on politico.pk_politico = proposicao.fk_iniciativa "
                    + "WHERE fk_iniciativa = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPolitico);

            rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    Node iconePost = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/Proposicao.fxml"));
                    iconePost.setEffect(shadow);
                    
                    String codigo = rs.getString("codigo");
                    String iniciativa = rs.getString("nome");
                    String titulo = rs.getString("tipo");
                    String ementa = rs.getString("ementa");
                    //Date envio = rs.getDate("envio");
                    //String data = formatoData.format(rs.getDate("envio").toString());
                    ProposicaoController.getInstance().setDados(codigo, iniciativa, titulo, ementa);
                    
                    proposições.add(iconePost);
                    postTimeLine.getChildren().add(proposições.get(cont));
                    postFim.toFront();
                    cont++;
                } catch (IOException ex) {
                    System.err.println("Não foi possível carregar um \"Post da Proposição\"");
                    System.err.println("Erro: " + ex);
                }
            }

        } catch (SQLException e) {
            System.err.println("Não foi possível consultar uma Proposição no banco!");
            System.err.println("ERRO: " + e);
        }

    }

    private void carregarImagemCapa(Politico po) {
        Image capa;
        //"If" para escolher foto do local de governo do estado!
        capa = new Image("/modelo/sedes/GovParaná.jpg");

        imgCapa.setImage(capa);
    }

    //BOTÃO SEGUIR
    @FXML
    private void botaoSeguirAcao(ActionEvent event) {

        //Faz o teste de Login
        if (ViewPrincipalController.getInstance().getLogin() == true) {
            //Ações do botão seguir se estiver logado
            if (btnSeguir.getText().equals("SEGUIR")) {
                try {
                    //System.out.println("Nome deste perfil: "+ this.getLblNomeUrna());

                    //-> Segue para o método "Initialize" da classe "IconeFavoritoController"
                    iconeFavorito = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconeFavorito.fxml"));
                    System.out.println("Criou um \"Icone de Favorito\"!");

                    //Seta um id do Icone
                    iconeFavorito.setId("" + this.idPagina);
                    //Passa os dados para preencher este icone
                    IconeFavoritoController.getInstance().setDados(this.idPagina, this.lblNomeUrna.getText(), this.lblNumero.getText(),
                            this.lblSiglaPartido.getText(), this.lblCargo.getText(), (ImagePattern) this.imgPerfil.getFill());

                    ViewFavoritosController.getInstance().adicionarIconesFilhos(iconeFavorito, true);

                    //Define os efeitos do botão
                    btnSeguir.getStyleClass().add("buttonSeguirSelected");
                    btnSeguir.setText("SEGUINDO");
                } catch (IOException ex) {
                    //Logger.getLogger(ViewPerfilController.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Não foi possivel carregar a interface \"Icone Favorito\"!");
                    System.err.println("Erro: " + ex);
                }
            } else {

                try {

                    ViewFavoritosController.getInstance().removerFavorito("" + this.idPagina);
                    //Remove o estilo "SEGUINDO"
                    btnSeguir.getStyleClass().removeAll("buttonSeguirSelected");

                    btnSeguir.setText("SEGUIR");
                } catch (Exception e) {
                    System.err.println("Não foi possível encontrar o icone!");
                    System.err.println("Erro: " + e);
                }
            }
        } else {
            mensagemBotaoSeguir();
        }

    }

    public void mensagemBotaoSeguir() {
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text("ATIVAR FUNÇÕES"));
        conteudo.setBody(new Text("Para habilitar esta e outras funções do app "
                + "faça login ou crie uma conta gratuíta!"));

        JFXDialog msgSeguir = new JFXDialog(perfil, conteudo, JFXDialog.DialogTransition.TOP);

        JFXButton btnEntrar = new JFXButton("ENTRAR");
        JFXButton btnOk = new JFXButton("OK");

        btnOk.setOnAction(event -> {
            msgSeguir.close();
            pnlPerfil.setEffect(null);
        });

        btnEntrar.setOnAction(event -> {
            Login log = new Login();//Cria Login

            msgSeguir.close();

            try {
                log.start(new Stage());//Abre tela Login
            } catch (Exception ex) {
                //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Não foi possível abrir a \"Tela de Login\"!");
                System.err.println("Erro: " + ex);
            }
            pnlPerfil.setEffect(null);
        });

        conteudo.setActions(btnEntrar, btnOk);

        msgSeguir.setOnDialogClosed(handler -> {
            pnlPerfil.setEffect(null);

        });

        msgSeguir.show();
        Effect gaussianPerfil = new GaussianBlur(4);
        pnlPerfil.setEffect(gaussianPerfil);

    }

    @FXML
    private void botaoSobreAcao(ActionEvent event) {
        if (checkCliqueSobre == false) {
            checkCliqueSobre = true;
            pnlSobre.toFront();
            pnlSobre.setVisible(true);
            btnSobre.setText("-Sobre");
            postTimeLine.setDisable(true);
            postTimeLine.setEffect(new GaussianBlur(6));

        } else {
            checkCliqueSobre = false;
            pnlSobre.setVisible(false);
            pnlSobre.toBack();
            btnSobre.setText("+Sobre");
            postTimeLine.setDisable(false);
            postTimeLine.setEffect(null);

        }
    }

    @FXML
    private void textSearchClicked(MouseEvent event) {
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text("DESCULPE"));
        conteudo.setBody(new Text("Esta opção ainda não está funcionando para esta página.\n\n"
                + "*Por ser uma versão Beta, o programa ainda não está completo!"));

        JFXDialog msgBusca = new JFXDialog(perfil, conteudo, JFXDialog.DialogTransition.TOP);

        JFXButton btnOk = new JFXButton("OK");

        btnOk.setOnAction(evt -> {
            msgBusca.close();
            pnlPerfil.setEffect(null);

        });

        conteudo.setActions(btnOk);

        msgBusca.setOnDialogClosed(handler -> {
            pnlPerfil.setEffect(null);

        });

        msgBusca.show();
        Effect gaussianPerfil = new GaussianBlur(4);
        pnlPerfil.setEffect(gaussianPerfil);

    }

    public void error500ToFront() {
        pnlErro500.toFront();
        areaErros.setVisible(true);
        areaErros.toFront();
    }

    //GET E SET
    public String getLblNomeUrna() {
        return this.lblNomeUrna.getText();
    }

    public String getLblNumero() {
        return this.lblNumero.getText();
    }

    public String getLblSiglaPartido() {
        return this.lblSiglaPartido.getText();
    }

    public String getLblCargo() {
        return this.lblCargo.getText();
    }

    public void setLblNomeUrna(String nomeUrna) {
        lblNomeUrna.setText(nomeUrna);
    }

    public void setLblNumero(String numero) {
        this.lblNumero.setText(numero);
    }

    public void setLblSiglaPartido(String siglaPartido) {
        this.lblSiglaPartido.setText(siglaPartido);
    }

    public void setLblCargo(String cargo) {
        this.lblCargo.setText(cargo);
    }

    public void setLblErro(String erro) {
        this.lblErro.setText(erro);
    }

}
