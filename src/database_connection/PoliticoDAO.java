package database_connection;

import modelo.Politico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoliticoDAO {

    public static List<Politico> getPolitico(int id) {
        List<Politico> politicos = new ArrayList<>();

        Connection conexao = DBConexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Politico po = new Politico();
            stmt = conexao.prepareStatement("SELECT politico.pk_politico, politico.nome, politico.n_politico, cargo.nome, partido.sigla FROM vote_bem.politico "
                    + "inner join cargo on politico.fk_cargo = cargo.pk_cargo "
                    + "inner join partido on politico.fk_partido = partido.pk_partido "
                    + "where pk_politico=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            //Pegando do banco
            while (rs.next()) {
                po.setIdPolitico(rs.getInt("politico.pk_politico"));
                po.setCargoPolitico(rs.getString("cargo.nome"));
                po.setNomePolitico(rs.getString("politico.nome"));
                po.setNumeroPolitico(rs.getInt("politico.n_politico"));
                po.setPartidoPolitico(rs.getString("partido.sigla"));
            }
            if(po.getCargoPolitico().equals("Prefeito") || po.getCargoPolitico().equals("Vereador")){
                stmt = conexao.prepareStatement("SELECT cidade.nome, estado_est.nome FROM vote_bem.politico "
                        + "inner join cidade on politico.fk_cidade = cidade.pk_cidade "
                        + "inner join estado_est on cidade.fk_estado_est = estado_est.pk_estado_est "
                        + "where pk_politico = ? ");
                        stmt.setInt(1, id);
                        rs = stmt.executeQuery();
                        while(rs.next()){
                            po.setCidadePolitico(rs.getString("cidade.nome"));
                            po.setEstadoPolitico(rs.getString("estado_est.nome"));
                        }
            } else if(po.getCargoPolitico().equals("Governador") || po.getCargoPolitico().equals("Senador") || po.getCargoPolitico().equals("Deputado Estadual") || po.getCargoPolitico().equals("Deputado Federal")){
                stmt = conexao.prepareStatement("SELECT estado_fed.nome from vote_bem.politico "
                        + "inner join estado_fed on politico.fk_estado_fed = estado_fed.pk_estado_fed "
                        + "where pk_politico = ? ");
                        stmt.setInt(1, id);
                        rs = stmt.executeQuery();
                        while(rs.next()){
                            po.setEstadoPolitico(rs.getString("estado_fed.nome"));
                        }
            }
            
            //Adicona ao ArrayList
            politicos.add(po);

        } catch (SQLException ex) {
            Logger.getLogger(PoliticoDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return politicos;
    }

}
