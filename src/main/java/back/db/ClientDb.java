package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.Client;

public class ClientDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public ClientDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT CODE_CLIENT, NOM, PRENOM, ADRESSE, CODE_POSTAL, VILLE, STIMESTAMP FROM client");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT CODE_CLIENT, NOM, PRENOM, ADRESSE, CODE_POSTAL, VILLE, STIMESTAMP FROM client " + 
		"WHERE CODE_CLIENT = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE client " + 
		"SET 		NOM = ?, " +  
		"PRENOM = ?, " +  
		"ADRESSE = ?, " +  
		"CODE_POSTAL = ?, " +  
		"VILLE = ? " + 
		"WHERE CODE_CLIENT = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO client " + 
		"(CODE_CLIENT, NOM, PRENOM, ADRESSE, CODE_POSTAL, VILLE) " + 
		"values(?, ?, ?, ?, ?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM client " + 
		"WHERE CODE_CLIENT = ? "); 
	}
	public static int deleteByKey(Connexion c, Client t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setString(1, t.getCodeClient());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(7).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setString(1, t.getCodeClient());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, Client t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setString(1, t.getCodeClient());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setString(1, t.getCodeClient());
			insert.setString(2, t.getNom());
			insert.setString(3, t.getPrenom());
			insert.setString(4, t.getAdresse());
			insert.setString(5, t.getCodePostal());
			insert.setString(6, t.getVille());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, Client t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setString(1, t.getCodeClient());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(7).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setString(1, t.getNom());
			updateByKey.setString(2, t.getPrenom());
			updateByKey.setString(3, t.getAdresse());
			updateByKey.setString(4, t.getCodePostal());
			updateByKey.setString(5, t.getVille());
			updateByKey.setString(6, t.getCodeClient());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static Client getByKey(Connexion c, Client t) throws BizException {
		ResultSet rs = null;
		Client result = new Client();
		try {
			statementSelectByKey(c);
			selectByKey.setString(1, t.getCodeClient());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setCodeClient(rs.getString(1));
				result.setNom(rs.getString(2));
				result.setPrenom(rs.getString(3));
				result.setAdresse(rs.getString(4));
				result.setCodePostal(rs.getString(5));
				result.setVille(rs.getString(6));
				result.setStimestamp(rs.getTimestamp(7));
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
				Client t = new Client();
				t.setCodeClient(rs.getString(1));
				t.setNom(rs.getString(2));
				t.setPrenom(rs.getString(3));
				t.setAdresse(rs.getString(4));
				t.setCodePostal(rs.getString(5));
				t.setVille(rs.getString(6));
				t.setStimestamp(rs.getTimestamp(7));
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
