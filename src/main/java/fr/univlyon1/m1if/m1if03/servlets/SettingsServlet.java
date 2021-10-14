package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Settings", value = "/settings")
public class SettingsServlet extends HttpServlet {


   public void init() {
      // Do required initialization
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");

      String nom = request.getParameter("nom");
      if (nom != null && !nom.equals("")) {
         User current = (User)request.getSession().getAttribute("user");
         current.setNom((nom));
         request.getRequestDispatcher("settings.jsp").forward(request, response);
      } else {
         response.sendRedirect("index.html");
      }
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.sendRedirect("index.html");
   }

   public void destroy() {
      // do nothing.
   }
}