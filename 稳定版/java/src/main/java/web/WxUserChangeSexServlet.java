package web;

import service.WxChatListService;
import service.WxUsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles requests to update a user's sex in both the user profile and the chat list.
 * It retrieves the user ID and the new sex value from the request, and calls the relevant service methods
 * to update the information in both places.
 */
@WebServlet("/WxUserChangeSexServlet")
public class WxUserChangeSexServlet extends HttpServlet {

    /**
     * Handles GET requests for updating a user's sex.
     * The user ID and new sex value are retrieved from the request parameters.
     * The user's sex is updated in both the user profile and the chat list via respective service methods.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON
        resp.setContentType("application/json; charset=utf-8");

        // Retrieve parameters from the request
        int id = Integer.parseInt(req.getParameter("id"));
        int sex = Integer.parseInt(req.getParameter("sex"));

        // Create service instances to handle the changes
        WxUsersService wxUsersService = new WxUsersService();
        WxChatListService wxChatListService = new WxChatListService();

        // Update the user's sex in the user profile and chat list
        wxUsersService.changeSex(id, sex);
        wxChatListService.changeSex(id, sex);
    }

    /**
     * Handles POST requests by forwarding them to the doGet method.
     * This ensures that both GET and POST requests are processed in the same way.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the request character encoding to UTF-8 for proper handling of non-ASCII characters
        req.setCharacterEncoding("utf-8");

        // Forward the POST request to the doGet method for processing
        this.doGet(req, resp);
    }
}
