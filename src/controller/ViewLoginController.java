package controller;

import database_connection.UsuarioDao;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Usuario;
import votebem_Main.*;

public class ViewLoginController implements Initializable {

    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Button btnLogin, btnCadastro, btnSair;
    @FXML
    private Label lblTitulo, lblAlerta;
    @FXML
    private ImageView imgUser, imgSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblAlerta.setVisible(false);
        Effect gaussian = new GaussianBlur(6);
        ViewPrincipalController.getInstance().setEffectDisable(gaussian, true);
        mouseEvents();

    }

    private void mouseEvents() {
        /*lblCadastro.setOnMouseEntered((MouseEvent e) -> { //Faz o "Login/Cadastro" mudar cor
            lblCadastro.setStyle("-fx-text-fill: #00BFFF");
        });

        lblCadastro.setOnMouseExited((MouseEvent e) -> { //Faz o "Login/Cadastro" voltar para preto
            lblCadastro.setStyle("-fx-text-fill: #1C1C1C");
        });*/

        btnCadastro.setOnAction((ActionEvent e) -> {
            Cadastro cadastro = new Cadastro();//Cria Cadastro
            fecharViewLogin();//Fecha tela Login
            try {
                cadastro.start(new Stage());//Abre tela Cadastro
            } catch (Exception ex) {
                Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnSair.setOnAction((ActionEvent e) -> {
            //Principal principal = new Principal();//Cria Principal
            fecharViewLogin();//Fecha tela Login
            try {
                //principal.start(new Stage());//Abre tela Principal
                ViewPrincipalController.getInstance().setEffectDisable(null, false);
            } catch (Exception ex) {
                //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Não foi possível abrir a \"Tela Principal\"!");
                System.err.println("Erro: " + ex);
            }
        });

    }

    public void fecharViewLogin() {
        Login.getViewLogin().close();
    }

    @FXML
    public void btnLogarAction(ActionEvent event) {
        UsuarioDao dao = new UsuarioDao();
        List<Usuario> usuarios = dao.getList();

        for (int x = 0; x < usuarios.size(); x++) {

            if (txtUsuario.getText().equals(usuarios.get(x).getUsuario()) && txtSenha.getText().equals(usuarios.get(x).getSenha())) {
                //Principal viewPrincipal = new Principal();
                ViewPrincipalController.getInstance().setIdUser(usuarios.get(x).getId());
                ViewFavoritosController.getInstance().consultaFavoritoBD();

                //viewPrincipal.start(new Stage());
                ViewPrincipalController.getInstance().setEffectDisable(null, false);
                ViewPrincipalController.getInstance().setLogin(true, usuarios.get(x).getNome(), usuarios.get(x).getSexo());

                fecharViewLogin();
                System.out.println("Login com sucesso!");

                break;
            } else {
                if (x == usuarios.size() - 1) {
                    /*Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setHeaderText("Usuario ou Senha incorreto!");
                    al.show();*/
                    Image userErro = new Image("/modelo/icones/UserRed_45px.png");
                    Image senhaErro = new Image("/modelo/icones/PasswordRed_45px.png");
                    imgUser.setImage(userErro);
                    imgSenha.setImage(senhaErro);

                    lblAlerta.setVisible(true);
                    txtSenha.setText(null);
                }
            }
        }
    }

}
