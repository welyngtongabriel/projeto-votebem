package database_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

public class UsuarioDao {

    private Connection conn;

    public UsuarioDao() {
        this.conn = DBConexao.getConexao();
    }

    public boolean add(Usuario p) {
        String sql = "INSERT INTO usuario(nome, login, senha, sexo, email, acesso) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getUsuario());
            stmt.setString(3, p.getSenha());
            stmt.setString(4, p.getSexo());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, "user");
            stmt.execute();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Usuario> getList() {
        List<Usuario> usuarios = new ArrayList();
        String sql = "SELECT * FROM usuario";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("pk_usuario"));//essa string é o nome da coluna do banco
                user.setNome(rs.getString("nome"));
                user.setUsuario(rs.getString("login"));
                user.setSenha(rs.getString("senha"));
                user.setSexo(rs.getString("sexo"));
                user.setEmail(rs.getString("email"));
                usuarios.add(user);
            }
            stmt.close();
            rs.close();
            conn.close();

        } catch (SQLException ex) {
            return null;
        }
        return usuarios;
    }

}
