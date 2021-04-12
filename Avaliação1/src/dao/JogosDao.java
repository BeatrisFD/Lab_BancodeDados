package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.Jogo;
import model.Time;

public class JogosDao implements IJogosDao {

	@Override
	public List<Jogo> buscarTodosJogos() {
		List<Jogo> lista = new LinkedList<Jogo>();
		String sql = "SELECT * FROM jogos";
		try {
			TimesDao td = new TimesDao();
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Time a = td.getTimesByCod(rs.getInt("codigo_timeA"));
				Time b = td.getTimesByCod(rs.getInt("codigo_timeB"));
				Jogo j = new Jogo(a,b, rs.getInt("gols_timeA"), rs.getInt("gols_timeB"), (rs.getDate("data_jogo").toLocalDate()));
				lista.add(j);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Jogo> buscarJogosPorData(LocalDate dia) {
		List<Jogo> lista = new LinkedList<Jogo>();
		String sql = "SELECT * FROM jogos WHERE data_jogo = ?";
		try {
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			java.sql.Date d = java.sql.Date.valueOf(dia);
			System.out.println(d);
			ps.setDate(1,d);
			ResultSet rs = ps.executeQuery();
			
			TimesDao td = new TimesDao();
			while(rs.next()) {
				System.out.println("A");
				Time a = td.getTimesByCod(rs.getInt("codigo_timeA"));
				Time b = td.getTimesByCod(rs.getInt("codigo_timeB"));
				Jogo j = new Jogo(a, b, rs.getInt("gols_timeA"), rs.getInt("gols_timeB"), rs.getDate("data_jogo").toLocalDate());
				lista.add(j);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}

	@Override
	public void adicionarJogo(Jogo jogo) {
		String sql = "INSERT INTO jogos (codigo_timeA,codigo_timeB,gols_timeA,gols_timeB,data_jogo) "
				+ "VALUES(?,?,?,?,?) ";
		try {
			PreparedStatement ps  = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ps.setInt(1, jogo.getTimeA().getCodTime());
			ps.setInt(2, jogo.getTimeB().getCodTime());
			ps.setInt(3, jogo.getGolsTimeA());
			ps.setInt(4, jogo.getGolsTimeB());
			ps.setDate(5,   java.sql.Date.valueOf(jogo.getData()));
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void limparJogos() {
				try {
					String sql = "DELETE FROM jogos";
					PreparedStatement stmt = GenericDao.getInstancia().getConnection().prepareStatement(sql);
					stmt.executeUpdate();
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}

}
