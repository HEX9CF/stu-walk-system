package web;

import com.alibaba.fastjson.JSON;
import pojo.SecondChat;
import service.SecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for retrieving the replies made by the user.
 * This servlet handles requests to fetch replies made by a specific user
 * and returns the results in JSON format.
 */
@WebServlet("/selectOwnReplyServlet")
public class SelectOwnReplyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the user ID from the request parameters
        int userId = Integer.parseInt(req.getParameter("id"));

        // Create service instance and fetch the replies made by the user
        SecondChatService secondChatService = new SecondChatService();
        List<SecondChat> userReplies = secondChatService.selectOwnReply(userId);

        // Convert the list of replies to JSON format
        String jsonString = JSON.toJSONString(userReplies);

        // Write the JSON response
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the request character encoding to UTF-8
        req.setCharacterEncoding("utf-8");

        // Delegate the handling of POST requests to doGet
        this.doGet(req, resp);
    }
}
