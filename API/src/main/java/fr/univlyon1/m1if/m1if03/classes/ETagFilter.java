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
import java.util.Map;


@WebFilter(filterName = "ETagFilter" , urlPatterns="/election/vote/ballot")
public class ETagFilter extends HttpFilter {
    ServletContext context;

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession(false);
            int nb = getNbVotes();
            String lastETagFromBrowser = request.getHeader("If-None-Match");

            String lastETagBallot = getETag(nb, session);

            if (lastETagBallot.equals(lastETagFromBrowser)) {
                //setting 304 and returning with empty body
                System.out.println("\n NOT MODIFIED : browser ETag match server one");
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            System.out.println("\n MODIFIED : server update client and ETag");
            System.out.print(lastETagBallot);
            //String.valueOf(lastETagBallot.hashCode());

            session.setAttribute("nbLoadVotes", nb);

            response.addHeader("ETag",getETag(nb, session));

        }
        chain.doFilter(request, response);
    }

    private Integer getNbVotes() {
        int cpt = 0;
        final Map<String, Ballot> ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");
        for(String ignored : ballots.keySet()) {
            cpt++;
        }
        return cpt;
    }

    private String getETag (Integer nbVote, HttpSession session) {
        if(session.getAttribute("ballot") != null) {
            Ballot ballot = (Ballot) session.getAttribute("ballot");
            return String.valueOf((nbVote + ballot.hashCode()) * getServletContext().hashCode());
        } else {
            return String.valueOf((nbVote + getFilterConfig().hashCode()) * getServletContext().hashCode());
        }
    }

    public void destroy() {

    }
}
