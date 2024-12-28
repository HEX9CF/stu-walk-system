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
 * Servlet for retrieving the list of second-level comments for a specific comment.
 * This servlet handles requests to fetch the replies (second-level comments) 
 * for a given comment and returns the results in JSON format.
 */
@WebServlet("/selectSecondChatListServlet")
public class SelectSecondChatListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the comment ID from the request parameters
        int commentId = Integer.parseInt(req.getParameter("comment_id"));

        // Create service instance and fetch the second-level comments for the specified comment
        SecondChatService secondChatService = new SecondChatService();
        List<SecondChat> secondChats = secondChatService.selectChatList(commentId);

        // Convert the list of second-level comments to JSON format
        String jsonString = JSON.toJSONString(secondChats);

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
