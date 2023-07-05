package back.metier;
import java.sql.Timestamp;
import java.util.Date;

import back.db.ProduitDb;
import util.BizException;
import util.Connexion;

public class Produit {

	private String codeProduit;
	private String libelleProduit;
	private String description;
	private float prix;
	private Timestamp stimestamp;

	public Produit(){}

	public String getCodeProduit() { return codeProduit; }
	public String getLibelleProduit() { return libelleProduit; }
	public String getDescription() { return description; }
	public float getPrix() { return prix; }
	public Timestamp getStimestamp() { return stimestamp; }

	public void setCodeProduit(String codeProduit){ this.codeProduit = codeProduit; }
	public void setLibelleProduit(String libelleProduit){ this.libelleProduit = libelleProduit; }
	public void setDescription(String description){ this.description = description; }
	public void setPrix(float prix){ this.prix = prix; }
	public void setStimestamp(Timestamp stimestamp){ this.stimestamp = stimestamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"codeProduit : " + codeProduit + "\n" + 
			"libelleProduit : " + libelleProduit + "\n" + 
			"description : " + description + "\n" + 
			"prix : " + prix + "\n" + 
			"stimestamp : " + stimestamp + "\n" + 
			 super.toString();
		return message;
	}
	public Produit select(Connexion c) throws BizException {
		return ProduitDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = ProduitDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = ProduitDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = ProduitDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return codeProduit;
	}
}

