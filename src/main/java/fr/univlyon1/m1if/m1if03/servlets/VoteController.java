package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VoteController", urlPatterns = {"/vote", "/vote/*"})
public class VoteController extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        /*System.out.println("\nPath (vote controller) : " + path);
        System.out.println("In Context : " + context.getContextPath());
        System.out.println("From referer : " + request.getHeader("Referer"));*/

        String subPath = path.substring(5);

        //check if the path isn't looping
        if(subPath.startsWith("/vote")) {
            response.sendError(404);
        }

        switch (subPath) {
            case "/":
            case "":
                request.getRequestDispatcher("/WEB-INF/components/vote.jsp").include(request, response);
                break;
            case "/ballot":
                request.getRequestDispatcher("/WEB-INF/components/ballot.jsp").include(request, response);
                break;
            default:
                request.getRequestDispatcher(subPath).include(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        processRequest(request, response);
    }
}