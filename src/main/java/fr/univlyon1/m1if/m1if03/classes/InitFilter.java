package fr.univlyon1.m1if.m1if03.classes;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@WebFilter(filterName = "InitFilter" , urlPatterns="/*")
public class InitFilter extends HttpFilter {
    ServletContext context;
    final ArrayList<String> uncaught = new ArrayList<>();

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
        //these are files/paths that not needs to be filtered
        uncaught.add("/");
        uncaught.add("/resultats");
        uncaught.add("/index.html");
        uncaught.add("/vote.css");
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //getting the filtered page path
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String referer = request.getHeader("Referer");

        System.out.println("\nPath : " + path);
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI());
        System.out.println(referer); //referer is the requesting entity client side path
        HttpSession session = request.getSession(false);

        // check if the path must be filtered
        boolean isCaught = true;
        for(String p : uncaught) {
            if(p.equals(path)) {
                isCaught = false;
                break;
            }
        }
        if(isCaught) {
            if((session == null) || (session.getAttribute("user") == null)) {
                String login = request.getParameter("login");
                if (login != null) {
                    if (login.equals("")){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        request.getRequestDispatcher("index.html").forward(request, response);
                        return;
                    } else {
                        session = request.getSession(true);
                        session.setAttribute("user", new User(login,
                                request.getParameter("nom") != null ? request.getParameter("nom") : "",
                                request.getParameter("admin") != null
                                        && request.getParameter("admin").equals("on")));

                        Map<String, Ballot> ballots = (Map<String, Ballot>) context.getAttribute("ballots");

                        //on test si l'utilisateur à déjà voté
                        if(ballots.containsKey(login)) {
                            session.setAttribute("ballot", ballots.get(login));
                            session.setAttribute("bulletin", ballots.get(login).getBulletin());
                        }
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    request.getRequestDispatcher("index.html").forward(request, response);
                    return;
                }
            }

        }
        chain.doFilter(request, response);

    }

    public void destroy() {

    }
}
