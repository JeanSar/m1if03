package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//import org.json.simple.JSONObject;

@WebServlet(name = "ResultatsController", urlPatterns = "/resultats")
public class ResultatsController extends HttpServlet {
    ServletContext context;
    public void init(ServletConfig config) throws ServletException {
        // Cette instruction doit toujours être au début de la méthode init() pour pouvoir accéder à l'objet config.
        super.init(config);
        context = config.getServletContext();
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            assert(context.getAttribute("candidats") != null);

            ObjectMapper om = new ObjectMapper();

            List<LinkedHashMap<String, Object>> result = new ArrayList<>();

            Map<String, Integer> votes = new HashMap<>();
            for (String nomCandidat : ((Map<String, Candidat>) context.getAttribute("candidats")).keySet()) {
                votes.put(nomCandidat, 0);
            }
            if(context.getAttribute("bulletins") != null) {
                List<Bulletin> lesBulletins = new ArrayList<>((List<Bulletin>) context.getAttribute("bulletins"));
                for (Bulletin b : lesBulletins) {
                    int score = votes.get(b.getCandidat().getNom());
                    votes.put(b.getCandidat().getNom(), ++score);
                }
            }

            for(String i : votes.keySet()) {
                LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
                tmp.put("nomCandidat",i);
                tmp.put("votes", votes.get(i));
                result.add(tmp);
            }

            String json = om.writeValueAsString(result);
            response.setContentType("application/json");
            response.getWriter().print(json);
            response.getWriter().flush();

        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void destroy() {
       // do nothing.
    }
 }