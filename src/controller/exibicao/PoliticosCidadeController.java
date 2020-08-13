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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class PoliticosCidadeController implements Initializable {

    //<editor-fold desc="ATRIBUTOS E VARIÁVEIS GLOBAIS">
    @FXML
    private TilePane areaVereadores;

    @FXML
    private AnchorPane pnlExibicaoCidade;

    @FXML
    private Label lblPrefeito, lblVereadores;

    @FXML
    private HBox boxPrefeito;

    //Paginas que serão criadas a partir desta
    private Parent paginaPerfil;

    //Instancia desta Classe
    private static PoliticosCidadeController instance;

    private ArrayList<Node> iconesPrefeito;// = new ArrayList<>();
    private ArrayList<Node> iconesVereadores;// = new ArrayList<>();

    //Variáveis de conexão com o banco
    Connection conexao = DBConexao.getConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    //</editor-fold>
    //
    //CONTRUTOR
    public PoliticosCidadeController() {
        instance = this;
    }

    public static PoliticosCidadeController getInstance() {
        return instance;
    }

    //INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void carregarCargosMunicipais(int idEstado, int idCidade) {
        carregarPrefeito(idEstado, idCidade);
        carregarVereadores(idEstado, idCidade);
    }

    //CRIAR ICONES GOVERNADORES
    private void carregarPrefeito(int idEstado, int idCidade) {

        int cont = 0;

        iconesPrefeito = new ArrayList<>();
        try {

            //Buscas politico
            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=6 and fk_cidade=?");

            stmt.setInt(1, idCidade);
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
                        System.err.println("Não foi possível carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });
                iconesPrefeito.add(iconePolitico);
                boxPrefeito.getChildren().add(iconesPrefeito.get(cont));
                cont++;
            }

        } catch (SQLException e) {
            System.err.println("Não foi possivel buscar \"PREFEITO\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar Icone \"PREFEITO\"");
        }

    }

    private void carregarVereadores(int idEstado, int idCidade) {

        int cont = 0;

        iconesVereadores = new ArrayList<>();
        try {

            //Buscas politico
            stmt = conexao.prepareStatement("SELECT * FROM politico WHERE fk_cargo=7 and fk_cidade=?");

            stmt.setInt(1, idCidade);
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
                        System.err.println("Não foi possível carregar a \"Página Perfil\"!");
                        System.err.println("Erro: " + e);
                    }

                });
                iconesVereadores.add(iconePolitico);
                areaVereadores.getChildren().add(iconesVereadores.get(cont));
                cont++;
            }

        } catch (SQLException e) {
            System.err.println("Não foi possivel buscar \"PREFEITO\"");
            System.err.println("Erro: " + e);
        } catch (IOException e) {
            System.err.println("Não foi possivel carregar Icone \"PREFEITO\"");
        }

    }

    public int getIconesPrefeito() {
        return iconesPrefeito.size();
    }

    public int getIconesVereadores() {
        return iconesVereadores.size();
    }

}
