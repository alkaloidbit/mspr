package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	
	private final String CLASSE = "Connexion";
	private Log l = Log.getLog();
	private Connection c;
	
	public Connexion(){	this.connect();	}
	
	public Connection getConnection() { return c; }
	
	public void beginTransaction() {
		try { c.setAutoCommit(false); }
		catch (SQLException sqle) {	l.println(sqle, CLASSE); }
	}
	
	public void endTransaction() {
		try {
			c.commit();
			c.setAutoCommit(true);
			c.close();
		}
		catch (SQLException sqle) { l.println(sqle, CLASSE); }
		finally {
			try { c.close(); }
			catch (SQLException sqle) { l.println(sqle, CLASSE); }
		}	
	}
	
	public void rollBack() {
		try { c.rollback(); }
		catch (SQLException sqle) { l.println(sqle, CLASSE); }
	}
	
	public void close() {
		try { c.close(); }
		catch (SQLException sqle) { l.println(sqle, CLASSE); }
	}
	
	private void connect() {
		try {
			c = DriverManager.getConnection(Proprietes.getSingleton().getProperty("database") + "?" +
											"user=" + Proprietes.getSingleton().getProperty("user")
											+ "&password=" + Proprietes.getSingleton().getProperty("password"));
		}
		catch (Exception e) {
			l.println("Error loading driver", CLASSE);
			l.println(e, CLASSE);
		}
	}
}
