package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.utils.ElectionM1if03JwtHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "UsersController", urlPatterns ={"/users", "/users/*"})
public class UsersController extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("Controller Users = "+ path);
        String subPath = path.substring(6);
        System.out.println("le sous path controller = "+ subPath);

        //check if the path isn't looping
        if(subPath.startsWith("/users")) {
            response.sendError(404);
        }

        if ("/settings".equals(subPath)) {
            request.getRequestDispatcher("/WEB-INF/components/settings.jsp").include(request, response);
        } else {
            request.getRequestDispatcher(subPath).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String subPath = path.substring(7); // on enlève /candidats
        switch (subPath){
            case "login":
                String login = req.getParameter("login");
                //String nom = req.getParameter("nom");
                boolean isAdmin = Objects.equals(req.getParameter("admin"), "on");

                if (login == null || login.equals("")) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres de la requète non acceptables."); //Bad request
                } else {
                    resp.setHeader("location", req.getRequestURI() + "/" + login.replace(' ', '_'));
                    String token = ElectionM1if03JwtHelper.generateToken(req.getParameter("login"), isAdmin, req);
                    resp.setHeader("Authorization", "Bearer " + token);
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                break;
            case "logout":
                String authorizationHeader = req.getHeader("Authorization");
                String token;
                if (authorizationHeader == null || !authorizationHeader.split(" ")[0].equals("Bearer")) {
                    //No header "Authorization".
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié");
                    return;
                }
                token = authorizationHeader.split(" ")[1];
                try {
                    ElectionM1if03JwtHelper.verifyToken(token, req);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("application/json" + "; charset=utf-8");
                    resp.getWriter().write("Successful operation");
                    resp.getWriter().flush();
                } catch(Exception e) {
                    //Invalid token.
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié");
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    //creer un utilisateur
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("Controller Users = "+ path);
        String subPath = path.substring(8).replace('_', ' '); // on enlève /users/: et on remplace les "_" par des " "
        System.out.println("le sous path controller = "+ subPath);

        ObjectMapper om = new ObjectMapper();
        String monToken = ElectionM1if03JwtHelper.generateToken(subPath, false, request);
        String json = om.writeValueAsString(monToken);
        response.setContentType("application/json");
        response.getWriter().print(json);
        response.getWriter().flush();

        System.out.println("Token : "+ ElectionM1if03JwtHelper.verifyToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7), request));

    }
}