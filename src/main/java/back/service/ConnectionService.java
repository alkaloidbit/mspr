package back.service;

import java.util.ArrayList;

import back.db.RoleDb;
import back.metier.Role;
import util.BizException;
import util.Connexion;

public class ConnectionService {

	private static ConnectionService singleton = null;
	
	private ConnectionService() {}

	public static ConnectionService getConnectionService() {
		if (singleton == null) singleton = new ConnectionService();
		return singleton;
	}
	
	public ArrayList<Role> getAllRoles(Connexion c) throws BizException {
		return RoleDb.getAll(c);	
	}
	
	public ArrayList<Role> getAllRoles() throws BizException {
		ArrayList<Role> result = new ArrayList<Role>();
		Connexion con = new Connexion();
		try {
			result = this.getAllRoles(con);
			con.close();
			return result;
		} catch (BizException be) {
			be.printStackTrace();
			con.close();
			throw be;
		}
	}
	public Role getRoleByKey(Connexion c, Role role) throws BizException {
		return RoleDb.getByKey(c, role);
	}
	
	public Role getRoleByKey(Role role) throws BizException {
		Role result = new Role();
		Connexion con = new Connexion();
		try {
			result = this.getRoleByKey(con, role);
			con.close();
			return result;
		} catch (BizException be) {
			be.printStackTrace();
			con.close();
			throw be;
		}
	}
	/*
	public LoginBean isAuthenticated(LoginBean lb) throws BizException {
		
		LoginBean result;
		Connexion con = new Connexion();
		
		try {
			System.out.println("lb avant " + lb);
			result = isAuthenticated(lb, con);
			System.out.println("lb apres " + result);
			con.close();
			return result;
		} catch (BizException be) {
			con.close();
			throw be;
		}
		
	}
	public LoginBean isAuthenticated(LoginBean lb, Connexion con) throws BizException {
		
		LoginBean result = new LoginBean();
		try {
			Connection connection = ConnectionHome.getHome().getUserByKey(loginBeanToMetier(lb), con);
			if (connection.getMotp() != null && 
					lb.getPasswordMD5().compareTo(connection.getMotp()) == 0) {
				result.setLogin(connection.getLogin());
				result.setCodeRole(connection.getCodeRole());
				result.setAuthenticate(Boolean.TRUE);
			}
			return result;			
		} catch (BizException be) {
			throw be;
		}
		
	}
	*/
	
}
