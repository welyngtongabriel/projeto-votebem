package votebem_Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {

    private static Stage programa;

    @Override
    public void start(Stage stage) throws Exception {
        //Carrega a interface principal
        //-> Segue para o método "Initialize" da classe "ViewPrincipalController"
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ViewPrincipal.fxml"));

        //Cria uma cena com a interface principal
        Scene scene = new Scene(root);

        //Retira a "barra superior" do programa
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("EWE Polinfo");

        //Adiciona o fxml carregado "root" em uma "cena" 
        stage.setScene(scene);

        /*Atribui ao "estágio programa" uma "cópia" deste stage
        para poder "manipular" este programa em outras classes
         */
        setPrograma(stage);

        //Inicia o programa/stage
        stage.show();

    }

    //Métodos Get e Set de Progrma
    public static Stage getPrograma() {
        return programa;
    }

    public void setPrograma(Stage stage) {
        Principal.programa = stage;
    }

    //Método Main
    public static void main(String[] args) {
        launch(args);
    }

}
