package back.metier;

import java.sql.Timestamp;
import java.util.Date;

import back.db.CommandeDb;
import util.BizException;
import util.Connexion;


public class Commande {

	private int idCommande;
	private String codeClient;
	private Date date;
	private Timestamp stimetamp;

	public Commande(){}

	public int getIdCommande() { return idCommande; }
	public String getCodeClient() { return codeClient; }
	public Date getDate() { return date; }
	public Timestamp getStimestamp() { return stimetamp; }

	public void setIdCommande(int idCommande){ this.idCommande = idCommande; }
	public void setCodeClient(String codeClient){ this.codeClient = codeClient; }
	public void setDate(Date date){ this.date = date; }
	public void setStimestamp(Timestamp stimetamp){ this.stimetamp = stimetamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"idCommande : " + idCommande + "\n" + 
			"codeClient : " + codeClient + "\n" + 
			"date : " + date + "\n" + 
			"stimetamp : " + stimetamp + "\n" + 
			 super.toString();
		return message;
	}
	public Commande select(Connexion c) throws BizException {
		return CommandeDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = CommandeDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = CommandeDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = CommandeDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return String.valueOf(idCommande);
	}
}

