package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.Utilisateur;

public class UtilisateurDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public UtilisateurDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT LOGIN, PSW, CODE_ROLE, STIMESTAMP FROM utilisateur");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT LOGIN, PSW, CODE_ROLE, STIMESTAMP FROM utilisateur " + 
		"WHERE LOGIN = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE utilisateur " + 
		"SET 		PSW = ?, " +  
		"CODE_ROLE = ? " + 
		"WHERE LOGIN = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO utilisateur " + 
		"(LOGIN, PSW, CODE_ROLE) " + 
		"values(?, ?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM utilisateur " + 
		"WHERE LOGIN = ? "); 
	}
	public static int deleteByKey(Connexion c, Utilisateur t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setString(1, t.getLogin());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setString(1, t.getLogin());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, Utilisateur t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setString(1, t.getLogin());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setString(1, t.getLogin());
			insert.setString(2, t.getPsw());
			insert.setString(3, t.getCodeRole());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, Utilisateur t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setString(1, t.getLogin());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setString(1, t.getPsw());
			updateByKey.setString(2, t.getCodeRole());
			updateByKey.setString(3, t.getLogin());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static Utilisateur getByKey(Connexion c, Utilisateur t) throws BizException {
		ResultSet rs = null;
		Utilisateur result = new Utilisateur();
		try {
			statementSelectByKey(c);
			selectByKey.setString(1, t.getLogin());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setLogin(rs.getString(1));
				result.setPsw(rs.getString(2));
				result.setCodeRole(rs.getString(3));
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
				Utilisateur t = new Utilisateur();
				t.setLogin(rs.getString(1));
				t.setPsw(rs.getString(2));
				t.setCodeRole(rs.getString(3));
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
