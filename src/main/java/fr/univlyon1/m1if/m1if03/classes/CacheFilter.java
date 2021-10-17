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
import java.util.Date;
import java.util.Map;


@WebFilter(filterName = "CacheFilter" , urlPatterns= {"/election/vote/putvote", "/election/listBallots"})
public class CacheFilter extends HttpFilter {
    ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String subPath = path.substring(9);

        //if c'est une requete POST de vote
        if ("/vote/putvote".equals(subPath)) {
            Date date = new Date();
            response.setDateHeader("Last-Modified", date.getTime());
        } else { //if c'est une requete GET vers listballot
            long lastModifiedFromBrowser = request.getDateHeader("If-Modified-Since");
            long lastModifiedFromServer = request.getDateHeader("Last-Modified");

            if (lastModifiedFromBrowser != -1 && lastModifiedFromServer <= lastModifiedFromBrowser) {
                //setting 304 and returning with empty body
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            response.addDateHeader("Last-Modified", lastModifiedFromServer);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
