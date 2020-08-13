package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controller.exibicao.IconePoliticoController;
import controller.exibicao.PoliticosCidadeController;
import database_connection.DBConexao;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ViewPoliticosController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Label lblEstado, lblCidade, lblVoltar;

    @FXML
    private BorderPane bpEstado, bpCidade;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgSearch, imgVoltar;

    @FXML
    private StackPane areaPoliticosEstado, areaPoliticosCidade,
            areaErrosCidade, areaMensagem;
    @FXML
    private AnchorPane pnlErro500, pnlErro404, pnlErroEstado, pnlErroCidade;
    @FXML
    private Label lblErroDetalhes, lblErro;
    private boolean clickDetalhesError = false;

    @FXML
    private TilePane areaFiltro;

    @FXML
    private Button btnFiltroSenadores, btnFiltroDepEstadual,
            btnFiltroDepFederais, btnFiltroGovernador;

    //Variável que possivelmente identificará "está/cada" pagina desta
    private int idEstado;

    //Paginas/Icones que serão criadas a partir desta
    private Parent politicosEstado, politicosCidade, paginaPerfil;

    //Instancia da Classe
    private static ViewPoliticosController instance;

    //Variaveis para identificar "Filtro Ativo"
    private boolean filtroGovernadorSelected = false,
            filtroSenadorSelected = false,
            filtroDepEstadualSelected = false,
            filtroDepFederalSelected = false;

    private Button ultimoFiltro = null;

    //Criando um "Array de Classes" para filtro
    ArrayList<Node> iconesFiltro = new ArrayList<>();
    /*private ArrayList<Node> iconesGovernadores = new ArrayList<>();
    private ArrayList<Node> iconesSenadores = new ArrayList<>();
    private ArrayList<Node> iconesDepFederais = new ArrayList<>();
    private ArrayList<Node> iconesDepEstaduais = new ArrayList<>();
     */
    //Efeito do botão "Filtro" selecionado
    private final Effect shadowFiltro = new DropShadow(BlurType.GAUSSIAN, Color.rgb(187, 187, 187), 8, 0.25, 0, 0);

    Image imgVoltarPreechido = new Image("/modelo/icones/Back_Arrow_Preech_40px.png");
    Image imgVoltarDefault = new Image("/modelo/icones/Back_Arrow_Transp_40px.png");

    //Variáveis de conexão com o banco
    Connection conexao = DBConexao.getConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    private boolean checkClickedSearch = false;

    //</editor-fold>
    //
    //CONSTRUTOR
    public ViewPoliticosController() {
        instance = this;
    }

    public static ViewPoliticosController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblVoltar.setVisible(false);
        imgVoltar.setOnMouseEntered(event -> {
            lblVoltar.setVisible(true);
            imgVoltar.setImage(imgVoltarPreechido);
        });
        imgVoltar.setOnMouseExited(event -> {
            lblVoltar.setVisible(false);
            imgVoltar.setImage(imgVoltarDefault);
        });

        //Pra garantir que a tela fique atrás
        areaErrosCidade.toBack();
        areaErrosCidade.setVisible(false);

        //VER DETALHES DE UM POSSÍVEL ERRO AO ABRIR O PERFIL
        lblErro.setVisible(false);
        lblErroDetalhes.setOnMouseClicked((event) -> {

            if (clickDetalhesError == false) {
                lblErro.setVisible(true);
                clickDetalhesError = true;
            } else {
                lblErro.setVisible(false);
                clickDetalhesError = false;
            }
        });

        bpEstado.toFront();

        //pnlErroEstado.setVisible(false);
        //pnlErroEstado.toBack();
        areaFiltro.setVisible(false);
        try {
            //-> Segue para o método "Initialize" da classe "ViewPoliticosEstadoController"
            politicosEstado = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/PoliticosEstados.fxml"));
            System.out.println("Carregou uma exibição de \"PolíticosEstado\"");
            //Adiciona como "Filha" ao objeto
            areaPoliticosEstado.getChildren().add(politicosEstado);
            politicosEstado.toFront();
        } catch (IOException ex) {
            System.err.println("Não foi possível carregar os \"Políticos do Estado\"");
            System.err.println("Erro: " + ex);
        }

    }

    @FXML
    private void botaoVoltar(MouseEvent event) {

    }

    @FXML
    private void textSearchClicked(MouseEvent event) {
        System.out.println("Evento: " + event.getSource());
        /*if (this.idEstado != 18 && checkClickedSearch == false) {
            String mensagem = "Esta opção ainda não está funcionando para este estado.\n\n"
                    + "*Por se tratar de uma versão Beta, o programa ainda não está completo!";
            mensagemTextSearch(mensagem, "DESCULPE!");
            checkClickedSearch = true;
        } else*/
        if (this.idEstado == 18 && checkClickedSearch == false) {
            String mensagem = "A busca funcionará apenas para algumas cidades da região metropolitana de Curitiba.\n\n"
                    + "*Por se tratar de uma versão Beta, o programa ainda não está completo!";
            mensagemTextSearch(mensagem, "DICA!");
            checkClickedSearch = true;
        }
    }

    @FXML
    private void textSearchAction(ActionEvent event) {

        /*&& (txtSearch.getText().equals("Pinhais"))
                || (txtSearch.getText().equals("Curitiba"))
                || (txtSearch.getText().equals("Piraquara"))
         */
        bpCidade.toFront();
        lblCidade.setText("Pesquisa por \"" + txtSearch.getText() + "\"");
        areaErrosCidade.setVisible(true);
        try {
            //Buscar Cidade
            stmt = conexao.prepareStatement("SELECT * FROM vote_bem.cidade WHERE fk_estado_est = ? and nome = ?");
            stmt.setInt(1, idEstado);
            stmt.setString(2, txtSearch.getText());
            rs = stmt.executeQuery();

            int idCidade = 0;

            while (rs.next()) {
                idCidade = rs.getInt("pk_cidade");

            }
            System.out.println("ID CIDADE: " + idCidade);

            politicosCidade = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/PoliticosCidade.fxml"));
            System.out.println("Carregou uma exibição de \"PolíticosCidade\"");

            if (idCidade == 0) {
                //areaPoliticosCidade.getChildren().add(politicosCidade);
                areaErrosCidade.toFront();
                pnlErro404.toFront();
                lblErro.setText("Error code: #404 Not Found.");
            } else if (idCidade != 0 && this.idEstado == 18) {

                PoliticosCidadeController.getInstance().carregarCargosMunicipais(idEstado, idCidade);

                if (PoliticosCidadeController.getInstance().getIconesPrefeito() == 0
                        || PoliticosCidadeController.getInstance().getIconesVereadores() == 0) {
                    pnlErroCidade.toFront();
                    String mensagem = "Por ser uma versão Beta, o programa ainda não está completo!";
                    mensagemTextSearch(mensagem, "DESCULPE!");
                } else {

                    areaPoliticosCidade.getChildren().add(politicosCidade);
                    politicosCidade.toFront();
                }

            } else {
                String mensagem = "Por ser uma versão Beta, o programa ainda não está completo!";
                mensagemTextSearch(mensagem, "DESCULPE!");
                areaErrosCidade.toFront();
                pnlErroCidade.toFront();
                //lblErro.setText("Error code: #500 Internal Server Error. ");
                //lblErro.setText("Error code: #404 Not Found.");
                /*String mensagem = "Esta opção ainda não está funcionando para este estado.\n\n"
                        + "*Por se tratar de uma versão Beta, o programa ainda não está completo!";
                mensagemTextSearch(mensagem, "DESCULPE!");*/
            }

        } catch (IOException e) {
            System.err.println("Não foi possível carregar os \"Políticos da Cidade\"");
            System.err.println("Erro: " + e);
        } catch (SQLException e) {
            System.err.println("Não foi possível encontrar a \"CIDADE\".");
            System.err.println("Erro: " + e);
        }

    }

    private void mensagemTextSearch(String mensagem, String titulo) {
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text(titulo));
        conteudo.setBody(new Text(mensagem));

        JFXDialog msgBusca = new JFXDialog(areaMensagem, conteudo, JFXDialog.DialogTransition.TOP);

        JFXButton btnOk = new JFXButton("OK");

        btnOk.setOnAction(event -> {
            msgBusca.close();
            bpCidade.setEffect(null);
            bpEstado.setEffect(null);
            areaMensagem.setVisible(false);
        });

        conteudo.setActions(btnOk);

        msgBusca.setOnDialogClosed(handler -> {
            bpCidade.setEffect(null);
            bpEstado.setEffect(null);
            areaMensagem.setVisible(false);
        });

        areaMensagem.toFront();
        areaMensagem.setVisible(true);

        msgBusca.show();
        Effect gaussian = new GaussianBlur(4);
        bpCidade.setEffect(gaussian);
        bpEstado.setEffect(gaussian);

    }

    //BOTÕES DE FILTRO
    @FXML
    private void filtroSelectedAction(ActionEvent event) {

        /**
         * Se clicar no botão "não selecionado" executa a ação de acordo e chama
         * para frente o "Pane" de filtro.. Senão, se clicar novamente no botão
         * "já selecionado" reseta para o original e traz para frente a "visão
         * Geral" de todos os políticos do Estado.
         */
        //FILTRO GOVERNADOR
        if (event.getSource() == btnFiltroGovernador && filtroGovernadorSelected == false) {

            carregarIconesFiltro(event, 2);
            filtroGovernadorSelected = true;

        } else if (event.getSource() == btnFiltroGovernador && filtroGovernadorSelected == true) {
            resetarBotoes();
        }
        //FILTRO SENADOR
        if (event.getSource() == btnFiltroSenadores && filtroSenadorSelected == false) {

            carregarIconesFiltro(event, 3);
            filtroSenadorSelected = true;

        } else if (event.getSource() == btnFiltroSenadores && filtroSenadorSelected == true) {
            resetarBotoes();
        }

        //FILTRO DEP. FEDERAL
        if (event.getSource() == btnFiltroDepFederais && filtroDepFederalSelected == false) {

            carregarIconesFiltro(event, 4);
            filtroDepFederalSelected = true;

        } else if (event.getSource() == btnFiltroDepFederais && filtroDepFederalSelected == true) {
            resetarBotoes();
        }

        //FILTRO DEP. ESTADUAL
        if (event.getSource() == btnFiltroDepEstadual && filtroDepEstadualSelected == false) {

            carregarIconesFiltro(event, 5);
            filtroDepEstadualSelected = true;

        } else if (event.getSource() == btnFiltroDepEstadual && filtroDepEstadualSelected == true) {
            resetarBotoes();
        }
    }

    //CARREGA OS ICONES ESPECÍFICOS PARA A "AREA DE FILTRO" 
    private void carregarIconesFiltro(ActionEvent event, int idCargo) {
        resetarBotoes();

        //Recebe o evento do botão clicado para aplicar efeitos
        Button botao = (Button) event.getSource();
        areaFiltro.setVisible(true);
        areaFiltro.toFront();
        politicosEstado.setVisible(false);

        //Efeitos do botão selecionado
        botao.setScaleX(1.15);
        botao.setScaleY(1.15);
        botao.setScaleZ(1.15);
        botao.setEffect(shadowFiltro);
        botao.getStyleClass().add("buttonFiltroSelected");

        /**
         * Testa se é igual ao já clicado para não precise refazer a busca no
         * banco
         */
        if (event.getSource() != ultimoFiltro) {

            /**
             * Se "areaFiltro" conter filhos excluí para que outro botão não
             * adicione icones e misture os cargos
             */
            if (areaFiltro.getChildren() != null) {
                areaFiltro.getChildren().clear();
            }

            /**
             * Recebe o ultimo botão de filtro clicado!
             */
            ultimoFiltro = (Button) event.getSource();

            iconesFiltro.clear();

            buscarCargosFederaisBD(idCargo);

            if (!iconesFiltro.isEmpty()) {
                iconesFiltro.forEach((icone) -> {
                    areaFiltro.getChildren().add(icone);
                });
            } else {
                pnlErroEstado.toFront();
                pnlErroEstado.setVisible(true);
            }

        }
    }

    //RESETA OS BOTÕES DE FILTRO
    private void resetarBotoes() {

        politicosEstado.toFront();
        politicosEstado.setVisible(true);
        areaFiltro.setVisible(false);

        filtroGovernadorSelected = false;
        filtroSenadorSelected = false;
        filtroDepEstadualSelected = false;
        filtroDepFederalSelected = false;

        pnlErroEstado.setVisible(false);
        pnlErroEstado.toBack();

        int scale = 1;
        String classeEfeito = "buttonFiltroSelected";
        btnFiltroGovernador.setScaleX(scale);
        btnFiltroGovernador.setScaleY(scale);
        btnFiltroGovernador.setScaleZ(scale);
        btnFiltroGovernador.setEffect(null);
        btnFiltroGovernador.getStyleClass().remove(classeEfeito);

        btnFiltroSenadores.setScaleX(scale);
        btnFiltroSenadores.setScaleY(scale);
        btnFiltroSenadores.setScaleZ(scale);
        btnFiltroSenadores.setEffect(null);
        btnFiltroSenadores.getStyleClass().remove(classeEfeito);

        btnFiltroDepEstadual.setScaleX(scale);
        btnFiltroDepEstadual.setScaleY(scale);
        btnFiltroDepEstadual.setScaleZ(scale);
        btnFiltroDepEstadual.setEffect(null);
        btnFiltroDepEstadual.getStyleClass().remove(classeEfeito);

        btnFiltroDepFederais.setScaleX(scale);
        btnFiltroDepFederais.setScaleY(scale);
        btnFiltroDepFederais.setScaleZ(scale);
        btnFiltroDepFederais.setEffect(null);
        btnFiltroDepFederais.getStyleClass().remove(classeEfeito);
    }

    //SET das identificações do Estado
    public void setLblEstado(int estado, String texto) {
        this.idEstado = estado;
        this.lblEstado.setText(texto);
    }

    private void buscarCargosFederaisBD(int idCargo) {
        try {
            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=? and fk_estado_fed=?");
            stmt.setInt(1, idCargo);
            stmt.setInt(2, this.idEstado);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Node icone = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconePolitico.fxml"));

                int id = rs.getInt("pk_politico");

                icone.setId("" + id);
                IconePoliticoController.getInstance().setDados(id);

                icone.setOnMouseClicked(event -> {

                    try {
                        /**
                         * -> Segue, para o método "Initialize" da classe
                         * "IconePoliticoController"
                         */
                        paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                        System.out.println("Criou uma página de Perfil");
                        ViewPerfilController.getInstance().carregarPolitico(id);
                        ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
                    } catch (IOException e) {
                        System.err.println("ViewPolíticos: Não foi possivel carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });

                iconesFiltro.add(icone);

            }
        } catch (SQLException e) {
            System.err.println("ViewPolíticos: Não foi possivel buscar \"POLÍTICO\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("ViewPolíticos: Não foi possivel carregar \"Icone Político\"");
            System.err.println("Erro: " + e);
        }
    }

}
