package controller.exibicao;

import controller.ViewPerfilController;
import controller.ViewPrincipalController;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ProposicaoController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private Text lblTxtEmenta;

    @FXML
    private Label lblTitulo, lblTotVotos, lblVotaram,
            lblData, lblNomeIniciativa, lblCodigo;

    @FXML
    private ImageView imgLike, imgDeslike;

    private static int totVotos = 0, votosFavor = 0, votosContra = 0;

    private boolean likeSelected = false, deslikeSelected = false;

    private static ProposicaoController instance;

    //</editor-fold>
    //
    public ProposicaoController() {
        instance = this;
    }

    public static ProposicaoController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblData.setVisible(false);
        setLabelVotaram();
        //cliquesLike();
        //eventosSelecaoVotos();

    }

    //EVENTOS DO MOUSE
    @FXML
    private void cliquesLike(MouseEvent event) {
        if (ViewPrincipalController.getInstance().getLogin() == true) {
            if(event.getSource() == imgLike){
            //imgLike.setOnMouseClicked(event -> {

                if (likeSelected == false) {
                    Image newImgLike = new Image("/modelo/icones/LikeMarcadBlue_35px.png");
                    imgLike.setImage(newImgLike);

                    likeSelected = true;
                    if (deslikeSelected == true) {
                        deslikeSelected = false;
                        Image newImgDeslike = new Image("/modelo/icones/LikeDesmarc_35px.png");
                        imgDeslike.setImage(newImgDeslike);
                        ProposicaoController.votosContra--;
                    }

                    ProposicaoController.votosFavor++;
                    ProposicaoController.totVotos = votosContra + votosFavor;
                    setLabelVotaram();
                } else {
                    Image img = new Image("/modelo/icones/LikeDesmarc_35px.png");
                    imgLike.setImage(img);

                    likeSelected = false;
                    ProposicaoController.votosFavor--;
                    ProposicaoController.totVotos = votosContra + votosFavor;
                    setLabelVotaram();
                }

            //});
            }
            if(event.getSource() == imgDeslike){
            //imgDeslike.setOnMouseClicked(event -> {

                if (deslikeSelected == false) {
                    Image newImgDeslike = new Image("/modelo/icones/LikeMarcadRed_35px.png");
                    imgDeslike.setImage(newImgDeslike);

                    deslikeSelected = true;
                    if (likeSelected == true) {
                        likeSelected = false;
                        Image newImgLike = new Image("/modelo/icones/LikeDesmarc_35px.png");
                        imgLike.setImage(newImgLike);

                        ProposicaoController.votosFavor--;
                    }

                    ProposicaoController.votosContra++;
                    ProposicaoController.totVotos = votosContra + votosFavor;
                    setLabelVotaram();
                } else {
                    Image newImgDeslike = new Image("/modelo/icones/LikeDesmarc_35px.png");
                    imgDeslike.setImage(newImgDeslike);

                    deslikeSelected = false;
                    ProposicaoController.votosContra--;
                    ProposicaoController.totVotos = votosContra + votosFavor;
                    setLabelVotaram();
                }

            //});
            }
        } else {
            ViewPerfilController.getInstance().mensagemBotaoSeguir();
        }

    }

    //EVENTOS DO MOUSE (ANTIGO)
    private void acaoSelecaoVotos() {
        /*
        radioFavor.setOnAction(event -> {
            radioFavor.setId("A Favor");

            if (radioFavor.isSelected()) {
                if (!(radioContra.isSelected())) {
                    ProposicaoController.totVotos++;
                }
                radioContra.setSelected(false);
                ProposicaoController.votosFavor++;
                ProposicaoController.votosContra--;
                setLabelVotaram();
                System.out.println(radioFavor.getId() + " - Votos: " + totVotos);
            } else {
                if (!(radioContra.isSelected())) {
                    ProposicaoController.totVotos--;
                }
                ProposicaoController.votosFavor--;
                setLabelVotaram();
                System.out.println(radioFavor.getId() + " - Votos: " + totVotos);
            }

        });
        radioContra.setOnAction(event -> {
            radioContra.setId("Contra");
            if (radioContra.isSelected()) {
                if (!(radioFavor.isSelected())) {
                    ProposicaoController.totVotos++;
                }
                radioFavor.setSelected(false);
                ProposicaoController.votosContra++;
                setLabelVotaram();
                System.out.println(radioContra.getId() + " - Votos: " + totVotos);
            } else {
                if (!(radioFavor.isSelected())) {
                    ProposicaoController.totVotos--;
                }
                ProposicaoController.votosContra--;
                setLabelVotaram();
                System.out.println(radioContra.getId() + " - Votos: " + totVotos);
            }
        });
         */
    }

    //SETA O TEXTO DE ACORDO COMS OS VOTOS
    private void setLabelVotaram() {

        if (totVotos == 0) {
            this.lblTotVotos.setVisible(false);
            this.lblVotaram.setText("Vote para que mais possam votar!");
        }
        if (totVotos == 1) {
            this.lblTotVotos.setVisible(true);
            this.lblTotVotos.setText("" + totVotos);
            this.lblVotaram.setText("já votou nesta matéria!");
        }
        if (totVotos > 1) {
            this.lblTotVotos.setText("" + totVotos);
            this.lblVotaram.setText("já votaram nesta matéria!");
        }

        System.out.println("Total Votos: " + totVotos);
        System.out.println("CONTRA - Votos: " + votosContra);
        System.out.println("À FAVOR - Votos: " + votosFavor);
        System.out.println("-------------");

    }

    public void setDados(String codigo, String iniciativa, String titulo, String ementa) {
        lblCodigo.setText(codigo);
        lblNomeIniciativa.setText(iniciativa);
        lblTitulo.setText(titulo);
        lblTxtEmenta.setText(ementa);

        //lblData.setText(data);
    }

}
