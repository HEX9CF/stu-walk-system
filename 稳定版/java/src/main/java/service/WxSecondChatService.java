package service;

import mapper.WxSecondChatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.SecondChat;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * WxSecondChatService provides methods for managing second-level chat operations.
 * It interacts with the database through MyBatis.
 */
public class WxSecondChatService {
    // SqlSessionFactory to manage MyBatis sessions
    private final SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Adds a second-level chat message to the database.
     *
     * @param id              The ID of the second-level chat.
     * @param commentId       The ID of the original comment.
     * @param userName        The username of the person replying.
     * @param commentName     The name of the person who made the original comment.
     * @param date            The date of the second-level chat.
     * @param content         The content of the second-level chat.
     * @param commentUserId   The ID of the user who made the original comment.
     * @param headPic         The URL or path to the user's profile picture.
     */
    public void add(int id, int commentId, String userName, String commentName, String date, String content, int commentUserId, String headPic) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            mapper.add(id, commentId, userName, commentName, date, content, commentUserId, headPic);
            sqlSession.commit();
        }
    }

    /**
     * Selects a list of second-level chats by the comment ID.
     *
     * @param commentId The ID of the original comment.
     * @return List of second-level chat objects.
     */
    public List<SecondChat> selectChatList(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            return mapper.selectChatList(commentId);
        }
    }

    /**
     * Selects the count of second-level chats for a specific comment.
     *
     * @param commentId The ID of the original comment.
     * @return The count of second-level chats.
     */
    public int selectCount(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            return mapper.selectCount(commentId);
        }
    }

    /**
     * Deletes a second-level chat message by its comment ID.
     *
     * @param commentId The ID of the comment to be deleted.
     */
    public void deleteSecondChat(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            mapper.deleteSecondChat(commentId);
            sqlSession.commit();
        }
    }

    /**
     * Selects the second-level replies made by the user.
     *
     * @param id The ID of the user whose replies are to be retrieved.
     * @return List of second-level chat objects.
     */
    public List<SecondChat> selectOwnReply(int id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            return mapper.selectOwnReply(id);
        }
    }

    /**
     * Deletes a user's second-level chat reply by its unique ID.
     *
     * @param uniqueId The unique ID of the second-level reply to be deleted.
     */
    public void deleteOwnReply(int uniqueId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            mapper.deleteOwnReply(uniqueId);
            sqlSession.commit();
        }
    }

    /**
     * Changes the username of a user in the second-level chat based on their ID.
     *
     * @param userId     The ID of the user whose username is to be changed.
     * @param newUserName The new username.
     */
    public void changeUsername(int userId, String newUserName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            mapper.changeUsername(userId, newUserName);
            sqlSession.commit();
        }
    }

    /**
     * Changes the comment name in second-level chats based on the comment user's ID.
     *
     * @param commentUserId The ID of the comment user whose name is to be changed.
     * @param newCommentName The new comment name.
     */
    public void changeCommentName(int commentUserId, String newCommentName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxSecondChatMapper mapper = sqlSession.getMapper(WxSecondChatMapper.class);
            mapper.changeCommentName(commentUserId, newCommentName);
            sqlSession.commit();
        }
    }
}
