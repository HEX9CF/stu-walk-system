package web;

import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling the change of a user's introduction.
 * It processes requests to update a user's profile introduction.
 */
@WebServlet("/changeIntroductionServlet")
public class ChangeIntroductionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to change a user's introduction.
     * It retrieves the user ID and new introduction from the request parameters and updates the user's information.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the user ID and the new introduction from the request
        int userId = Integer.parseInt(req.getParameter("id"));
        String newIntroduction = req.getParameter("introduction");

        // Create an instance of the service class and update the user's introduction
        UsersService usersService = new UsersService();
        usersService.changeIntroduction(userId, newIntroduction);
    }

    /**
     * Handles the POST request by forwarding it to the doGet method.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
