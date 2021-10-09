import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Vote", value = "/vote")
public class VoteServlet extends HttpServlet {

   private String message;

   public void init() throws ServletException {
      // Do required initialization
      message = "Servlet qui traite les données du formulaire envoyé depuis vote.jsp";
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.

      Candidat candidat = new Candidat( 
         request.getParameter("candidats") != null ? request.getParameter("candidats") : "", 
         request.getParameter("candidats") != null ? request.getParameter("candidats") : ""); 
      
      Bulletin bulletin = new Bulletin(candidat); 
      Ballot ballot = new Ballot(bulletin);

      ServletContext context = request.getServletContext();
      context.setAttribute("ballots", ballot);
      context.setAttribute("bulletins", bulletin);

      //il faut enlever ca !! a remplacer par celui du haut 
      HttpSession session = request.getSession(true);
      session.setAttribute("vote", candidat);

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