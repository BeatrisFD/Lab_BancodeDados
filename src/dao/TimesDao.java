package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import model.Time;

public class TimesDao implements ITimesDao {

	@Override
	public List<Time> getAllTimes() {
		List<Time> lista = new LinkedList<Time>();
		try {
			String sql = "SELECT * FROM TIMES";
			PreparedStatement ps = GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Time t = new Time(rs.getInt("codigo_time"),rs.getString("nome_time"),rs.getString("cidade"),rs.getString("estadio"));
				lista.add(t);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Time getTimesByCod(int cod) {
		String sql = "SELECT * FROM TIMES WHERE codigo_time =?";
		try {
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ps.setInt(1, cod);
			ResultSet rs = ps.executeQuery();
			if ( rs.next()) {
				Time t= new Time(cod,rs.getString("nome_time"),rs.getString("cidade"),rs.getString("estadio"));
				return t;
			}
			
			rs.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
