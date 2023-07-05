package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Proprietes extends Properties{
	
	private static final long serialVersionUID = 1L;
	private final String CHEMIN = "db.properties";
	private final String CLASSE = "Proprietes";
    private static Proprietes singleton = null;
    private Log l = Log.getLog();
    
    private Proprietes() {}
    
    public static Proprietes getSingleton() {
        if (singleton == null) {
            singleton = new Proprietes().getProprietes();
        }
        return singleton;
    }
    
    public String toString() {
    	String s = "";
    	s = s + "Database : " + this.getProperty("database") + "\n";
    	s = s + "Driver : " + this.getProperty("driver") + "\n";
    	s = s + "User : " + this.getProperty("user") + "\n";
    	s = s + "Password : " + this.getProperty("password") + "\n";
    	return s;
    }
    
    private Proprietes getProprietes() {
        try {                
            FileInputStream fis = new FileInputStream(CHEMIN);
            this.load(fis);
            fis.close();
            return this;
        }
        catch (IOException ioe) {
			System.err.println("Loading error : " + CHEMIN);
			l.println(ioe, CLASSE);
			return null;
        }
    }
}
