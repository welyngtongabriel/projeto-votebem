package controller.exibicao;

import modelo.Politico;
import database_connection.PoliticoDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class IconePoliticoController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Rectangle recFoto;

    @FXML
    private Label lblNumero, lblSiglaPartido;

    @FXML
    public AnchorPane apIcone;

    @FXML
    private Text lblNomeUrna;

    //Instancia desta classe
    private static IconePoliticoController instance;
    //Variaveis de efeitos
    private final Effect enteredShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(187, 187, 187), 8, 0.25, 0, 0);
    private final Effect normalShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(187, 187, 187), 5, 0.18, 0, 0);

    //</editor-fold>
    //
    //CONSTRUTOR
    public IconePoliticoController() {
        instance = this;
    }

    public static IconePoliticoController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apIcone.setEffect(normalShadow);
        mouseEffectsEvent();
    }

    private void mouseEffectsEvent() {
        apIcone.setOnMouseEntered(event -> {
            apIcone.setEffect(null);
            apIcone.setEffect(enteredShadow);
        });
        apIcone.setOnMouseExited(event -> {
            apIcone.setEffect(null);
            apIcone.setEffect(normalShadow);
        });
    }

    public void setDados(int idPolitico) {

        for (Politico po : PoliticoDAO.getPolitico(idPolitico)) {

            //lblCargo.setText(po.getCargoPolitico().toUpperCase());
            lblNomeUrna.setText(po.getNomePolitico());
            lblNumero.setText("" + po.getNumeroPolitico());
            lblSiglaPartido.setText(po.getPartidoPolitico());
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
                    if(po.getCargoPolitico().equals("Prefeito") || po.getCargoPolitico().equals("Vereador")){
                      fotoPolitico = new Image("/modelo/fotospoliticos/"+po.getEstadoPolitico()+"/Municípios/"+po.getCidadePolitico()+"/"+po.getCargoPolitico()+"/"+ po.getNomePolitico() + formato);
                        recFoto.setFill(new ImagePattern(fotoPolitico));
                        break;  
                    } else if(po.getCargoPolitico().equals("Governador") || po.getCargoPolitico().equals("Senador") || po.getCargoPolitico().equals("Deputado Estadual") || po.getCargoPolitico().equals("Deputado Federal")){
                        fotoPolitico = new Image("/modelo/fotospoliticos/"+po.getEstadoPolitico()+"/"+po.getCargoPolitico()+"/" + po.getNomePolitico() + formato);
                        recFoto.setFill(new ImagePattern(fotoPolitico));
                        break; 
                    }
                } catch (Exception e) {
                    if (i == 2) {
                        System.err.println("Foto: \""+ po.getNomePolitico() +"\". Não encontrada!");
                        System.err.println("ERRO: " + e);

                        //Carrega Imagem padrão para a foto
                        Image fotoPadrao = new Image("/modelo/fotospoliticos/PerfilMale.png");
                        recFoto.setFill(new ImagePattern(fotoPadrao));
                    }
                }
            }*/
            //</editor-fold>
            //
            System.out.println("Estado: " + po.getEstadoPolitico());
            System.out.println("Cargo: " + po.getCargoPolitico());
            System.out.println("Cidade: " + po.getCidadePolitico());
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
                            //System.out.println("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/Municípios/" + po.getCidadePolitico() + "/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/"+ po.getCidadePolitico() +"/"+ po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            //fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/Municípios/"+ po.getCidadePolitico() +"/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            recFoto.setFill(new ImagePattern(fotoPolitico));
                            break OUTER;
                        case "Governador":
                        case "Senador":
                        case "Deputado Estadual":
                        case "Deputado Federal":
                            fotoPolitico = new Image("/modelo/fotospoliticos/" + po.getEstadoPolitico() + "/00_" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            recFoto.setFill(new ImagePattern(fotoPolitico));
                            break OUTER;
                        case "Presidente":
                            fotoPolitico = new Image("/modelo/fotospoliticos/Brasil/" + po.getCargoPolitico() + "/" + po.getNomePolitico() + formato);
                            recFoto.setFill(new ImagePattern(fotoPolitico));
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
                        recFoto.setFill(new ImagePattern(fotoPadrao));
                        break;
                    //}
                }
            }

            //</editor-fold>
        }
    }

    public void setTxtNomeUrna(String nome) {
        this.lblNomeUrna.setText(nome);
    }

}
