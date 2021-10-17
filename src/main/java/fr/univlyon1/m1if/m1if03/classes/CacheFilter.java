package fr.univlyon1.m1if.m1if03.classes;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@WebFilter(filterName = "CacheFilter" , urlPatterns= {"/election/vote/putvote", "/election/vote/deleteVote","/election/listBallots"})
public class CacheFilter extends HttpFilter {
    ServletContext context;
    long lastModifiedBallots = -1;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //String path = request.getRequestURI().substring(request.getContextPath().length());
        Date date = new Date();
        //if c'est une requete Get
        if (request.getMethod().equals("GET")) {
            long lastModifiedFromBrowser = request.getDateHeader("If-Modified-Since");
            long lastModifiedFromServer = lastModifiedBallots;

            if (lastModifiedFromBrowser != -1 && lastModifiedFromServer <= lastModifiedFromBrowser) {
                //setting 304 and returning with empty body
                System.out.println("\n NOT MODIFIED : browser version is up-to-date");
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            System.out.println("\n MODIFIED : server update client");
            System.out.print(lastModifiedFromServer);
            response.setDateHeader("Last-Modified", date.getTime());

        } else { //if c'est une requete POST
            System.out.println("\n MODIFIED : client update server");
            lastModifiedBallots = date.getTime();
        }


        chain.doFilter(request, response);
    }


    public void destroy() {

    }
}
