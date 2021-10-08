import javax.servlet.http.*;  
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;
import java.io.*;  

@WebServlet(name = "Vote", value = "/vote")
public class VoteServlet extends HttpServlet {
 
    private String message;
 
    public void init() throws ServletException {
       // Do required initialization
       message = "Servlet qui traite les données du formulaire envoyé depuis vote.jsp";
    }
 
    public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
       
       // Set response content type
       response.setContentType("text/html");
 
      // Actual logic goes here.
      HttpSession session = request.getSession(true);
      session.setAttribute("vote", new Candidat(
         request.getParameter("candidats") != null ? request.getParameter("candidats") : "", 
         request.getParameter("candidats") != null ? request.getParameter("candidats") : ""));
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