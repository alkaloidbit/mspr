package back.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Routeur extends HttpServlet {
	
	private static final long serialVersionUID = 0L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		session.removeAttribute("erreur");
		String pageApresErreur = "/jsp/erreur.jsp";
		
		try {
			if ("Connection".equals(request.getParameter("connection")) && "login".equals(request.getParameter("page"))) {
				pageApresErreur = "/login.jsp";
				//login
				String d = (String)request.getParameter("login");
				//password
				d = (String)request.getParameter("password");
				
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/blanc.jsp").forward(request, response);			
			} else 
			//Je viens du menu motp
			if ("menuMotp".equals(request.getParameter("parametre"))) {
				pageApresErreur = "/jsp/motpModification01.jsp";
				if (session.getAttribute("login") == null) {
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				String parent = request.getParameter("parent");
				if (parent.compareTo("null") == 0) { parent = null; }
				getServletConfig().getServletContext().getRequestDispatcher("/jsp/motpModification01.jsp").forward(request, response);
			} else
			//Je viens du menu deconnexion
			if ("menuDeconnexion".equals(request.getParameter("parametre"))) {
				pageApresErreur = "/login.jsp";
				if (session.getAttribute("login") == null) {
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				String parent = request.getParameter("parent");
				if (parent.compareTo("null") == 0) { parent = null; }
				session.invalidate();
				getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			} else				
			if ("motpModification01".equals(request.getParameter("page"))) {
				pageApresErreur = "/jsp/motpModification01.jsp";
				if (session.getAttribute("login") == null) {
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				//Password
				String d = (String)request.getParameter("password");
				getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);			
			} else { getServletConfig().getServletContext().getRequestDispatcher("/jsp/erreur.jsp").forward(request, response); }			
		}
		
		catch(Exception b) {
			try {
				b.printStackTrace();
				String cl = (String)session.getAttribute("codeLangue");
				getServletConfig().getServletContext().getRequestDispatcher(pageApresErreur).forward(request, response);
			} catch (Exception e) { e.printStackTrace(); }
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}
}
