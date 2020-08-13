package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import votebem_Main.Login;
import votebem_Main.Principal;

public class ViewPrincipalController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Button btnPaginaPrincipal, btnSeguindo, btnClose,
            btnLogin, btnMinimize;

    @FXML
    private Label lblSaudacao, lblNomeUsuario;

    @FXML
    private ImageView imgClose, imgMinize, imgLogin;

    @FXML
    private Pane pnlBotoesAcesso;

    @FXML
    private StackPane areaExibicao;

    @FXML
    private AnchorPane barraSuperior, apPrograma;

    private boolean login, seguindo, botaoMenuSelecionado = false;
    private int idUser;

    //Paginas que serão criadas a partir desta
    private Parent viewInicial, viewFavoritos, viewPoliticos, viewPerfilPolitico;

    //Variáveis para mover o programa
    private double xOffset = 0, yOffset = 0;

    //Instância desta classe
    private static ViewPrincipalController instance;

    //</editor-fold>
    //
    //CONSTRUTOR
    public ViewPrincipalController() {
        instance = this;
    }

    //Torna os "métodos publicos" desta classe visíveis em outras
    public static ViewPrincipalController getInstance() {
        return instance;
    }

    //INICIALIZADOR (executado apenas uma vez, quando se carrega um fxml)
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //**Verificar a necessidade destas váriaveis aqui
        login = false;
        seguindo = false;

        carregarTelasIniciais();
        mouseActionsEvents();
        moverPrograma();
