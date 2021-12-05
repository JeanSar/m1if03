package fr.univlyon1.m1if.m1if03.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.utils.ElectionM1if03JwtHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
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

        System.out.println("Token : "+ ElectionM1if03JwtHelper.verifyToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7), request));

    }
}