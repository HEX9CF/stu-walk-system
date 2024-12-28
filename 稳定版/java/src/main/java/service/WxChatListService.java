package service;

import mapper.WxChatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Chat;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * WxChatListService class provides methods for managing chat-related operations.
 * It uses MyBatis to interact with the database.
 */
public class WxChatListService {
    // SqlSessionFactory to manage MyBatis sessions
    private final SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Selects all chat records from the database.
     * 
     * @return List of all Chat objects.
     */
    public List<Chat> selectAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            return mapper.selectAll();
        }
    }

    /**
     * Adds a new chat record to the database.
     *
     * @param userId    The user ID of the person sending the chat.
     * @param userName  The username of the person sending the chat.
     * @param content   The content of the chat message.
     * @param date      The date when the chat was sent.
     * @param sex       The sex of the user (e.g., 1 for male, 2 for female).
     * @param headPic   The URL or path to the user's profile picture.
     */
    public void addChat(Integer userId, String userName, String content, String date, int sex, String headPic) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            mapper.addChat(userId, userName, content, date, sex, headPic);
            sqlSession.commit();
        }
    }

    /**
     * Selects chats by user ID.
     *
     * @param userId The user ID whose chats are to be retrieved.
     * @return List of Chat objects.
     */
    public List<Chat> selectByUserId(int userId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            return mapper.selectByUserId(userId);
        }
    }

    /**
     * Deletes a chat by its ID.
     *
     * @param chatId The ID of the chat to be deleted.
     */
    public void deleteMyTalk(int chatId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            mapper.deleteMyTalk(chatId);
            sqlSession.commit();
        }
    }

    /**
     * Selects a limited number of chat records for pagination.
     *
     * @param pageNum The page number for pagination.
     * @return List of Chat objects.
     */
    public List<Chat> selectLimit(int pageNum) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            return mapper.selectLimit(pageNum);
        }
    }

    /**
     * Selects a chat by its unique ID.
     *
     * @param chatId The ID of the chat to be retrieved.
     * @return The Chat object.
     */
    public Chat selectBySuchId(int chatId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            return mapper.selectBySuchId(chatId);
        }
    }

    /**
     * Updates the count for a specific chat.
     *
     * @param count The new count to be set.
     * @param chatId The ID of the chat whose count is to be updated.
     */
    public void updateCount(int count, int chatId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            mapper.updateCount(count, chatId);
            sqlSession.commit();
        }
    }

    /**
     * Changes the username of a user based on their user ID.
     *
     * @param userId       The user ID of the user whose username is to be changed.
     * @param newUserName  The new username to be set.
     */
    public void changeUsername(int userId, String newUserName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            mapper.changeUsername(userId, newUserName);
            sqlSession.commit();
        }
    }

    /**
     * Changes the sex of a user based on their user ID.
     *
     * @param userId The user ID of the user whose sex is to be updated.
     * @param sex    The new sex value (e.g., 1 for male, 2 for female).
     */
    public void changeSex(int userId, int sex) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            mapper.changeSex(userId, sex);
            sqlSession.commit();
        }
    }

    /**
     * Searches chats by a keyword.
     *
     * @param searchKeyword The keyword to search for in chats.
     * @return List of Chat objects containing the keyword.
     */
    public List<Chat> searchLike(String searchKeyword) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxChatMapper mapper = sqlSession.getMapper(WxChatMapper.class);
            return mapper.searchLike(searchKeyword);
        }
    }
}
