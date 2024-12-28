package service;

import mapper.SecondChatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.SecondChat;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * SecondChatService class provides methods for managing second-level chat messages.
 * It uses MyBatis to interact with the database.
 */
public class SecondChatService {
    // SqlSessionFactory to manage MyBatis sessions
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Adds a new second-level chat.
     *
     * @param id              The unique ID of the second-level chat.
     * @param commentId       The ID of the comment being replied to.
     * @param userName        The name of the user replying.
     * @param commentName     The name of the comment author.
     * @param date            The date of the reply.
     * @param content         The content of the second-level reply.
     */
    public void add(int id, int commentId, String userName, String commentName, String date, String content) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            mapper.add(id, commentId, userName, commentName, date, content);
            sqlSession.commit();
        }
    }

    /**
     * Retrieves the list of second-level chats for a specific comment.
     *
     * @param commentId The ID of the comment.
     * @return A list of second-level chats related to the comment.
     */
    public List<SecondChat> selectChatList(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            return mapper.selectChatList(commentId);
        }
    }

    /**
     * Retrieves the count of second-level chats for a specific comment.
     *
     * @param commentId The ID of the comment.
     * @return The number of second-level chats related to the comment.
     */
    public int selectCount(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            return mapper.selectCount(commentId);
        }
    }

    /**
     * Deletes all second-level chats for a specific comment.
     *
     * @param commentId The ID of the comment whose second-level chats are to be deleted.
     */
    public void deleteSecondChat(int commentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            mapper.deleteSecondChat(commentId);
            sqlSession.commit();
        }
    }

    /**
     * Retrieves the list of second-level chats that a user has replied to.
     *
     * @param userId The ID of the user.
     * @return A list of second-level chats where the user has replied.
     */
    public List<SecondChat> selectOwnReply(int userId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            return mapper.selectOwnReply(userId);
        }
    }

    /**
     * Deletes a second-level reply made by the user.
     *
     * @param uniqueId The unique ID of the second-level reply to be deleted.
     */
    public void deleteOwnReply(int uniqueId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            mapper.deleteOwnReply(uniqueId);
            sqlSession.commit();
        }
    }

    /**
     * Changes the username in second-level chats.
     *
     * @param oldUserName The old username.
     * @param newUserName The new username.
     */
    public void changeUsername(String oldUserName, String newUserName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            mapper.changeUsername(oldUserName, newUserName);
            sqlSession.commit();
        }
    }

    /**
     * Changes the comment author's name in second-level chats.
     *
     * @param oldCommentName The old comment author's name.
     * @param newCommentName The new comment author's name.
     */
    public void changeCommentName(String oldCommentName, String newCommentName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SecondChatMapper mapper = sqlSession.getMapper(SecondChatMapper.class);
            mapper.changeCommentName(oldCommentName, newCommentName);
            sqlSession.commit();
        }
    }
}
