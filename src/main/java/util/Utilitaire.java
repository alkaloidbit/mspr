package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilitaire {
	
	private static Utilitaire singleton = null;
		
	private Utilitaire() {}
	
	public static Utilitaire getSingleton() {
		if (singleton == null) singleton = new Utilitaire();
		return singleton;
	} 

	public static Date getDateAmericaine(String date) throws BizException {
		try {
			return new Date(date.substring(6, 10) + "/" + date.substring(3, 5) + "/" + date.substring(0, 2));
		} catch (Exception e) { throw new BizException("Wrong date format."); }
	}
	
	public static Date getDateEuropeenne(String date) throws BizException {
		try {
			return new Date(date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 3));
		} catch (Exception e) { throw new BizException("Wrong date format."); }
	}
	
	public static String getDateEuropeenne(Date date) throws BizException {
		try {
			StringBuffer d = (new SimpleDateFormat("dd/MM/yyyy")).format(date, new StringBuffer(), new FieldPosition(DateFormat.LONG));
			return d.toString();
		} catch (Exception e) { throw new BizException("Wrong date format."); }
	}
	
	public static boolean createDir() throws BizException, IOException {
		File f1 = new File("ObjetDb");
		File f2 = new File("ObjetMetier");
		return f1.mkdir() && f2.mkdir();
	}
	
	public static int writeFile(String path, String name, String contenu) throws BizException, IOException {
		FileOutputStream fos = null;
		PrintStream ps = null;
		try {
			File f = new File(path + name);
			fos = new FileOutputStream(f, false);
			ps = new PrintStream(fos, false);
			ps.println(contenu);
	    	ps.flush();
	    	ps.close();
	    	fos.close();
	    	return 0;
		} catch (Exception e) {
			ps.close();
	    	fos.close();
	    	throw new BizException("File Error : " + path + "/" + name);
		}
	}
	
	public static String _getFormatMetier(String sv) {
		String s = new String(sv);
		s = s.toLowerCase();
		String gauche = s.substring(0, 1);
		String droite = s.substring(1, s.length());
		gauche = gauche.toUpperCase();
		return gauche + droite;
	}
	
	public static String getFormatMetier(String sv) {
		String result = "";
		String t = sv.toLowerCase();
		result = t.substring(0, 1).toUpperCase();
		t = t.substring(1);
		for (int i = 0; i < t.length(); i++) {
			if (t.substring(i, i + 1).compareTo("_") != 0) {
				result = result + t.substring(i, i + 1);
			}
			else {
				result = result + t.substring(i + 1, i + 2).toUpperCase();
				i = i + 1;
			}
		}
		return result;
	}
	
	public static String getFormatAttribut(String sv) {
		String result = "";
		String t = sv.toLowerCase();
		result = t.substring(0, 1);
		t = t.substring(1);
		for (int i = 0; i < t.length(); i++) {
			if (t.substring(i, i + 1).compareTo("_") != 0) {
				result = result + t.substring(i, i + 1);
			}
			else {
				result = result + t.substring(i + 1, i + 2).toUpperCase();
				i = i + 1;
			}
		}
		return result;
	}
	
	public static String transformeNom(String sv) {
		String result;
		if (sv.contains("_")) {
			String gauche = "";
			String droite = "";
			int i = 0;
			while (sv.charAt(i) != '_') {
				gauche = gauche + sv.charAt(i);
				i++;
			}
			i++;
			while(i < sv.length()) {
				droite = droite + sv.charAt(i);
				i++;
			}
			result = getFormatMetier(gauche);
			result = result + getFormatMetier(droite);
		}
		else { result = getFormatMetier(sv); }
		return result;
	}
	public static String getStringMD5(String pString) throws BizException {
		
		String result = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(pString.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// Get complete hashed password in hex format
			result = sb.toString();
			return result;
			
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
			throw new BizException (nsae.getMessage());
		}
	}
}
