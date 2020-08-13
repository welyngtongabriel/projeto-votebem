package controller.exibicao;

import controller.ViewPerfilController;
import controller.ViewPrincipalController;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PoliticosEstadoController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private HBox boxGovernador, boxSenadores, boxDepEstaduais,
            boxDepFederais;

    @FXML
    private VBox boxCargos;

    @FXML
    private ScrollPane scrlGovernador, scrlSenadores,
            scrlDepFederais, scrlDepEstaduais;

    @FXML
    private Label lblGovernador, lblSenadores, lblDepFederais,
            lblDepEstaduais;

    //Paginas que serão criadas a partir desta
    private Parent paginaPerfil;

    //Instancia desta Classe
    private static PoliticosEstadoController instance;

    private ArrayList<Node> iconesGovernadores;// = new ArrayList<>();
    private ArrayList<Node> iconesSenadores;// = new ArrayList<>();
    private ArrayList<Node> iconesDepFederais;// = new ArrayList<>();
    private ArrayList<Node> iconesDepEstaduais;// = new ArrayList<>();
    //iconePrefeitos = null, iconeVereadores = null;

    //Variáveis de conexão com o banco
    Connection conexao = DBConexao.getConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    //ScrollBar scrl = (ScrollBar) scrlDepEstaduais.lookup(".barrarolagem .scroll-bar:vertical");
    //</editor-fold>
    //
    //CONTRUTOR
    public PoliticosEstadoController() {
        instance = this;
    }

    public static PoliticosEstadoController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //scrl.setDisable(true);
    }

    public void carregarPoliticos(int idEstado) {

        carregarGovernadores(idEstado);
        carregarSenadores(idEstado);
        carregarDepFederais(idEstado);
        carregarDepEstaduais(idEstado);

    }

    //CRIAR ICONES GOVERNADORES
    private void carregarGovernadores(int idEstado) {

        int cont = 0;
        iconesGovernadores = new ArrayList<>();
        try {

            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=2 and fk_estado_fed=?");

            stmt.setInt(1, idEstado);
            rs = stmt.executeQuery();

            while (rs.next()) {
                /**
                 * -> Segue, a cada loop, para o método "Initialize" da classe
                 * "IconePoliticoController"
                 */
                Node iconePolitico = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconePolitico.fxml"));

                int id = rs.getInt("pk_politico");

                iconePolitico.setId("" + id);
                //Seta os dados no icone carregado agora
                IconePoliticoController.getInstance().setDados(id);

                iconePolitico.setOnMouseClicked(event -> {

                    try {
                        paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                        System.out.println("Criou uma página de Perfil");
                        paginaPerfil.setId("" + id);

                        ViewPerfilController.getInstance().carregarPolitico(id);
                        ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
                    } catch (IOException e) {
                        System.err.println("PoliticosEstado: Não foi possível carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });
                iconesGovernadores.add(iconePolitico);
                boxGovernador.getChildren().add(iconesGovernadores.get(cont));
                cont++;
            }

            if (iconesGovernadores.isEmpty()) {
                boxCargos.getChildren().removeAll(lblGovernador, scrlGovernador);
            }
        } catch (SQLException e) {
            System.err.println("Não foi possivel buscar \"GOVERNADOR\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar Icone \"GOVERNADOR\"");
        }

    }

    //CRIAR ICONES SENADORES
    private void carregarSenadores(int idEstado) {
        int cont = 0;
        iconesSenadores = new ArrayList<>();
        try {

            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=3 and fk_estado_fed=?");
            stmt.setInt(1, idEstado);
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
                        System.err.println("Não foi possivel carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });

                iconesSenadores.add(icone);

                boxSenadores.getChildren().add(iconesSenadores.get(cont));

                cont++;
            }
            if (iconesSenadores.isEmpty()) {
                boxCargos.getChildren().removeAll(lblSenadores, scrlSenadores);
            }
        } catch (SQLException e) {
            System.err.println("PolíticosEstado: Não foi possivel buscar \"SENADORES\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("PolíticosEstado: Não foi possivel carregar Icone \"SENADORES\"");
            System.err.println("Erro: " + e);
        }

    }

    //CRIAR ICONES DEP. FEDERAIS
    private void carregarDepFederais(int idEstado) {
        int cont = 0;
        iconesDepFederais = new ArrayList<>();
        try {

            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=4 and fk_estado_fed=?");
            stmt.setInt(1, idEstado);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Node teste = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconePolitico.fxml"));

                int id = rs.getInt("pk_politico");

                /**
                 * -> Segue, a cada loop, para o método "Initialize" da classe
                 * "IconePoliticoController"
                 */
                teste.setId("" + id);
                IconePoliticoController.getInstance().setDados(id);

                teste.setOnMouseClicked(event -> {
                    //System.out.println("ID Clicado: " + id);
                    try {
                        paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                        System.out.println("Criou uma página de Perfil");
                        ViewPerfilController.getInstance().carregarPolitico(id);
                        ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
                    } catch (IOException e) {
                        System.err.println("Não foi possivel carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });
                iconesDepFederais.add(teste);
                boxDepFederais.getChildren().add(iconesDepFederais.get(cont));
                cont++;
            }
            if (iconesDepFederais.isEmpty()) {
                boxCargos.getChildren().removeAll(lblDepFederais, scrlDepFederais);
            }
        } catch (SQLException e) {
            System.err.println("Não foi possivel buscar \"Dep Federal\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar Icone \"Dep Federal\"");
        }

    }

    //CRIAR ICONES DEP. ESTADUAIS
    private void carregarDepEstaduais(int idEstado) {
        int cont = 0;
        iconesDepEstaduais = new ArrayList<>();
        try {

            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=5 and fk_estado_fed=?");
            stmt.setInt(1, idEstado);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Node teste = FXMLLoader.load(getClass().getResource("/interfaces/exibicao/IconePolitico.fxml"));

                int id = rs.getInt("pk_politico");

                /**
                 * -> Segue, a cada loop, para o método "Initialize" da classe
                 * "IconePoliticoController"
                 */
                teste.setId("" + id);
                IconePoliticoController.getInstance().setDados(id);

                teste.setOnMouseClicked(event -> {
                    //System.out.println("ID Clicado: " + id);
                    try {
                        paginaPerfil = FXMLLoader.load(getClass().getResource("/interfaces/ViewPerfil.fxml"));
                        System.out.println("Criou uma página de Perfil");
                        ViewPerfilController.getInstance().carregarPolitico(id);
                        ViewPrincipalController.getInstance().adicionarTelasFilhas(paginaPerfil);
                    } catch (IOException e) {
                        System.err.println("Não foi possivel carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });
                iconesDepEstaduais.add(teste);
                boxDepEstaduais.getChildren().add(iconesDepEstaduais.get(cont));
                cont++;
            }
            if (iconesDepEstaduais.isEmpty()) {
                boxCargos.getChildren().removeAll(lblDepEstaduais, scrlDepEstaduais);
            }
        } catch (SQLException e) {
            System.err.println("Não foi possivel buscar \"Dep Estadual\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar Icone \"Dep Estadual\"");
        }
        

    }

    /*
    public ArrayList<Node> getIconesGovernadores() {
        return iconesGovernadores;
    }

    public ArrayList<Node> getIconesSenadores() {
        return iconesSenadores;
    }

    public ArrayList<Node> getIconesDepFederais() {
        return iconesDepFederais;
    }

    public ArrayList<Node> getIconesDepEstaduais() {
        return iconesDepEstaduais;
    }
     */
}
