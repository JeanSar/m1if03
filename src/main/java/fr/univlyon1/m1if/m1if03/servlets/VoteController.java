package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        /*
        if(session.getAttribute("pseudo")==null) {
            response.sendRedirect(request.getContextPath() + "/init");
        }

        else {
            Billet billet = ((GestionBillets) getServletContext().getAttribute("billets"))
                    .getBilletByTitre(request.getPathInfo().substring(1));
            if (billet != null) {
                request.setAttribute("billet", billet);
                request.getRequestDispatcher("/WEB-INF/jsp/billet.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/init");
            }
        }

        */
    }

}