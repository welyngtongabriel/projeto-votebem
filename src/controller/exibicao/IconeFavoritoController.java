package controller.exibicao;

import com.jfoenix.controls.JFXButton;
import controller.ViewFavoritosController;
import controller.ViewPerfilController;
import controller.ViewPrincipalController;
import database_connection.PoliticoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import modelo.Politico;

public class IconeFavoritoController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Label lblNomeUrna, lblNumero, lblSiglaPartido, lblCargo;

    @FXML
    private Circle imgPerfilCirc;

    @FXML
    private Rectangle imgPerfilRec;

    @FXML
    private JFXButton btnSeguindo;

    @FXML
    private AnchorPane apIcone;

    private int id;
    private Parent paginaPerfil;
    private final Effect normalShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(187, 187, 187), 5, 0.18, 0, 0);

    private static IconeFavoritoController instance;

    //</editor-fold>
    //
    //CONSTRUTOR
    public IconeFavoritoController() {
        instance = this;
    }

    public static IconeFavoritoController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apIcone.setEffect(normalShadow);

        mouseEvents();
    }

    private void mouseEvents() {

        apIcone.setOnMouseEntered(event -> {
            apIcone.setScaleX(1.03);
            apIcone.setScaleY(1.03);
            apIcone.setScaleZ(1.03);
        });
        apIcone.setOnMouseExited(event -> {
            apIcone.setEffect(normalShadow);
            apIcone.setScaleX(1);
            apIcone.setScaleY(1);
            apIcone.setScaleZ(1);
        });
    }

    //RECEBE OS DADOS ENVIADOS DO PERFIL
    public void setDados(int idPolitico, String nome, String numeroPolitico, String sigla, String cargo, ImagePattern foto) {
        this.id = idPolitico;
        this.lblNomeUrna.setText(nome);
        this.lblNumero.setText(numeroPolitico);
        this.lblSiglaPartido.setText(sigla);
        this.lblCargo.setText(cargo);
        this.imgPerfilRec.setFill(foto);
        //his.imgPerfilCirc.setFill(foto);

        //imgPerfilCirc.toFront();
        //SETA UMA AÇÃO AO ICONE
        apIcone.setOnMouseClicked(event -> {

            try {
                paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                System.out.println("Criou uma página de Perfil");
                ViewPerfilController.getInstance().carregarPolitico(this.id);

                ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
            } catch (IOException e) {
                System.err.println("Não foi possível carregar a \"Página Perfil\"!");
                System.err.println("Erro: " + e);
            }
        });

    }

    public void setDados(int idPolitico) {

        for (Politico po : PoliticoDAO.getPolitico(idPolitico)) {

            this.id = idPolitico;
            lblNomeUrna.setText(po.getNomePolitico());
            lblNumero.setText("" + po.getNumeroPolitico());
            lblSiglaPartido.setText(po.getPartidoPolitico());
            lblCargo.setText(po.getCargoPolitico().toUpperCase());
            Image fotoPolitico = null;
            String formato = null;

            //<editor-fold desc="CARREGAR FOTO PERFIL ANTIGO">
            //Testa o formato de imagem
            /*for (int i = 0; i < 3; i++) {
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

                    fotoPolitico = new Image("/modelo/fotospoliticos/parana/" + po.getNomePolitico() + formato);
                    imgPerfilCirc.setFill(new ImagePattern(fotoPolitico));
                    //imgPerfilRec.setFill(new ImagePattern(fotoPolitico));
                    break;

                } catch (Exception e) {
                    if (i == 2) {
                        System.err.println("Foto: \"" + po.getNomePolitico() + "\". Não encontrada!");
                        System.err.println("ERRO: " + e);

                        //Carrega Imagem padrão para a foto
                        Image fotoPadrao = new Image("/modelo/fotospoliticos/PerfilMale.png");
                        imgPerfilCirc.setFill(new ImagePattern(fotoPadrao));
                        //imgPerfilRec.setFill(new ImagePattern(fotoPadrao));
                    }
                }
            }*/
            //</editor-fold>
            //
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
                            imgPerfilRec.setFill(new ImagePattern(fotoPolitico));
                            break OUTER;
                        case "Governador":
                        case "Senador":
                        case "Deputado Estadual":
                        case "Deputado Federal":
                            fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/00_" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            imgPerfilRec.setFill(new ImagePattern(fotoPolitico));
                            break OUTER;
                        case "Presidente":
                            fotoPolitico = new Image("/modelo/fotospoliticos/Brasil/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            imgPerfilRec.setFill(new ImagePattern(fotoPolitico));
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
                        imgPerfilRec.setFill(new ImagePattern(fotoPadrao));
                    //}
                }
            }

            //</editor-fold>
            //SETA UMA AÇÃO AO ICONE
            apIcone.setOnMouseClicked(event -> {

                try {
                    paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                    System.out.println("Criou uma página de Perfil");
                    ViewPerfilController.getInstance().carregarPolitico(this.id);

                    ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
                } catch (IOException e) {
                    System.err.println("Icone Favorito: Não foi possível carregar a \"Página Perfil\"!");
                    System.err.println("Erro: " + e);
                }
            });
        }
    }

    //REMOVER DOS FAORITOS **NÃO SEI SE ESTA FUNCIONANDO DESTE JEITO**
    @FXML
    private void btnSeguindoAction(ActionEvent event) {

        ViewFavoritosController.getInstance().removerFavorito("" + this.id);

    }
}
