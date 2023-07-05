package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.Role;

public class RoleDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public RoleDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT CODE_ROLE, LIBELLE_ROLE, STIMESTAMP FROM role");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT CODE_ROLE, LIBELLE_ROLE, STIMESTAMP FROM role " + 
		"WHERE CODE_ROLE = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE role " + 
		"SET 		LIBELLE_ROLE = ? " + 
		"WHERE CODE_ROLE = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO role " + 
		"(CODE_ROLE, LIBELLE_ROLE) " + 
		"values(?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM role " + 
		"WHERE CODE_ROLE = ? "); 
	}
	public static int deleteByKey(Connexion c, Role t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setString(1, t.getCodeRole());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(3).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setString(1, t.getCodeRole());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, Role t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setString(1, t.getCodeRole());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setString(1, t.getCodeRole());
			insert.setString(2, t.getLibelleRole());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, Role t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setString(1, t.getCodeRole());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(3).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setString(1, t.getLibelleRole());
			updateByKey.setString(2, t.getCodeRole());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static Role getByKey(Connexion c, Role t) throws BizException {
		ResultSet rs = null;
		Role result = new Role();
		try {
			statementSelectByKey(c);
			selectByKey.setString(1, t.getCodeRole());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setCodeRole(rs.getString(1));
				result.setLibelleRole(rs.getString(2));
				result.setStimestamp(rs.getTimestamp(3));
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
				Role t = new Role();
				t.setCodeRole(rs.getString(1));
				t.setLibelleRole(rs.getString(2));
				t.setStimestamp(rs.getTimestamp(3));
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
