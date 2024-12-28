package web;

import pojo.Chat;
import service.WxChatListService;
import service.WxSecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to handle adding a reply to the second-level chat list.
 * It updates the reply count in the original comment and stores the new reply.
 */
@WebServlet("/WxAddToSecondChatListServlet")
public class WxAddToSecondChatListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        try {
            // Parse input parameters
            int id = Integer.parseInt(req.getParameter("id"));
            int comment_id = Integer.parseInt(req.getParameter("comment_id"));
            String user_name = req.getParameter("user_name");
            String comment_name = req.getParameter("comment_name");
            String date = req.getParameter("date");
            String content = req.getParameter("content");
            String head_pic = req.getParameter("head_pic");

            // Validate required parameters
            if (user_name == null || comment_name == null || content == null || head_pic == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Missing required parameters\"}");
                return;
            }

            // Fetch the original comment from the WxChatListService
            WxChatListService wxChatListService = new WxChatListService();
            Chat chat = wxChatListService.selectBySuchId(comment_id);

            if (chat == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Original comment not found\"}");
                return;
            }

            // Add the new second-level comment using WxSecondChatService
            WxSecondChatService wxSecondChatService = new WxSecondChatService();
            wxSecondChatService.add(id, comment_id, user_name, comment_name, date, content, chat.getUser_id(), head_pic);

            // Update the reply count for the original comment
            int replyCount = wxSecondChatService.selectCount(comment_id);
            wxChatListService.updateCount(replyCount, comment_id);

            // Send success response
            resp.getWriter().write("{\"message\":\"Reply added successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid number format in parameters\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"An unexpected error occurred\"}");
            e.printStackTrace();  // Log the error to server logs for debugging
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);  // Handle POST requests the same as GET requests
    }
}
