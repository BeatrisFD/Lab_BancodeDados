package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.List;
import java.util.Collections;

import model.Grupo;
import model.Time;

public class GruposDao implements IGruposDao {

	@Override
	public void deletarGrupos() {
		try {
			String sql = "DELETE FROM GRUPOS";
			PreparedStatement stmt = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void gerarGrupos() {
		try {
			String sql = "{call sp_gerarGrupos()}";
			CallableStatement callstmt = GenericDao.getInstancia().getConnection().prepareCall(sql);
			callstmt.execute();
			callstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public List<Grupo> buscarGrupos() {
		List<Grupo> lista = new LinkedList<>();
		try {
			String sql = "SELECT * FROM grupos,times WHERE times.codigo_time = grupos.codigo_time";
			Grupo grvt[] = new Grupo[4];
			grvt[0]=new Grupo('A', new LinkedList<Time>());
			grvt[1]=new Grupo('B', new LinkedList<Time>());
			grvt[2]=new Grupo('C', new LinkedList<Time>());
			grvt[3]=new Grupo('D', new LinkedList<Time>());
			PreparedStatement ps = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Time t = new Time(rs.getInt("codigo_time"),rs.getString("nome_time"),rs.getString("cidade"),rs.getString("estadio"));
				char c = rs.getString("grupo").charAt(0);
				grvt[(c-65)].addTimes(t);
			}
			for (Grupo grupo : grvt) {
				lista.add(grupo);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

}
