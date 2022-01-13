package fr.univlyon1.m1if.m1if03.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.classes.Candidat;
import fr.univlyon1.m1if.m1if03.classes.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UsersController", urlPatterns ={"/users", "/users/*"})
public class UsersController extends HttpServlet {
    ServletContext context;
    ElectionM1if03JwtHelper jwt = new ElectionM1if03JwtHelper();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorizationHeader = req.getHeader("Authorization");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String subPath = path.substring(7); // on enlève /candidats
        switch (subPath){
            case "login":
                String login = req.getParameter("login");
                String nom = req.getParameter("nom");
                boolean isAdmin = (req.getParameter("admin")) == "on" ? true : false;

                if (login == null || login.equals("")) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres de la requète non acceptables."); //Bad request
                } else {
                    resp.setHeader("location", req.getRequestURI() + "/" + login);
                    String token = jwt.generateToken(req.getParameter("login"), isAdmin, req);
                    resp.setHeader("Authorization", "Bearer " + token);
                    resp.setStatus(HttpServletResponse.SC_OK);

                    ObjectMapper objectMapper = new ObjectMapper();
                    User user = new User(login, nom, isAdmin);
                    String jsonUser = objectMapper.writeValueAsString(user);
                    resp.setContentType("application/json");
                    resp.getWriter().print(jsonUser);
                    resp.getWriter().flush();
                }
                break;
            case "logout":
                authorizationHeader = req.getHeader("Authorization");
                String token = "INVALID_TOKEN";
                if (authorizationHeader == null || !authorizationHeader.split(" ")[0].equals("Bearer")) {
                    //No header "Authorization".
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié");
                    return;
                }
                token = authorizationHeader.split(" ")[1];
                try {
                    jwt.verifyToken(token, req);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("application/json" + "; charset=utf-8");
                    resp.getWriter().write("Successful operation");
                } catch(Exception e) {
                    //Invalid token.
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié");
                }
                break;
                case "user":
                if (req.getParameter("nom") != null && !(req.getParameter("nom").equals(""))) {
                    User user = (User) req.getSession().getAttribute("user");
                    user.setNom(req.getParameter("nom"));
                    req.getRequestDispatcher("/WEB-INF/components/electionHome.jsp").forward(req, resp);
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
        ObjectMapper om = new ObjectMapper();
        String monToken = ElectionM1if03JwtHelper.generateToken(request.getParameter("nom"), false, request);
        String json = om.writeValueAsString(monToken);
        response.setContentType("application/json");
        response.getWriter().print(json);
        response.getWriter().flush();

        System.out.println("Token : "+ ElectionM1if03JwtHelper.verifyToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7).toString().toString(), request));

    }
}
