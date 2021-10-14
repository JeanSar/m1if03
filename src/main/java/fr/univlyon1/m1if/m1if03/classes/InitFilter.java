package fr.univlyon1.m1if.m1if03.classes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;


@WebFilter(filterName = "InitFilter" , urlPatterns="/*")
public class InitFilter extends HttpFilter {
    ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //getting the filtered page path
            String path = request.getRequestURI().substring(request.getContextPath().length());
            System.out.println("\nPath1 : " + path);
            System.out.println(request.getPathInfo());
            System.out.println(request.getHeader("Referer"));

            if(!Pattern.compile(
                    "^/((resultats)|(index\\.html)|([/a-z\\-]+\\.css))?$"
            ).matcher(path).matches()){

                // Gestion de la session utilisateur
                String login = request.getParameter("login");
                if (login != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", new User(login,
                            request.getParameter("nom") != null ? request.getParameter("nom") : "",
                            request.getParameter("admin") != null && request.getParameter("admin").equals("on")));

                    Map<String, Ballot> ballots = (Map<String, Ballot>) context.getAttribute("ballots");
                    //on test si l'utilisateur à déjà voté
                    if(ballots.containsKey(login)) {
                        request.getServletContext().setAttribute("ballot", ballots.get(login));
                        request.getServletContext().setAttribute("bulletin", ballots.get(login).getBulletin());
                    } else {
                        request.getServletContext().setAttribute("ballot", "");
                        request.getServletContext().setAttribute("bulletin", "");
                    }
                } else {
                    response.sendRedirect("index.html");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur dans la récupération de la liste des candidats.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
