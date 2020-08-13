package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controller.exibicao.PoliticosEstadoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ViewInicialController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private ImageView imgPernambuco, imgAcre, imgRioGrandeSul, imgDivisaoEstados,
            imgParaiba, imgAlagoas, imgAmapa, imgRioJaneiro, imgMatoGrosso,
            imgParana, imgAmazonas, imgTocantins, imgRioGrandeNorte, imgSergipe,
            imgEspiritoSanto, imgPiaui, imgBahia, imgMaranhao,
            imgDistritoFederal, imgInfo, imgCeara, imgMinasGerais, imgBrasil,
            imgSaoPaulo, imgGoias, imgMatoGSul, imgRondonia, imgSantaCatarina,
            imgRoraima, imgSearch, imgPara;

    @FXML
    private StackPane areaExibicao;

    @FXML
    private AnchorPane pnlMapa;

    @FXML
    private Text txtInfo;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblNomeEstado;

    @FXML
    private Button btnPresidente;

    //Paginas que serão criadas a partir desta
    private Parent paginaPoliticos, paginaPerfil;

    //Intancia desta classe
    private static ViewInicialController instance;

    //</editor-fold>
    //
    //CONSTRUTOR
    public ViewInicialController() {
        instance = this;
    }

    public static ViewInicialController getInstance() {
        return instance;
    }

    //INCIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblNomeEstado.setVisible(false);
        estadosMouseEntered(); //Criar Eventos do mouse
        estadosMouseExited();

        //estadosMouseClicked(); //Criar Ações de Clique do mouse
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
            pnlMapa.setEffect(null);

        });

        conteudo.setActions(btnOk);

        msgBusca.setOnDialogClosed(handler -> {
            pnlMapa.setEffect(null);

        });

        msgBusca.show();
        Effect gaussian = new GaussianBlur(4);
        pnlMapa.setEffect(gaussian);

    }

    @FXML
    private void btnBrasilAction(ActionEvent event) {
        //CARREGAR PERFIL "EXCLUSIVO" PRESIDENTE
        try {
            paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
            System.out.println("Criou uma página de Perfil");

            ViewPerfilController.getInstance().carregarPolitico(1);
            ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar a \"Página Perfil\"!");
            System.err.println("Erro: " + e);
        }

    }

    @FXML
    private void estadoClicked(MouseEvent event) {
        //Teste para saber qual estado foi clicado
        if (event.getSource() == imgAcre) {
            loadViewPoliticos(1, "Políticos do Acre");
        }
        if (event.getSource() == imgAlagoas) {
            loadViewPoliticos(2, "Políticos de Alagos");
        }
        if (event.getSource() == imgAmapa) {
            loadViewPoliticos(4, "Políticos do Amapá");
        }
        if (event.getSource() == imgAmazonas) {
            loadViewPoliticos(3, "Políticos do Amazonas");
        }
        if (event.getSource() == imgBahia) {
            loadViewPoliticos(5, "Políticos da Bahia");
        }
        if (event.getSource() == imgCeara) {
            loadViewPoliticos(6, "Políticos do Ceará");
        }
        if (event.getSource() == imgDistritoFederal) {
            loadViewPoliticos(7, "Políticos do Distrito Federal");
        }
        if (event.getSource() == imgEspiritoSanto) {
            loadViewPoliticos(8, "Políticos do Espírito Santo");
        }
        if (event.getSource() == imgGoias) {
            loadViewPoliticos(9, "Políticos de Goiás");
        }
        if (event.getSource() == imgMaranhao) {
            loadViewPoliticos(10, "Políticos do Maranhão");
        }
        if (event.getSource() == imgMatoGSul) {
            loadViewPoliticos(12, "Políticos do Mato Grosso do Sul");
        }
        if (event.getSource() == imgMatoGrosso) {
            loadViewPoliticos(13, "Políticos do Mato Grosso");
        }
        if (event.getSource() == imgMinasGerais) {
            loadViewPoliticos(11, "Políticos de Minas Gerais");
        }
        if (event.getSource() == imgPara) {
            loadViewPoliticos(14, "Políticos do Pará");
        }
        if (event.getSource() == imgParaiba) {
            loadViewPoliticos(15, "Políticos da Paraíba");
        }
        if (event.getSource() == imgParana) {
            loadViewPoliticos(18, "Políticos do Paraná");
        }
        if (event.getSource() == imgPernambuco) {
            loadViewPoliticos(16, "Políticos de Pernambuco");
        }
        if (event.getSource() == imgPiaui) {
            loadViewPoliticos(17, "Políticos do Piauí");
        }
        if (event.getSource() == imgRioGrandeNorte) {
            loadViewPoliticos(20, "Políticos do Rio Grande do Norte");
        }
        if (event.getSource() == imgRioGrandeSul) {
            loadViewPoliticos(23, "Políticos do Rio Grande do Sul");
        }
        if (event.getSource() == imgRioJaneiro) {
            loadViewPoliticos(19, "Políticos do Rio de Janeiro");
        }
        if (event.getSource() == imgRondonia) {
            loadViewPoliticos(21, "Políticos de Rondônia");
        }
        if (event.getSource() == imgRoraima) {
            loadViewPoliticos(22, "Políticos de Roraima");
        }
        if (event.getSource() == imgSantaCatarina) {
            loadViewPoliticos(24, "Políticos de Santa Catarina");
        }
        if (event.getSource() == imgSaoPaulo) {
            loadViewPoliticos(26, "Políticos de São Paulo");
        }
        if (event.getSource() == imgSergipe) {
            loadViewPoliticos(25, "Políticos do Sergipe");
        }
        if (event.getSource() == imgTocantins) {
            loadViewPoliticos(27, "Políticos de Tocantins");
        }
    }

    //CARREGAR PAGINA POLITICOS DO ESTADO SELECIONADO
    private void loadViewPoliticos(int idEstado, String textoEstado) {
        //Recebe o ID do Estado e um texto para o usuário

        try {
            //-> Segue para o método "Initialize" da classe "ViewPoliticosController"
            paginaPoliticos = FXMLLoader.load(getClass().getResource("/interfaces/ViewPoliticos.fxml"));
            System.out.println("Criou uma página de \"" + textoEstado + "\".");

            //Envia os dados para "PoliticosEstadosController"
            PoliticosEstadoController.getInstance().carregarPoliticos(idEstado);

            //Seta o texto e passa o ID de referência. 
            ViewPoliticosController.getInstance().setLblEstado(idEstado, textoEstado);

            //Adiciona como "filha" na View Principal
            ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPoliticos);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar a \"Página Políticos do Estado\"!");
            System.err.println("Erro: " + e);
        }
    }

    //EVENTOS DE EFEITOS DO MOUSE
    private void estadosMouseEntered() {
        btnPresidente.setOnMouseEntered((MouseEvent event) -> {
            imgBrasil.setVisible(true);
            lblNomeEstado.setText("BRASIL");
            lblNomeEstado.setVisible(true);
        });

        imgAcre.setOnMouseEntered((MouseEvent event) -> {
            imgAcre.setImage(new Image("/modelo/estados/Acre_Flag.png"));
            lblNomeEstado.setText("ACRE");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgAlagoas.setOnMouseEntered((MouseEvent event) -> {
            imgAlagoas.setImage(new Image("/modelo/estados/Alagoas_Flag.png"));
            lblNomeEstado.setText("ALAGOAS");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgAmapa.setOnMouseEntered((MouseEvent event) -> {
            imgAmapa.setImage(new Image("/modelo/estados/Amapá_Flag.png"));
            lblNomeEstado.setText("AMAPÁ");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgAmazonas.setOnMouseEntered((MouseEvent event) -> {
            imgAmazonas.setImage(new Image("/modelo/estados/Amazonas_Flag.png"));
            lblNomeEstado.setText("AMAZONAS");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgBahia.setOnMouseEntered((MouseEvent event) -> {
            imgBahia.setImage(new Image("/modelo/estados/Bahia_Flag.png"));
            lblNomeEstado.setText("BAHIA");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgCeara.setOnMouseEntered((MouseEvent event) -> {
            imgCeara.setImage(new Image("/modelo/estados/Ceará_Flag.png"));
            lblNomeEstado.setText("CEARÁ");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgDistritoFederal.setOnMouseEntered((MouseEvent event) -> {
            imgDistritoFederal.setImage(new Image("/modelo/estados/Distrito Federal_Flag.png"));
            lblNomeEstado.setText("DISTRITO FEDERAL");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgEspiritoSanto.setOnMouseEntered((MouseEvent event) -> {
            imgEspiritoSanto.setImage(new Image("/modelo/estados/Espírito Santo_Flag.png"));
            lblNomeEstado.setText("ESPÍRITO SANTO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgGoias.setOnMouseEntered((MouseEvent event) -> {
            imgGoias.setImage(new Image("/modelo/estados/Goias_Flag.png"));
            lblNomeEstado.setText("GOIÁS");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgMaranhao.setOnMouseEntered((MouseEvent event) -> {
            imgMaranhao.setImage(new Image("/modelo/estados/Maranhão_Flag.png"));
            lblNomeEstado.setText("MARANHÃO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgMatoGSul.setOnMouseEntered((MouseEvent event) -> {
            imgMatoGSul.setImage(new Image("/modelo/estados/Mato Grosso do Sul_Flag.png"));
            lblNomeEstado.setText("MATO GROSSO DO SUL");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgMatoGrosso.setOnMouseEntered((MouseEvent event) -> {
            imgMatoGrosso.setImage(new Image("/modelo/estados/Mato Grosso_Flag.png"));
            lblNomeEstado.setText("MATO GROSSO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgMinasGerais.setOnMouseEntered((MouseEvent event) -> {
            imgMinasGerais.setImage(new Image("/modelo/estados/Minas Gerais_Flag.png"));
            lblNomeEstado.setText("MINAS GERAIS");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgParana.setOnMouseEntered((MouseEvent event) -> {
            imgParana.setImage(new Image("/modelo/estados/Paraná_Flag.png"));
            lblNomeEstado.setText("PARANÁ");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgParaiba.setOnMouseEntered((MouseEvent event) -> {
            imgParaiba.setImage(new Image("/modelo/estados/Paraíba_Flag.png"));
            lblNomeEstado.setText("PARAÍBA");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgPara.setOnMouseEntered((MouseEvent event) -> {
            imgPara.setImage(new Image("/modelo/estados/Pará_Flag.png"));
            lblNomeEstado.setText("PARÁ");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgPernambuco.setOnMouseEntered((MouseEvent event) -> {
            imgPernambuco.setImage(new Image("/modelo/estados/Pernambuco_Flag.png"));
            lblNomeEstado.setText("PERNAMBUCO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgPiaui.setOnMouseEntered((MouseEvent event) -> {
            imgPiaui.setImage(new Image("/modelo/estados/Piauí_Flag.png"));
            lblNomeEstado.setText("PIAUÍ");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgRioGrandeNorte.setOnMouseEntered((MouseEvent event) -> {
            imgRioGrandeNorte.setImage(new Image("/modelo/estados/Rio Grande do Norte_Flag.png"));
            lblNomeEstado.setText("RIO GRANDE DO NORTE");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgRioGrandeSul.setOnMouseEntered((MouseEvent event) -> {
            imgRioGrandeSul.setImage(new Image("/modelo/estados/Rio Grande do Sul_Flag.png"));
            lblNomeEstado.setText("RIO GRANDE DO SUL");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgRioJaneiro.setOnMouseEntered((MouseEvent event) -> {
            imgRioJaneiro.setImage(new Image("/modelo/estados/Rio de Janeiro_Flag.png"));
            lblNomeEstado.setText("RIO DE JANEIRO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgRondonia.setOnMouseEntered((MouseEvent event) -> {
            imgRondonia.setImage(new Image("/modelo/estados/Rondônia_Flag.png"));
            lblNomeEstado.setText("RONDÔNIA");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgRoraima.setOnMouseEntered((MouseEvent event) -> {
            imgRoraima.setImage(new Image("/modelo/estados/Roraima_Flag.png"));
            lblNomeEstado.setText("RORAIMA");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgSantaCatarina.setOnMouseEntered((MouseEvent event) -> {
            imgSantaCatarina.setImage(new Image("/modelo/estados/Santa Catarina_Flag.png"));
            lblNomeEstado.setText("SANTA CATARINA");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgSergipe.setOnMouseEntered((MouseEvent event) -> {
            imgSergipe.setImage(new Image("/modelo/estados/Sergipe_Flag.png"));
            lblNomeEstado.setText("SERGIPE");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgSaoPaulo.setOnMouseEntered((MouseEvent event) -> {
            imgSaoPaulo.setImage(new Image("/modelo/estados/São Paulo_Flag.png"));
            lblNomeEstado.setText("SÃO PAULO");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
        imgTocantins.setOnMouseEntered((MouseEvent event) -> {
            imgTocantins.setImage(new Image("/modelo/estados/Tocantins_Flag.png"));
            lblNomeEstado.setText("TOCANTINS");
            //lblNomeEstado.setVisible(true);
            zoomEstado(event);
        });
    }

    private void estadosMouseExited() {
        btnPresidente.setOnMouseExited((MouseEvent event) -> {
            imgBrasil.setVisible(false);
            lblNomeEstado.setVisible(false);
        });

        imgAcre.setOnMouseExited((MouseEvent event) -> {
            imgAcre.setImage(new Image("/modelo/estados/opaco/Acre.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgAlagoas.setOnMouseExited((MouseEvent event) -> {
            imgAlagoas.setImage(new Image("/modelo/estados/opaco/Alagoas.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgAmapa.setOnMouseExited((MouseEvent event) -> {
            imgAmapa.setImage(new Image("/modelo/estados/opaco/Amapá.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgAmazonas.setOnMouseExited((MouseEvent event) -> {
            imgAmazonas.setImage(new Image("/modelo/estados/opaco/Amazonas.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgBahia.setOnMouseExited((MouseEvent event) -> {
            imgBahia.setImage(new Image("/modelo/estados/opaco/Bahia.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgCeara.setOnMouseExited((MouseEvent event) -> {
            imgCeara.setImage(new Image("/modelo/estados/opaco/Ceará.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgDistritoFederal.setOnMouseExited((MouseEvent event) -> {
            imgDistritoFederal.setImage(new Image("/modelo/estados/opaco/Distrito Federal.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgEspiritoSanto.setOnMouseExited((MouseEvent event) -> {
            imgEspiritoSanto.setImage(new Image("/modelo/estados/opaco/Espírito Santo.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgGoias.setOnMouseExited((MouseEvent event) -> {
            imgGoias.setImage(new Image("/modelo/estados/opaco/Goias.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgMaranhao.setOnMouseExited((MouseEvent event) -> {
            imgMaranhao.setImage(new Image("/modelo/estados/opaco/Maranhão.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgMatoGSul.setOnMouseExited((MouseEvent event) -> {
            imgMatoGSul.setImage(new Image("/modelo/estados/opaco/Mato Grosso do Sul.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgMatoGrosso.setOnMouseExited((MouseEvent event) -> {
            imgMatoGrosso.setImage(new Image("/modelo/estados/opaco/Mato Grosso.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgMinasGerais.setOnMouseExited((MouseEvent event) -> {
            imgMinasGerais.setImage(new Image("/modelo/estados/opaco/Minas Gerais.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgParana.setOnMouseExited((MouseEvent event) -> {
            imgParana.setImage(new Image("/modelo/estados/opaco/Paraná.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgParaiba.setOnMouseExited((MouseEvent event) -> {
            imgParaiba.setImage(new Image("/modelo/estados/opaco/Paraíba.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgPara.setOnMouseExited((MouseEvent event) -> {
            imgPara.setImage(new Image("/modelo/estados/opaco/Pará.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgPernambuco.setOnMouseExited((MouseEvent event) -> {
            imgPernambuco.setImage(new Image("/modelo/estados/opaco/Pernambuco.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgPiaui.setOnMouseExited((MouseEvent event) -> {
            imgPiaui.setImage(new Image("/modelo/estados/opaco/Piauí.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgRioGrandeNorte.setOnMouseExited((MouseEvent event) -> {
            imgRioGrandeNorte.setImage(new Image("/modelo/estados/opaco/Rio Grande do Norte.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgRioGrandeSul.setOnMouseExited((MouseEvent event) -> {
            imgRioGrandeSul.setImage(new Image("/modelo/estados/opaco/Rio Grande do Sul.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgRioJaneiro.setOnMouseExited((MouseEvent event) -> {
            imgRioJaneiro.setImage(new Image("/modelo/estados/opaco/Rio de Janeiro.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgRondonia.setOnMouseExited((MouseEvent event) -> {
            imgRondonia.setImage(new Image("/modelo/estados/opaco/Rondônia.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgRoraima.setOnMouseExited((MouseEvent event) -> {
            imgRoraima.setImage(new Image("/modelo/estados/opaco/Roraima.png"));
            //lblNomeEstado.setVisible(false);
        });
        imgSantaCatarina.setOnMouseExited((MouseEvent event) -> {
            imgSantaCatarina.setImage(new Image("/modelo/estados/opaco/Santa Catarina.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgSergipe.setOnMouseExited((MouseEvent event) -> {
            imgSergipe.setImage(new Image("/modelo/estados/opaco/Sergipe.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgSaoPaulo.setOnMouseExited((MouseEvent event) -> {
            imgSaoPaulo.setImage(new Image("/modelo/estados/opaco/São Paulo.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
        imgTocantins.setOnMouseExited((MouseEvent event) -> {
            imgTocantins.setImage(new Image("/modelo/estados/opaco/Tocantins.png"));
            //lblNomeEstado.setVisible(false);
            resetZoomEstado(event);
        });
    }

    private void zoomEstado(MouseEvent img) {
        lblNomeEstado.setVisible(true);
        /*
        ImageView estado = (ImageView) img.getSource();
        estado.toFront();
        estado.setScaleX(1.1);
        estado.setScaleY(1.1);
        estado.setScaleZ(1.1);
         */
    }

    private void resetZoomEstado(MouseEvent img) {
        lblNomeEstado.setVisible(false);
        /*
        ImageView estado = (ImageView) img.getSource();
        estado.toBack();
        estado.setScaleX(1);
        estado.setScaleY(1);
        estado.setScaleZ(1);
         */
    }

    //GET's de PÁGINAS
    public Parent getPoliticosEstado() {
        return paginaPoliticos;
    }
}
