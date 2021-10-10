package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Vote", value = "/vote")
public class VoteServlet extends HttpServlet {


   public void init() {
      // Do required initialization
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.

      String[] str = request.getParameter("candidats").split(" ");

      Candidat candidat = new Candidat(
         request.getParameter("candidats") != null ? str[0] : "", //nom
         request.getParameter("candidats") != null ? str[1] : ""); //prenom

      Bulletin bulletin = new Bulletin(candidat);
      Ballot ballot = new Ballot(bulletin); // on crée un ballot de retour


      ServletContext requestContext = request.getServletContext();

      requestContext.setAttribute("ballot", ballot);
      requestContext.setAttribute("bulletin", bulletin);

      //on met a jour le contexte des servlets i.e. du serveur
      List<Bulletin> bulletins = (List<Bulletin>)getServletContext().getAttribute("bulletins");
      Map<String, Ballot>  ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");
      bulletins.add(bulletin);
      getServletContext().setAttribute("bulletins", bulletins);
      ballots.put(request.getParameter("candidats"), ballot);
      getServletContext().setAttribute("ballots", ballots);

      //on renvoie la réponse
      request.getRequestDispatcher("ballot.jsp").forward(request, response);

   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.sendRedirect("index.html");
   }

   public void destroy() {
      // do nothing.
   }
}