package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CandidatController", urlPatterns = {"/candidats", "/candidats/*"})
public class CandidatController extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response )  throws IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("Controller Candidats = "+ path);
        String subPath = path.substring(10); // on enlève /candidats
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