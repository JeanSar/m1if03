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


@WebFilter(filterName = "BallotFilter" , urlPatterns="/election/listBallots")
public class BallotFilter extends HttpFilter {
    ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if(!((User)session.getAttribute("user")).isAdmin()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.getRequestDispatcher("/election").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
