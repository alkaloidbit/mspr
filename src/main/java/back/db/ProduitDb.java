package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.Produit;

public class ProduitDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public ProduitDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT CODE_PRODUIT, LIBELLE_PRODUIT, DESCRIPTION, PRIX, STIMESTAMP FROM produit");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT CODE_PRODUIT, LIBELLE_PRODUIT, DESCRIPTION, PRIX, STIMESTAMP FROM produit " + 
		"WHERE CODE_PRODUIT = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE produit " + 
		"SET 		LIBELLE_PRODUIT = ?, " +  
		"DESCRIPTION = ?, " +  
		"PRIX = ? " + 
		"WHERE CODE_PRODUIT = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO produit " + 
		"(CODE_PRODUIT, LIBELLE_PRODUIT, DESCRIPTION, PRIX) " + 
		"values(?, ?, ?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM produit " + 
		"WHERE CODE_PRODUIT = ? "); 
	}
	public static int deleteByKey(Connexion c, Produit t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setString(1, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(5).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setString(1, t.getCodeProduit());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, Produit t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setString(1, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setString(1, t.getCodeProduit());
			insert.setString(2, t.getLibelleProduit());
			insert.setString(3, t.getDescription());
			insert.setFloat(4, t.getPrix());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, Produit t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setString(1, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(5).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setString(1, t.getLibelleProduit());
			updateByKey.setString(2, t.getDescription());
			updateByKey.setFloat(3, t.getPrix());
			updateByKey.setString(4, t.getCodeProduit());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static Produit getByKey(Connexion c, Produit t) throws BizException {
		ResultSet rs = null;
		Produit result = new Produit();
		try {
			statementSelectByKey(c);
			selectByKey.setString(1, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setCodeProduit(rs.getString(1));
				result.setLibelleProduit(rs.getString(2));
				result.setDescription(rs.getString(3));
				result.setPrix(rs.getFloat(4));
				result.setStimestamp(rs.getTimestamp(5));
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
				Produit t = new Produit();
				t.setCodeProduit(rs.getString(1));
				t.setLibelleProduit(rs.getString(2));
				t.setDescription(rs.getString(3));
				t.setPrix(rs.getFloat(4));
				t.setStimestamp(rs.getTimestamp(5));
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
