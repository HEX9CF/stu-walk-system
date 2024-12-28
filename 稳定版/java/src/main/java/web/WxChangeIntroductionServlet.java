package web;

import service.WxUsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class WxChangeIntroductionServlet
 * 
 * This servlet handles the request to change a user's introduction (or bio) in the system.
 * 
 * Request Method:
 * - GET
 * 
 * Request Parameters:
 * - id: The unique user ID whose introduction needs to be updated.
 * - introduction: The new introduction text to be saved for the user.
 * 
 * Response Format:
 * - JSON object with the response code and message indicating success or failure.
 */
@WebServlet("/WxChangeIntroductionServlet")
public class WxChangeIntroductionServlet extends HttpServlet {

    /**
     * Handles the GET request to change the user's introduction.
     * 
     * @param req HttpServletRequest object containing the client request data.
     * @param resp HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare the response map to hold the response data
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // Parse the input parameters (user ID and introduction text)
            int id = Integer.parseInt(req.getParameter("id"));
            String introduction = req.getParameter("introduction");

            // Validate the input to ensure introduction is not empty
            if (introduction == null || introduction.isEmpty()) {
                responseMap.put("code", 400);  // HTTP Status 400: Bad Request
                responseMap.put("msg", "Introduction cannot be empty");
                resp.getWriter().write(responseMap.toString());
                return;
            }

            // Create the service object to update the introduction
            WxUsersService wxUsersService = new WxUsersService();
            boolean success = wxUsersService.changeIntroduction(id, introduction);

            // Determine response based on the result of the update operation
            if (success) {
                responseMap.put("code", 200);  // HTTP Status 200: OK
                responseMap.put("msg", "Introduction updated successfully");
            } else {
                responseMap.put("code", 500);  // HTTP Status 500: Internal Server Error
                responseMap.put("msg", "Failed to update introduction");
            }

        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            responseMap.put("code", 400);
            responseMap.put("msg", "Invalid user ID");
        } catch (Exception e) {
            // Catch any other unexpected errors
            responseMap.put("code", 500);
            responseMap.put("msg", "An error occurred while updating the introduction");
            e.printStackTrace();  // Log the exception for debugging purposes
        }

        // Write the JSON response back to the client
        resp.getWriter().write(responseMap.toString());
    }

    /**
     * Handles the POST request by delegating to the doGet method.
     * 
     * @param req HttpServletRequest object containing the client request data.
     * @param resp HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
