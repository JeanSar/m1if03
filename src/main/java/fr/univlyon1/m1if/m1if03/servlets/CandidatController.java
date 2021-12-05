package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.classes.Candidat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CandidatController", urlPatterns = {"/candidats", "/candidats/*"})
public class CandidatController extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        String subPath = path.substring(10); // on enlève /candidats
        System.out.println("le sous path controller = "+ subPath);

        //response.sendRedirect(context.getContextPath() + "/index.html");
        // TODO : manage actions in switch on the subPath parsing
        switch (subPath) {
            case "":
                try {
                    assert(context.getAttribute("candidats")!= null);
                    ObjectMapper om = new ObjectMapper();
                    String toAdd = "http://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                    List<String> noms = new ArrayList<>();
                    for(String s : ((Map<String, Candidat>) context.getAttribute("candidats")).keySet()) {
                        noms.add(toAdd + s.replace(' ', '_'));
                    }
                    String json = om.writeValueAsString(noms);

                    response.setContentType("application/json");
                    response.getWriter().print(json);
                    response.getWriter().flush();
                } catch( IOException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
                break;
            case "/noms":
                try {
                    assert(context.getAttribute("candidats")!= null);
                    ObjectMapper om = new ObjectMapper();

                    List<String> noms = new ArrayList<>(((Map<String, Candidat>) context.getAttribute("candidats")).keySet());

                    String json = om.writeValueAsString(noms);
                    response.setContentType("application/json");
                    response.getWriter().print(json);
                    response.getWriter().flush();
                } catch( IOException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
                break;
            default:
                request.getRequestDispatcher(subPath).forward(request, response);
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        processRequest(request, response);
    }
}
