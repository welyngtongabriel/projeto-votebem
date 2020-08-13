package votebem_Main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {

    private static Stage viewLogin;//Criação da Janela 1

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ViewLogin.fxml"));//Carrega FXML
        Scene scene = new Scene(root);//Coloca FXML na cena
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("EWE Login");//Titulo da Janela 2

        stage.setScene(scene);//Coloca a cena na Janela 2
        stage.show();//Abre Janela 2
        setViewLogin(stage);//Coloca Janela 2 na Janela 1

    }

    public static Stage getViewLogin() {
        return viewLogin;
    }

    public static void setViewLogin(Stage viewLogin) {
        Login.viewLogin = viewLogin;
    }
}
