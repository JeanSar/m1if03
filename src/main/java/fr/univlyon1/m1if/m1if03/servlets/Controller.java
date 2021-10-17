package fr.univlyon1.m1if.m1if03.servlets;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/election", "/election/*"})
public class Controller extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("\nPath " + getServletName()+" : " + path);
        System.out.println("In Context : " + context.getContextPath());
        System.out.println("From referer : " + request.getHeader("Referer"));
        System.out.println("Type : " + request.getMethod());

        String subPath = path.substring(9); // on enlève /election
        //System.out.println("le sous path controller = "+ subPath);

        //check if the path isn't looping
        if(subPath.startsWith("/election")) {
            response.sendError(404);
        }

        switch (subPath) {
            case "/":
            case "":
                response.sendRedirect(context.getContextPath() + "/index.html");
                break;
            default:
                request.getRequestDispatcher(subPath).forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        processRequest(request, response);
    }
}