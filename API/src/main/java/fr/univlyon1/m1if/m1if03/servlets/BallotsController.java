package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BallotsController", urlPatterns = {"/ballots", "/ballots/*"})
public class BallotsController extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response )
            throws IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());

        String subPath = path.substring(8); // on enlève /ballots
        System.out.println("le sous path controller = "+ subPath);

        response.sendRedirect(context.getContextPath() + "/index.html");
        // TODO : manage actions in switch on the subPath parsing
        /*switch (subPath) {
            case "/":
            case "":

                break;
            default:
                request.getRequestDispatcher(subPath).forward(request, response);
                break;
        }*/
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