package service;

import mapper.ChatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Chat;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * ChatListService class provides methods to interact with the Chat data in the database.
 * It uses MyBatis to execute SQL queries related to chat operations such as adding, deleting, 
 * selecting, and updating chat records in the database.
 */
public class ChatListService {
    // SqlSessionFactory to manage MyBatis sessions
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Retrieves all chat records from the database.
     *
     * @return List of all Chat records.
     */
    public List<Chat> selectAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        List<Chat> chatList = mapper.selectAll();
        sqlSession.close();
        return chatList;
    }

    /**
     * Adds a new chat record to the database.
     *
     * @param userId    ID of the user sending the chat message.
     * @param userName  Name of the user sending the chat message.
     * @param content   Content of the chat message.
     * @param date      Date when the message was sent.
     * @param sex       Gender of the user (represented as an integer).
     */
    public void addChat(Integer userId, String userName, String content, String date, int sex) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        mapper.addChat(userId, userName, content, date, sex);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Retrieves chat records by a specific user ID.
     *
     * @param userId User ID to filter the chat records.
     * @return List of Chat records belonging to the specified user.
     */
    public List<Chat> selectByUserId(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        List<Chat> chatList = mapper.selectByUserId(userId);
        sqlSession.close();
        return chatList;
    }

    /**
     * Deletes a specific chat message by its ID.
     *
     * @param chatId ID of the chat message to be deleted.
     */
    public void deleteMyTalk(int chatId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        mapper.deleteMyTalk(chatId);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Retrieves a limited number of chat records based on the given page number.
     *
     * @param pageNum Page number to limit the chat records.
     * @return List of Chat records for the specified page.
     */
    public List<Chat> selectLimit(int pageNum) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        List<Chat> chatList = mapper.selectLimit(pageNum);
        sqlSession.close();
        return chatList;
    }

    /**
     * Retrieves a chat record by its ID.
     *
     * @param chatId ID of the chat message to be retrieved.
     * @return The Chat object corresponding to the given ID.
     */
    public Chat selectBySuchId(int chatId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        Chat chat = mapper.selectBySuchId(chatId);
        sqlSession.close();
        return chat;
    }

    /**
     * Updates the count of a chat message (e.g., for likes or views).
     *
     * @param count  The new count to be updated.
     * @param chatId ID of the chat message to be updated.
     */
    public void updateCount(int count, int chatId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        mapper.updateCount(count, chatId);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Changes the username in chat records from a specified old username to a new one.
     *
     * @param beforeUserName The old username to be replaced.
     * @param afterUserName  The new username to replace the old one.
     */
    public void changeUsername(String beforeUserName, String afterUserName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        mapper.changeUsername(beforeUserName, afterUserName);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Changes the gender (sex) of a user in chat records.
     *
     * @param userId ID of the user whose gender is to be changed.
     * @param sex    New gender value to be updated (represented as an integer).
     */
    public void changeSex(int userId, int sex) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        mapper.changeSex(userId, sex);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * Searches for chat records that contain the specified string.
     *
     * @param s The string to search for in the chat records.
     * @return List of Chat records that match the search criteria.
     */
    public List<Chat> searchLike(String s) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ChatMapper mapper = sqlSession.getMapper(ChatMapper.class);
        List<Chat> chatList = mapper.searchLike(s);
        sqlSession.close();
        return chatList;
    }
}
