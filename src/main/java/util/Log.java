package util;

import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.FieldPosition;

public class Log {

	public static final String LOG = "log.txt";
	
	private static Log log = null;
	private File f = new File(Log.LOG);
	private FileOutputStream fos = null;
	private PrintStream ps = null;
	
	private Log() { super(); }
	
	public synchronized static Log getLog () {
        if (log == null) {
			log = new Log();
        	try {
            	log.buildStream();
        	}
        	catch(IOException io) {
        		System.err.println(io);
        		System.err.println("<Error creating log:" + Log.LOG + ">");
				return log;
        	}
        }
		return log;
   	}
	
	public void println(Object message, String classe) {
		if (message == null) message = "null";
    	String e = this.getDate().toString() + "<" + classe + ">" + message.toString();
    	System.out.println(e);
    	ps.println(e);
    	ps.flush();
    }
	
	public void println(Exception erreur, String classe) {
		String e = this.getDate().toString() + "<" + classe + ">";
		System.out.println(e);
		ps.println(e);
    	erreur.printStackTrace();
    	erreur.printStackTrace(ps);
    	ps.flush();
	}
	
	private String getDate() {
		StringBuffer date = (new SimpleDateFormat("dd/MM/yy kk:mm:ss:SSS")).format(new Date(), new StringBuffer(), new FieldPosition(DateFormat.SHORT));
		return("<" + date.toString() + ">");
	}
	
	 private void buildStream() throws FileNotFoundException {
    	fos = new FileOutputStream(Log.LOG, true);
    	ps = new PrintStream(fos, true);
    }	
}