//2ea3b4
    }

    //Ações referentes ao status do login
    public void setLogin(boolean login, String usuario, String sexo) {
        this.login = login;
        if (this.login == true) {
            btnLogin.setText("Logout");
            btnLogin.getStyleClass().add("buttonLogout"); //Adiciona um Stylo css
            Image imageLogout = new Image("/modelo/icones/Logout_Rounded_Left_32px.png");
            imgLogin.setImage(imageLogout); //Altera o icone do botão

            //Operador ternário
            lblSaudacao.setText((sexo.equals("Masculino") ? "Bem-vindo!" : "Bem-vinda!"));

            lblNomeUsuario.setText(usuario);

            //Mostrar a tela correta na seção "Seguindo"
            ViewFavoritosController.getInstance().verificarLogin();
            //paginaInicialToFront();

        } else {
            lblSaudacao.setText("Bem-vindo!");
            lblNomeUsuario.setText("Usuário");

            btnLogin.setText("Entrar");
            btnLogin.getStyleClass().remove("buttonLogout");
            Image imageEntrar = new Image("/modelo/icones/Login_32px.png");
            imgLogin.setImage(imageEntrar);
        }
    }

    //CARREGAR TELAS DOS BOTÕES DESTA CLASSE
    private void carregarTelasIniciais() {
        /**
         * Ao carregar essas telas, é executado o método "Initialize" de cada
         * uma, depois o "código" continua! Então, a leitura dos códigos vai
         * passar lendo todos os métodos "Initialize" que por ventura estão
         * conectados.
         *
         * ***NESTE PONTO O PROGRAMA AINDA NÃO ABRIU***
         */

        try {
            //-> Segue para o método "Initialize" da classe "ViewInicialController"
            viewInicial = FXMLLoader.load(getClass().getResource("/interfaces/ViewInicial.fxml"));
            areaExibicao.getChildren().add(viewInicial);
            viewInicial.toFront();
        } catch (IOException e) {
            System.out.println("Não foi possível carregar a interface \"ViewInicial\"!");
            System.out.println("Erro: " + e);
        }
        try {
            viewFavoritos = FXMLLoader.load(getClass().getResource("/interfaces/ViewFavoritos.fxml"));
            areaExibicao.getChildren().add(viewFavoritos);
            viewFavoritos.toBack();
            viewFavoritos.setVisible(false);
        } catch (IOException e) {
            System.out.println("Não foi possível carregar a interface \"View Favoritos\"!");
            System.out.println("Erro: " + e);
        }
    }

    //MOVER O PROGRAMA
    private void moverPrograma() {
        barraSuperior.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        barraSuperior.setOnMouseDragged(event -> {
            Principal.getPrograma().setX(event.getScreenX() - xOffset);
            Principal.getPrograma().setY(event.getScreenY() - yOffset);
            Principal.getPrograma().setOpacity(0.85f);
        });

        barraSuperior.setOnDragDone(event -> {
            Principal.getPrograma().setOpacity(1.0f);
        });
        barraSuperior.setOnMouseReleased(event -> {
            Principal.getPrograma().setOpacity(1.0f);
        });
    }

    //Adiciona na Tela Principal as demais telas 
    public void adicionarTelasFilhas(Node pagina) {

        /**
         * Testes para saber de onde vem a página e aplicar ações de acordo
         * Talvez desse modo dê para usar no "histórico" de páginas
         */
        if (pagina == ViewInicialController.getInstance().getPoliticosEstado()) {

            System.out.println("Adicionou \"Pagina Políticos Estado\" como Filha!");
            areaExibicao.getChildren().remove(viewPoliticos);//Não necessário mas por via das dúvidas...
            viewPoliticos = (Parent) pagina;
            areaExibicao.getChildren().add((Node) viewPoliticos);
            viewPoliticos.toFront();
        } else { //if ((pagina == ViewInicialController.getInstance().getPaginaPerfil())
            System.out.println("Adicionou \"Pagina Perfil\" como Filha!");
            areaExibicao.getChildren().remove(viewPerfilPolitico);//Não necessário mas por via das dúvidas...
            viewPerfilPolitico = (Parent) pagina;
            areaExibicao.getChildren().add((Node) viewPerfilPolitico);
            viewPerfilPolitico.toFront();
        }
    }

    private void mouseActionsEvents() {
        //"LAMBDA" para criar Eventos e Ações do mouse
        btnPaginaPrincipal.setOnAction((event) -> {
            paginaInicialToFront();
            verificarCliqueBotaoMenu(event);

        });

        btnLogin.setOnMouseEntered(event -> {
            imgLogin.setFitWidth(25);
            imgLogin.setFitHeight(25);
        });
        btnLogin.setOnMouseExited(event -> {
            imgLogin.setFitWidth(20);
            imgLogin.setFitHeight(20);
        });

        btnClose.setOnMouseEntered((event) -> {
            Image img = new Image("/modelo/icones/CloseWhite_35px.png");
            imgClose.setImage(img);
        });
        btnClose.setOnMouseExited((event) -> {
            Image img = new Image("/modelo/icones/CloseGrey_35px.png");
            imgClose.setImage(img);
        });

        btnClose.setOnMouseClicked((event) -> {
            Principal.getPrograma().close();

        });

        //imSearch.setCursor(Cursor.HAND);
    }

    //CHAMAM A PÁGINA DESEJADA PARA FRENTE
    public void paginaInicialToFront() {
        /**
         * Ao retornar para a "Página Inicial" todas as outras telas criadas a
         * partir destas são removidas!
         */
        viewInicial.toFront();
        areaExibicao.getChildren().removeAll(viewPerfilPolitico, viewPoliticos);
    }

    public void paginaFavoritosToFront() {

        viewFavoritos.setVisible(true);
        viewFavoritos.toFront();
        //areaExibicao.getChildren().removeAll(viewPerfilPolitico, viewPoliticos);
    }

    public void paginaPerfilToFront() {
        viewPerfilPolitico.toFront();
    }

    public void redefinirPrograma() {

    }

    @FXML
    private void btnLoginAction(ActionEvent actionEvent) {
        System.out.println("Clicou em login!");
        if (login == false) {
            Login log = new Login();//Cria Login referencia da Tela Login

            //Effect gaussian = new GaussianBlur(6);
            //setEffectDisable(gaussian, true); //Aplica efeito
            //Principal.getPrograma().close(); //Fecha tela Principal
            //fecha();//Fecha tela Principal
            try {
                log.start(new Stage());//Abre tela Login
            } catch (Exception ex) {
                //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Não foi possível abrir a \"Tela de Login\"!");
                System.err.println("Erro: " + ex);
            }
        } else {
            setLogin(false, "Usuário", null);
            ViewFavoritosController.getInstance().verificarLogin();
        }
    }

    @FXML
    private void btnSeguindoAction(ActionEvent event) {
        ViewFavoritosController.getInstance().verificarLogin();
        paginaFavoritosToFront();
        verificarCliqueBotaoMenu(event);
    }

    private void verificarCliqueBotaoMenu(ActionEvent event) {
        Button btnSelecionado = (Button) event.getSource();

        if (botaoMenuSelecionado == false) {

            btnSelecionado.getStyleClass().add("buttoMenuSelected");
            botaoMenuSelecionado = true;
        } else {
            resetarBotoesMenu();

        }
    }

    private void resetarBotoesMenu() {

        btnPaginaPrincipal.getStyleClass().remove("buttoMenuSelected");
        btnSeguindo.getStyleClass().remove("buttoMenuSelected");
    }

    //EFEITO PARA QUANDO AS TELAS DE LOGIN/CADASTRO SÃO CHAMADAS
    public void setEffectDisable(Effect efeito, boolean disable) {
        apPrograma.setDisable(disable);
        apPrograma.setEffect(efeito);
    }

    //Get de "Seguindo" para ***TESTES***
    public boolean getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(boolean seg) {
        this.seguindo = seg;
    }

    //GET DE LOGIN
    public boolean getLogin() {
        return login;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}
