package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;

@WebServlet(name = "DeleteVote", value = "/deleteVote")
public class DeleteVoteServlet extends HttpServlet {

   public void init() throws ServletException {
      // Do required initialization
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      if (request.getParameter("nomVote") != null) {
         ServletContext context = request.getServletContext();
         context.setAttribute("ballots", "");
         context.setAttribute("bulletins", "");

         request.getRequestDispatcher("ballot.jsp").forward(request, response);
      } else {
         response.sendRedirect("ballot.jsp");
      }
   }

   public void destroy() {
      // do nothing.
   }
}