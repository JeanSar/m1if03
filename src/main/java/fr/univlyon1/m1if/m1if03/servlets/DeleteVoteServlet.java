package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Ballot;
import fr.univlyon1.m1if.m1if03.classes.Bulletin;
import fr.univlyon1.m1if.m1if03.classes.User;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.io.*;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DeleteVote", value = "/deleteVote")
public class DeleteVoteServlet extends HttpServlet {

    public void init() {
        // Do required initialization
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Bulletin> bulletins = (List<Bulletin>)getServletContext().getAttribute("bulletins");
        Map<String, Ballot> ballots = (Map<String, Ballot>) getServletContext().getAttribute("ballots");

        HttpSession session = request.getSession(false);
        User current = (User)session.getAttribute("user");
        String login = request.getParameter("user");

        //removing the bulletin/ballot in model and updating it
        Ballot ballotToRm = ballots.get(login);
        if(ballotToRm == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.getRequestDispatcher("/WEB-INF/components/ballot.jsp").forward(request, response);
            return;
        }
        bulletins.remove(ballotToRm.getBulletin());
        ballots.remove(login);
        getServletContext().setAttribute("bulletins", bulletins);
        getServletContext().setAttribute("ballots", ballots);

        //if the user  is not trying to delete another user's ballot
        if(current.getLogin().equals(login)) {
            session.removeAttribute("ballot");
            session.removeAttribute("bulletin");
            System.out.println("Current " + login +" just deleted his own vote");
            request.getRequestDispatcher("/WEB-INF/components/ballot.jsp").forward(request, response);
        } else  {
            System.out.println("Admin " + current.getLogin() + " just deleted " + login + " vote");
            request.getRequestDispatcher("/WEB-INF/components/listBallots.jsp").forward(request, response);
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/components/ballot.jsp").forward(request, response);

    }

    public void destroy() {
        // do nothing.
    }
}