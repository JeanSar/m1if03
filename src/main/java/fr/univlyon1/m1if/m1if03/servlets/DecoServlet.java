package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;  

@WebServlet(name = "Deco", value = "/deco")
public class DecoServlet extends HttpServlet {

 
    public void init(ServletConfig config) throws ServletException {
        // Cette instruction doit toujours être au début de la méthode init() pour pouvoir accéder à l'objet config.
        super.init(config);
        //ServletContext context = config.getServletContext();
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws IOException {
        try {
            HttpSession session = request.getSession(true); //on récupère la session
            session.invalidate();
            request.getRequestDispatcher("/election").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la déconnexion");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
 
    public void destroy() {
       // do nothing.
    }
 }