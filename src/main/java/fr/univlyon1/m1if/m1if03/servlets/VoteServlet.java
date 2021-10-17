package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Vote", value = "/putvote")
public class VoteServlet extends HttpServlet {

   public void init() {
      // Do required initialization
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");
      HttpSession session = request.getSession(false);
      // Actual logic goes here.

      String[] str = request.getParameter("candidats").split(" ");

      Candidat candidat = new Candidat(
         request.getParameter("candidats") != null ? str[0] : "", //nom
         request.getParameter("candidats") != null ? str[1] : ""); //prenom


      //on met a jour le contexte des servlets i.e. du serveur

      Map<String, Ballot>  ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");

      User current = (User)request.getSession().getAttribute("user");

      if(!ballots.containsKey(current.getLogin())) {  //cannot add if already exists

         Bulletin bulletin = new Bulletin(candidat); // on instancie le bulletin de vote
         session.setAttribute("bulletin", bulletin);
         session.setAttribute("ballot", new Ballot(bulletin));
         List<Bulletin> bulletins = (List<Bulletin>)getServletContext().getAttribute("bulletins");
         ballots.put(current.getLogin(), new Ballot(bulletin));
         bulletins.add(bulletin);
         getServletContext().setAttribute("bulletins", bulletins);
         getServletContext().setAttribute("ballots", ballots);
         System.out.println(current.getLogin() + " just voted.");

      } else {
         System.out.println("Cannot create the ballot, cause : " + current.getLogin() +" already voted");
      }
      request.getRequestDispatcher("/election/vote").forward(request, response);
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.sendRedirect(getServletContext().getContextPath() + "/index.html");
   }

   public void destroy() {
      // do nothing.
   }
}