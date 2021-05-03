package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.Jogo;
import model.JogoQuartas;
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
		String sql = "INSERT INTO jogos (codigo_timeA,codigo_timeB,data_jogo) "
				+ "VALUES(?,?,?) ";
		try {
			PreparedStatement ps  = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ps.setInt(1, jogo.getTimeA().getCodTime());
			ps.setInt(2, jogo.getTimeB().getCodTime());
			ps.setDate(3,   java.sql.Date.valueOf(jogo.getData()));
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

	@Override
	public List<JogoQuartas> buscarQuartas() {
		List<JogoQuartas> lista = new LinkedList<JogoQuartas>();
		String sql = "SELECT * FROM DBO.fn_gerarQuartas()";
		try {
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				JogoQuartas jq= new JogoQuartas(rs.getString("nome_timeA"), rs.getString("nome_timeB"));
				lista.add(jq);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	@Override
	public void atualizarJogo(Jogo jogo) {
		String sql = "Update jogos set gols_timeA = ? , gols_timeB = ? "
				+ "WHERE codigo_timeA = ? AND codigo_timeB = ? AND data_jogo = ?";
			
		System.out.println(jogo.getData()+"B");
		System.out.println(jogo.getGolsTimeA());
		System.out.println(jogo.getGolsTimeB());
		System.out.println(jogo.getTimeA().getCodTime());
		System.out.println(jogo.getTimeB().getCodTime());
		try {
			PreparedStatement stmt = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			stmt.setInt(1, jogo.getGolsTimeA());
			stmt.setInt(2, jogo.getGolsTimeB());
			stmt.setInt(3, jogo.getTimeA().getCodTime());
			stmt.setInt(4, jogo.getTimeB().getCodTime());
			stmt.setDate(5,java.sql.Date.valueOf(jogo.getData()));
			stmt.executeUpdate();
			stmt.close();
			System.out.println("C");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
