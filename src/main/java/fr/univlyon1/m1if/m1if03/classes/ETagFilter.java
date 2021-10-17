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


@WebFilter(filterName = "ETagFilter" , urlPatterns="/election/vote/ballot")
public class ETagFilter extends HttpFilter {
    ServletContext context;
    String lastETagBallot = null;


    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (request.getMethod().equals("GET")) {
            String lastETagFromBrowser = request.getHeader("If-None-Match");
            String lastETagFromServer = lastETagBallot;

            if (lastETagFromServer.equals(lastETagFromBrowser)) {
                //setting 304 and returning with empty body
                System.out.println("\n NOT MODIFIED : browser ETag match server one");
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            System.out.println("\n MODIFIED : server update client and ETag");
            System.out.print(lastETagFromServer);
            response.addHeader("ETag", getETag());

        } else { //if c'est une requete POST
            System.out.println("\n MODIFIED : client update server and ETag");
            lastETagBallot = getETag();
        }
        chain.doFilter(request, response);
    }

    private String getETag () {
        return "\"version1\"";
    }

    public void destroy() {

    }
}
