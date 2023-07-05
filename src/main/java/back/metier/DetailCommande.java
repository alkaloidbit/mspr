package back.metier;

import java.sql.Timestamp;
import java.util.Date;

import back.db.DetailCommandeDb;
import util.BizException;
import util.Connexion;

public class DetailCommande {

	private int idCommande;
	private String codeProduit;
	private int quantite;
	private Timestamp stimestamp;

	public DetailCommande(){}

	public int getIdCommande() { return idCommande; }
	public String getCodeProduit() { return codeProduit; }
	public int getQuantite() { return quantite; }
	public Timestamp getStimestamp() { return stimestamp; }

	public void setIdCommande(int idCommande){ this.idCommande = idCommande; }
	public void setCodeProduit(String codeProduit){ this.codeProduit = codeProduit; }
	public void setQuantite(int quantite){ this.quantite = quantite; }
	public void setStimestamp(Timestamp stimestamp){ this.stimestamp = stimestamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"idCommande : " + idCommande + "\n" + 
			"codeProduit : " + codeProduit + "\n" + 
			"quantite : " + quantite + "\n" + 
			"stimestamp : " + stimestamp + "\n" + 
			 super.toString();
		return message;
	}
	public DetailCommande select(Connexion c) throws BizException {
		return DetailCommandeDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = DetailCommandeDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = DetailCommandeDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = DetailCommandeDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return String.valueOf(idCommande) + codeProduit;
	}
}

