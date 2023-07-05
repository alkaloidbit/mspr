package back.metier;

import java.sql.Timestamp;
import java.util.Date;

import back.db.RoleDb;
import util.BizException;
import util.Connexion;


public class Role {

	private String codeRole;
	private String libelleRole;
	private Timestamp stimestamp;

	public Role(){}

	public String getCodeRole() { return codeRole; }
	public String getLibelleRole() { return libelleRole; }
	public Timestamp getStimestamp() { return stimestamp; }

	public void setCodeRole(String codeRole){ this.codeRole = codeRole; }
	public void setLibelleRole(String libelleRole){ this.libelleRole = libelleRole; }
	public void setStimestamp(Timestamp stimestamp){ this.stimestamp = stimestamp; }

	public String toString() {
		String message = "";
		message = message + "\n" +
			"codeRole : " + codeRole + "\n" + 
			"libelleRole : " + libelleRole + "\n" + 
			"stimestamp : " + stimestamp + "\n" + 
			 super.toString();
		return message;
	}
	public Role select(Connexion c) throws BizException {
		return RoleDb.getByKey(c, this);
	}
	public int delete(Connexion c) throws BizException {
		int result = RoleDb.deleteByKey(c, this);
		return result;
	}
	public int update(Connexion c) throws BizException {
		int result = RoleDb.updateByKey(c, this);
		return result;
	}
	public int insert(Connexion c) throws BizException {
		int result = RoleDb.insert(c, this);
		return result;
	}
	public String getKey() {
		return codeRole;
	}
}

