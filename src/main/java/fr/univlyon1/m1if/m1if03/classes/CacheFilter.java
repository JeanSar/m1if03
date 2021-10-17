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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


@WebFilter(filterName = "CacheFilter" , urlPatterns= {"/election/vote/putvote", "/election/listBallots"})
public class CacheFilter extends HttpFilter {
    ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //String path = request.getRequestURI().substring(request.getContextPath().length());

        //if c'est une requete POST
        if (request.getMethod().equals("GET")) {
            long lastModifiedFromBrowser = request.getDateHeader("If-Modified-Since");
            long lastModifiedFromServer = getLastModifiedMillis();

            if (lastModifiedFromBrowser != -1 && lastModifiedFromServer <= lastModifiedFromBrowser) {
                //setting 304 and returning with empty body
                System.out.println("\n NOT MODIFIED : browser version is up-to-date");
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            System.out.println("\n MODIFIED : server update client");

        } else { //if c'est une requete POST
            System.out.println("\n MODIFIED : client update server");
        }
        Date date = new Date();
        response.setDateHeader("Last-Modified", date.getTime());

        chain.doFilter(request, response);
    }

    private static long getLastModifiedMillis() {
        //Using hard coded value, in real scenario there should be for example
        // last modified date of this servlet or of the underlying resource
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2017, 1, 8,
                        10, 30, 20),
                ZoneId.of("GMT"));
        return zdt.toInstant().toEpochMilli();
    }

    public void destroy() {

    }
}
