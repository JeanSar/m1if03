package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;
import fr.univlyon1.m1if.m1if03.utils.CandidatListGenerator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Init", value = "/init")
public class Init extends HttpServlet {
    Map<String, Candidat> candidats = null;
    final Map<String, Ballot>   ballots = new HashMap<>();
    final List<Bulletin> bulletins = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // Cette instruction doit toujours être au début de la méthode init() pour pouvoir accéder à l'objet config.
        super.init(config);
        ServletContext context = config.getServletContext();
        context.setAttribute("ballots", ballots);
        context.setAttribute("bulletins", bulletins);
        try {
            if (candidats == null) {
                candidats = CandidatListGenerator.getCandidatList();
                context.setAttribute("candidats", candidats);
            }
        }   catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
            System.out.println("Applicants list not generated");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // On intercepte le premier appel à Init pour mettre en place la liste des candidats,
        // car en cas d'erreur de chargement, il faut pouvoir renvoyer une erreur HTTP.
        // Fait dans un bloc try/catch pour le cas où la liste des candidats ne s'est pas construite correctement.
        try {
            // Gestion de la session utilisateur
            String login = request.getParameter("login");
            if (login != null && !login.equals("")) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", new User(login,
                        request.getParameter("nom") != null ? request.getParameter("nom") : "",
                        request.getParameter("admin") != null && request.getParameter("admin").equals("on")));
                request.getRequestDispatcher("vote.jsp").forward(request, response);

                Map<String, Ballot>  ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");
                //on test si l'utilisateur à déjà voté
                if(ballots.containsKey(login)) {
                    request.getServletContext().setAttribute("ballot", ballots.get(login));
                    request.getServletContext().setAttribute("bulletin", ballots.get(login).getBulletin());
                } else {
                    request.getServletContext().setAttribute("ballot", "");
                    request.getServletContext().setAttribute("bulletin", "");
                }
                request.getRequestDispatcher("vote.jsp").forward(request, response);
            } else {
                response.sendRedirect("index.html");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect("index.html");
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}