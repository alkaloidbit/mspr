package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.Commande;

public class CommandeDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public CommandeDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT ID_COMMANDE, CODE_CLIENT, DATE, STIMETAMP FROM commande");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT ID_COMMANDE, CODE_CLIENT, DATE, STIMETAMP FROM commande " + 
		"WHERE ID_COMMANDE = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE commande " + 
		"SET 		CODE_CLIENT = ?, " +  
		"DATE = ?, " +  
		"STIMETAMP = ? " + 
		"WHERE ID_COMMANDE = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO commande " + 
		"(ID_COMMANDE, CODE_CLIENT, DATE, STIMETAMP) " + 
		"values(?, ?, ?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM commande " + 
		"WHERE ID_COMMANDE = ? "); 
	}
	public static int deleteByKey(Connexion c, Commande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setInt(1, t.getIdCommande());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, Commande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setInt(1, t.getIdCommande());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setInt(1, t.getIdCommande());
			insert.setString(2, t.getCodeClient());
			insert.setDate(3, new java.sql.Date(t.getDate().getTime()));
			insert.setTimestamp(4, t.getStimestamp());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, Commande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setString(1, t.getCodeClient());
			updateByKey.setDate(2, new java.sql.Date(t.getDate().getTime()));
			updateByKey.setTimestamp(3, t.getStimestamp());
			updateByKey.setInt(4, t.getIdCommande());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static Commande getByKey(Connexion c, Commande t) throws BizException {
		ResultSet rs = null;
		Commande result = new Commande();
		try {
			statementSelectByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setIdCommande(rs.getInt(1));
				result.setCodeClient(rs.getString(2));
				result.setDate(rs.getDate(3));
				result.setStimestamp(rs.getTimestamp(4));
			}
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static ArrayList getAll(Connexion c) throws BizException {
		ResultSet rs = null;
		ArrayList result = null;
		try {
			statementSelectAll(c);
			rs = selectAll.executeQuery();
			result = new ArrayList();
			rs.beforeFirst();
			while (rs.next()) {
				Commande t = new Commande();
				t.setIdCommande(rs.getInt(1));
				t.setCodeClient(rs.getString(2));
				t.setDate(rs.getDate(3));
				t.setStimestamp(rs.getTimestamp(4));
				result.add(t);
			}
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
}
