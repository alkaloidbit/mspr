package back.metier;

import java.sql.Timestamp;
import java.util.Date;

import back.db.UtilisateurDb;
import util.BizException;
import util.Connexion;

public class Utilisateur {

	private String login;
	private String psw;
	private String codeRole;
	private Timestamp stimestamp;

	public Utilisateur(){}

	public String getLogin() { return login; }
	public String getPsw() { return psw; }
	public String getCodeRole() { return codeRole; }
	public Timestamp getStimestamp() { return stimestamp; }

	public void setLogin(String login){ this.login = login; }
	public void setPsw(String psw){ this.psw = psw; }
	public void setCodeRole(String codeRole){ this.codeRole = codeRole; }
	public void setStimestamp(Timestamp stimestamp){ this.stimestamp = stimestamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"login : " + login + "\n" + 
			"psw : " + psw + "\n" + 
			"codeRole : " + codeRole + "\n" + 
			"stimestamp : " + stimestamp + "\n" + 
			 super.toString();
		return message;
	}
	public Utilisateur select(Connexion c) throws BizException {
		return UtilisateurDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = UtilisateurDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = UtilisateurDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = UtilisateurDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return login;
	}
}

