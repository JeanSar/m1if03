package fr.univlyon1.m1if.m1if03.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.utils.ElectionM1if03JwtHelper;

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
import java.util.HashMap;
import java.util.List;
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
        uncaught.add("/election");
        uncaught.add("/users");
        uncaught.add("/ballots");
        uncaught.add("/candidats");
        uncaught.add("/resultats");
        uncaught.add("/index.html");
        uncaught.add("/vote.css");
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //getting the filtered page path
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String referer = request.getHeader("Referer");

        HttpSession session = request.getSession(false);

        // check if the path must be filtered
        boolean isCaught = true;
        for(String p : uncaught) {
            if(path.endsWith(p)) {
                isCaught = false;
                System.out.println("\n"+ path +" request UNCAUGHT");
                break;
            }
        }


        if(isCaught) {
            if((session == null) || (session.getAttribute("user") == null)) {

                System.out.println("\nPath(Filter) : " + path);
                System.out.println("In Context : " + request.getContextPath());
                System.out.println("From referer : " + referer); //referer is the requesting entity client side path
                System.out.println("CAUGHT");

                String login = request.getParameter("login");
                if (login != null) {
                    if (login.equals("")){
                        response.sendRedirect(context.getContextPath() + "/index.html");
                        return;
                    } else {

                        ObjectMapper om = new ObjectMapper();
                        String monToken = ElectionM1if03JwtHelper.generateToken(request.getParameter("nom"), false, request);
                        String json = om.writeValueAsString(monToken);
                        response.setContentType("application/json");
                        response.getWriter().print(json);
                        response.getWriter().flush();


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
                    response.sendRedirect(context.getContextPath() + "/index.html");
                    return;
                }
            }

        }
        chain.doFilter(request, response);

    }

    public void destroy() {

    }
}
