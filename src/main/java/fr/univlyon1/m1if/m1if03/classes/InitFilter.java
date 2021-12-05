package fr.univlyon1.m1if.m1if03.classes;

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
        uncaught.add("/noms");
        uncaught.add("/ballots");
        uncaught.add("/candidats");
        uncaught.add("/resultats");
        uncaught.add("/index.html");
        uncaught.add("/vote.css");
        uncaught.add("/login");
        uncaught.add("/logout");
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //getting the filtered page path
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String referer = request.getHeader("Referer");

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
            System.out.println("\nPath(Filter) : " + path);
            System.out.println("In Context : " + request.getContextPath());
            System.out.println("From referer : " + referer); //referer is the requesting entity client side path
            System.out.println("CAUGHT");
            try {
                String authorizationHeader = request.getHeader("apiKey");
                String token = authorizationHeader.split(" ")[1];
                ElectionM1if03JwtHelper.verifyToken(token, request);
                response.setHeader("Authorization", "Bearer " + token);
            } catch(Exception e) {
                //Invalid token.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié");
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
