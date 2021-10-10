package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DeleteVote", value = "/deleteVote")
public class DeleteVoteServlet extends HttpServlet {

   public void init() {
      // Do required initialization
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ServletContext requestContext = request.getServletContext();




      //on met a jour le contexte des servlets i.e. du serveur
      List<Bulletin> bulletins = (List<Bulletin>)getServletContext().getAttribute("bulletins");
      Map<String, Ballot> ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");

      Bulletin b = (Bulletin) requestContext.getAttribute("bulletin");
      bulletins.remove(b);
      getServletContext().setAttribute("bulletins", bulletins);

      ballots.remove(b.getCandidat().getNom());
      getServletContext().setAttribute("ballots", ballots);

      requestContext.setAttribute("ballot", "");
      requestContext.setAttribute("bulletin", "");

      request.getRequestDispatcher("ballot.jsp").forward(request, response);

   }

   public void destroy() {
      // do nothing.
   }
}