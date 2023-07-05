package back.db;

import util.Connexion;
import util.BizException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.metier.DetailCommande;

public class DetailCommandeDb {

	private static PreparedStatement selectAll;
	private static PreparedStatement selectByKey;
	private static PreparedStatement updateByKey;
	private static PreparedStatement insert;
	private static PreparedStatement deleteByKey;

	public DetailCommandeDb(){}

	private static void statementSelectAll(Connexion c) throws SQLException {
		selectAll = c.getConnection().prepareStatement(
		"SELECT ID_COMMANDE, CODE_PRODUIT, QUANTITE, STIMESTAMP FROM detail_commande");
	}
	private static void statementSelectByKey(Connexion c) throws SQLException {
		selectByKey = c.getConnection().prepareStatement(
		"SELECT ID_COMMANDE, CODE_PRODUIT, QUANTITE, STIMESTAMP FROM detail_commande " + 
		"WHERE ID_COMMANDE = ? " + 
		"AND CODE_PRODUIT = ? " ); 
 	}
	private static void statementUpdateByKey(Connexion c) throws SQLException {
		updateByKey = c.getConnection().prepareStatement(
		"UPDATE detail_commande " + 
		"SET 		QUANTITE = ? " + 
		"WHERE ID_COMMANDE = ? " +
		"AND CODE_PRODUIT = ? "); 
	}
	private static void statementInsert(Connexion c) throws SQLException {
		insert = c.getConnection().prepareStatement(
		"INSERT INTO detail_commande " + 
		"(ID_COMMANDE, CODE_PRODUIT, QUANTITE) " + 
		"values(?, ?, ?)");
	}
	private static void statementDeleteByKey(Connexion c) throws SQLException {
		deleteByKey = c.getConnection().prepareStatement(
		"DELETE FROM detail_commande " + 
		"WHERE ID_COMMANDE = ? " +
		"AND CODE_PRODUIT = ? "); 
	}
	public static int deleteByKey(Connexion c, DetailCommande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementDeleteByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			selectByKey.setString(2, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			deleteByKey.setInt(1, t.getIdCommande());
			deleteByKey.setString(2, t.getCodeProduit());
			result = deleteByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int insert(Connexion c, DetailCommande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementInsert(c);
			selectByKey.setInt(1, t.getIdCommande());
			selectByKey.setString(2, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) throw new BizException("Data already exists");
			insert.setInt(1, t.getIdCommande());
			insert.setString(2, t.getCodeProduit());
			insert.setInt(3, t.getQuantite());
			result = insert.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static int updateByKey(Connexion c, DetailCommande t) throws BizException {
		ResultSet rs = null;
		int result;
		try {
			statementSelectByKey(c);
			statementUpdateByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			selectByKey.setString(2, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if(rs.next()) {
				if (rs.getTimestamp(4).after(t.getStimestamp())) {
					throw new BizException("Data modified by another user");
				}
			}
			else { throw new BizException("Data modified by another user"); }
			updateByKey.setInt(1, t.getQuantite());
			updateByKey.setInt(2, t.getIdCommande());
			updateByKey.setString(3, t.getCodeProduit());
			result = updateByKey.executeUpdate();
			if (rs != null) rs.close();
			return result;
		}
		catch(SQLException sqle) {
			throw new BizException(sqle.getMessage());
		}
	}
	public static DetailCommande getByKey(Connexion c, DetailCommande t) throws BizException {
		ResultSet rs = null;
		DetailCommande result = new DetailCommande();
		try {
			statementSelectByKey(c);
			selectByKey.setInt(1, t.getIdCommande());
			selectByKey.setString(2, t.getCodeProduit());
			rs = selectByKey.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result.setIdCommande(rs.getInt(1));
				result.setCodeProduit(rs.getString(2));
				result.setQuantite(rs.getInt(3));
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
				DetailCommande t = new DetailCommande();
				t.setIdCommande(rs.getInt(1));
				t.setCodeProduit(rs.getString(2));
				t.setQuantite(rs.getInt(3));
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
