import javax.servlet.http.*;  
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;  

@WebServlet(name = "Vote", value = "/vote")
public class VoteServlet extends HttpServlet {
 
    private String message;
 
    public void init() throws ServletException {
       // Do required initialization
       message = "Servlet qui traite les données du formulaire envoyé depuis vote.jsp";
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
       
       // Set response content type
       response.setContentType("text/html");
 
       // Actual logic goes here.
       PrintWriter out = response.getWriter();
       out.println("<h1>" + message + "</h1>");
    }
 
    public void destroy() {
       // do nothing.
    }
 }