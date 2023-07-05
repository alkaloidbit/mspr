package back.metier;
import java.sql.Timestamp;

import back.db.ClientDb;
import util.BizException;
import util.Connexion;


public class Client {

	private String codeClient;
	private String nom;
	private String prenom;
	private String adresse;
	private String codePostal;
	private String ville;
	private Timestamp stimestamp;

	public Client(){}

	public String getCodeClient() { return codeClient; }
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public String getAdresse() { return adresse; }
	public String getCodePostal() { return codePostal; }
	public String getVille() { return ville; }
	public Timestamp getStimestamp() { return stimestamp; }

	public void setCodeClient(String codeClient){ this.codeClient = codeClient; }
	public void setNom(String nom){ this.nom = nom; }
	public void setPrenom(String prenom){ this.prenom = prenom; }
	public void setAdresse(String adresse){ this.adresse = adresse; }
	public void setCodePostal(String codePostal){ this.codePostal = codePostal; }
	public void setVille(String ville){ this.ville = ville; }
	public void setStimestamp(Timestamp stimestamp){ this.stimestamp = stimestamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"codeClient : " + codeClient + "\n" + 
			"nom : " + nom + "\n" + 
			"prenom : " + prenom + "\n" + 
			"adresse : " + adresse + "\n" + 
			"codePostal : " + codePostal + "\n" + 
			"ville : " + ville + "\n" + 
			"stimestamp : " + stimestamp + "\n" + 
			 super.toString();
		return message;
	}
	public Client select(Connexion c) throws BizException {
		return ClientDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = ClientDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = ClientDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = ClientDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return codeClient;
	}
}

