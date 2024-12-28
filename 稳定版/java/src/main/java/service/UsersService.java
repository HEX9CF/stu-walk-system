package service;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.User;
import util.SqlSessionFactoryUtil;

/**
 * UsersService class provides methods for managing user-related operations.
 * It uses MyBatis to interact with the database.
 */
public class UsersService {
    // SqlSessionFactory to manage MyBatis sessions
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Searches for a user by their username.
     *
     * @param username The username of the user to be searched.
     * @return The User object if found, otherwise null.
     */
    public User searchUser(String username) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.searchUser(username);
        }
    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     */
    public void registerUser(String username, String password) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.registerUser(username, password);
            sqlSession.commit();
        }
    }

    /**
     * Changes the username of an existing user.
     *
     * @param oldUsername The current username of the user.
     * @param newUsername The new username to be set.
     */
    public void changeUsername(String oldUsername, String newUsername) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.changeUsername(oldUsername, newUsername);
            sqlSession.commit();
        }
    }

    /**
     * Changes the sex of a user.
     *
     * @param userId The ID of the user.
     * @param sex    The new sex of the user (e.g., 1 for male, 2 for female).
     */
    public void changeSex(int userId, int sex) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.changeSex(userId, sex);
            sqlSession.commit();
        }
    }

    /**
     * Updates the user's introduction.
     *
     * @param userId      The ID of the user.
     * @param introduction The new introduction text for the user.
     */
    public void changeIntroduction(int userId, String introduction) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.changeIntroduction(userId, introduction);
            sqlSession.commit();
        }
    }
}
