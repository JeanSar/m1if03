import javax.servlet.http.*;  
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*; 

@WebServlet(name = "DeleteVote", value = "/deleteVote")
public class DeleteVoteServlet extends HttpServlet {
 
    private String message, url;
 
    public void init() throws ServletException {
       // Do required initialization
       url = "resultats.jsp";
       message = "Servlet qui traite les données du formulaire envoyé depuis ballot.jsp";
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
       
       // Set response content type
       response.sendRedirect(url);
    }
 
    public void destroy() {
       // do nothing.
    }
 }