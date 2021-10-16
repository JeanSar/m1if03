package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/election/*"})
public class Controller extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        //if (path.toString().contains("secondPage")) {}
        /*System.out.println("\nPath1 : " + path);
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI());
        System.out.println(request.getHeader("Referer"));
         */

        RequestDispatcher dispatcher = request.getRequestDispatcher("Resultats");
        dispatcher.include(request, response);

        //response.sendRedirect("resultats");
    }
}