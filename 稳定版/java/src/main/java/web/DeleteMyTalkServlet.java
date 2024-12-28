package web;

import service.ChatListService;
import service.SecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class responsible for handling the deletion of a user's chat record.
 * This includes removing both the main chat and secondary chat records.
 */
@WebServlet("/deleteMyTalkServlet")
public class DeleteMyTalkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to delete a chat record.
     * It deletes both the main chat and the related secondary chat.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the ID of the chat to be deleted from the request parameters
        int chatId = Integer.parseInt(req.getParameter("id"));

        // Call the service to delete the main chat record
        ChatListService chatListService = new ChatListService();
        chatListService.deleteMyTalk(chatId);

        // Call the service to delete the related secondary chat record
        SecondChatService secondChatService = new SecondChatService();
        secondChatService.deleteSecondChat(chatId);
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
