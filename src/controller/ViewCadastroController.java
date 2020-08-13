package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import database_connection.UsuarioDao;
import java.net.URL;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Usuario;
import votebem_Main.Cadastro;
import votebem_Main.Login;

public class ViewCadastroController implements Initializable {

    @FXML
    private PasswordField txtSenha, txtConfSenha;
    @FXML
    private TextField txtNome, txtEmail, txtUsuario;
    @FXML
    private JFXRadioButton radioMasculino, radioFeminino;
    @FXML
    private ToggleGroup genero;
    @FXML
    private ImageView imgNome, imgUser, imgSenha, imgConfirmaSenha,
            imgEmail, imgClose;
    @FXML
    private Label lblAlerta;
    @FXML
    private Button btnCadastrar, btnLogin, btnSair;
    @FXML
    private StackPane areaMensagem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblAlerta.setVisible(false);
        areaMensagem.setVisible(false);
        mouseActions();
    }

    private void mouseActions() {
        btnSair.setOnAction((ActionEvent e) -> {
            //Principal principal = new Principal();//Cria Principal
            Cadastro.getViewCadastro().close();//Fecha tela Login
            try {
                //principal.start(new Stage());//Abre tela Principal
                ViewPrincipalController.getInstance().setEffectDisable(null, false);
            } catch (Exception ex) {
                //Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Não foi possível abrir a \"Tela Principal\"!");
                System.err.println("Erro: " + ex);
            }
        });
        btnLogin.setOnAction(event -> {
            Login login = new Login();//Cria Login
            Cadastro.getViewCadastro().close();//Fecha tela Cadastro
            try {
                login.start(new Stage());//Abre tela Login
            } catch (Exception ex) {
                Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void btnCadastroAction(ActionEvent event) {
        String nome = txtNome.getText(),
                usuario = txtUsuario.getText(),
                senha = txtSenha.getText(),
                confsenha = txtConfSenha.getText(),
                email = txtEmail.getText(),
                sexo = null;

        if (!txtNome.getText().isEmpty()) {
            Image userName = new Image("/modelo/icones/UserNameWhite_45px.png");
            imgNome.setImage(userName);
            if (!txtUsuario.getText().isEmpty()) {
                Image userUser = new Image("/modelo/icones/UserWhite_45px.png");
                imgUser.setImage(userUser);
                if (senha.equals(confsenha) && !txtSenha.getText().isEmpty()) {
                    Image sen1 = new Image("/modelo/icones/PasswordWhite_45px.png");
                    Image sen2 = new Image("/modelo/icones/PassChaveWhite_45px.png");
                    imgSenha.setImage(sen1);
                    imgConfirmaSenha.setImage(sen2);
                    if (!txtEmail.getText().isEmpty()) {
                        Image e_mail = new Image("/modelo/icones/EnvelopeWhite_45px.png");
                        imgEmail.setImage(e_mail);
                        if (txtEmail.getText().contains("@")) {
                            if (radioFeminino.isSelected() || radioMasculino.isSelected()) {
                                if (radioFeminino.isSelected()) {
                                    sexo = "Feminino";
                                } else {
                                    sexo = "Masculino";
                                }

                                //Login login = new Login();//Cria Login
                                Usuario user = new Usuario(nome, usuario, senha, sexo, email, "user");
                                UsuarioDao dao = new UsuarioDao();

                                if (dao.add(user)) {
                                    /*Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                                al.setHeaderText("Usuario Cadastrado!");*/
                                    mensagemCadastro("BEM VINDO!", "Cadastro realizado com Sucesso!");
                                    //Cadastro.getViewCadastro().close();//Fecha tela Cadastro
                                    try {
                                        //viewPrincipal.start(new Stage());
                                        ViewPrincipalController.getInstance().setEffectDisable(null, false);
                                        ViewPrincipalController.getInstance().setLogin(true, nome, sexo);
                                        System.out.println("Login com sucesso!");

                                    } catch (Exception ex) {
                                        //Logger.getLogger(ViewLoginController.class.getName()).log(Level.SEVERE, null, ex);
                                        System.err.println("Não foi possível abrir a \"Tela Principal\"!");
                                        System.err.println("Erro: " + ex);
                                    }
                                    //al.show();
                                } else {
                                    /*Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setHeaderText("Usuario nao cadastrado");
                                al.show();*/
                                    mensagemCadastro("Oops!", "Algo deu errado! Tente novamente.");
                                }
                            } else {
                                lblAlerta.setText("Escolha um gênero!");
                                lblAlerta.setVisible(true);
                            }
                        } else {
                            /*Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setHeaderText("Insira um Email valido!");
                            al.show();*/
                            lblAlerta.setText("Insirá um e-mail válido!");
                            lblAlerta.setVisible(true);
                            Image img = new Image("/modelo/icones/EnvelopeRed_45px.png");
                            imgEmail.setImage(img);
                        }
                    } else {
                        /*Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setHeaderText("Insira um Email!");
                        al.show();*/
                        lblAlerta.setText("Insirá um e-amil!");
                        lblAlerta.setVisible(true);
                        Image img = new Image("/modelo/icones/EnvelopeRed_45px.png");
                        imgEmail.setImage(img);
                    }

                } else {
                    /*Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setHeaderText("Nao possui senha ou nao coincidem");
                    al.show();*/
                    lblAlerta.setText("Campos de senhas vazios ou\nnão coincidem!");
                    lblAlerta.setVisible(true);
                    Image imgSen1 = new Image("/modelo/icones/PasswordRed_45px.png");
                    Image imgSen2 = new Image("/modelo/icones/PassChaveRed_45px.png");
                    imgSenha.setImage(imgSen1);
                    imgConfirmaSenha.setImage(imgSen2);
                }
            } else {
                /* Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Insira um Usuario!");
                al.show();*/
                lblAlerta.setText("Insirá um nome de usuário!");
                lblAlerta.setVisible(true);
                Image img = new Image("/modelo/icones/UserRed_45px.png");
                imgUser.setImage(img);
            }
        } else {
            /*Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Insira um Nome!");
            al.show();*/
            lblAlerta.setText("Insirá um nome!");
            lblAlerta.setVisible(true);
            Image img = new Image("/modelo/icones/UserNameRed_45px.png");
            imgNome.setImage(img);
        }
    }

    private void mensagemCadastro(String titulo, String mensagem) {
        JFXDialogLayout conteudo = new JFXDialogLayout();
        conteudo.setHeading(new Text(titulo));
        conteudo.setBody(new Text(mensagem));

        JFXDialog msgSeguir = new JFXDialog(areaMensagem, conteudo, JFXDialog.DialogTransition.TOP);

        JFXButton btnOk = new JFXButton("FECHAR");

        btnOk.setOnAction(event -> {
            msgSeguir.close();
            areaMensagem.setVisible(true);
            Cadastro.getViewCadastro().close();
        });

        conteudo.setActions(btnOk);

        msgSeguir.setOnDialogClosed(handler -> {
            areaMensagem.setVisible(true);
            Cadastro.getViewCadastro().close();
        });
        areaMensagem.setVisible(true);
        msgSeguir.show();

    }
}
