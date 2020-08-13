package database_connection;

import controller.ViewPerfilController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConexao {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/vote_bem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection conexao = null;

    //ABRIR CONEXÃO
    //*Retirei o "Static"
    public static Connection getConexao() {
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o Banco com Sucesso!");
            return conexao;
        } catch (ClassNotFoundException erro) {
            System.err.println("Falha na Conexão com a Classe!");
            System.err.println("ERRO: " + erro.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println("Falha na conexão com o Banco!");
            System.err.println("ERRO: " + e.getMessage());

            ViewPerfilController.getInstance().setLblErro("Error code: #500 Internal Server Error. "+ e.getMessage() +".");
            ViewPerfilController.getInstance().error500ToFront();
            return null;
        }
    }

    //FECHAR CONEXÃO
    public static void closeConnection(Connection conn) {

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Conexão com o \"Banco\" encerrada!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FECHA STATMENT
    public static void closeConnection(Connection conn, PreparedStatement stmt) {

        closeConnection(conn);

        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("Conexão \"PreparedStatement\" encerrada!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FECHA RESULT SET
    public static void closeConnection(java.sql.Connection conn, PreparedStatement stmt, ResultSet rs) {

        closeConnection(conn, stmt);

        try {
            if (rs != null) {
                rs.close();
                System.out.println("Conexão \"ResultSet\" encerrada!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
