package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IGenericDao {
	public Connection getConnection();
}
