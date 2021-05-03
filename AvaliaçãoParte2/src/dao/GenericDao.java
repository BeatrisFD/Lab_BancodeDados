package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDao implements IGenericDao {
	private static GenericDao instancia;
	private  Connection c;
	
	synchronized public static GenericDao getInstancia() {
		if(instancia == null) instancia = new GenericDao();
		return instancia;
	}
	
	private GenericDao() {
		try {
			this.c=createConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection(){
		return c;
	}
	
	private Connection createConnection() throws ClassNotFoundException, SQLException{
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:jtds:sqlserver://127.0.0.1:1433;DatabaseName=LBD_AV1;namedPipes=true",
				"raj","123");
		System.out.println("Conexao OK");
		return c;
	}
}

	
