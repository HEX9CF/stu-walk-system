package service;

import mapper.TJgoodsMapper;
import mapper.songsMapper;
import mapper.usersMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.TJgoods;
import pojo.song;
import pojo.users;
import util.sqlSessionFactoryUtil;

import java.util.List;

/**
 * Service class for user-related operations.
 */
public class UsersService {
    private SqlSessionFactory sqlSessionFactory = sqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Searches for a user by username.
     *
     * @param username The username to search for.
     * @return The user object if found, otherwise null.
     */
    public user searchUser(String username) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            usersMapper mapper = sqlSession.getMapper(usersMapper.class);
            return mapper.searchUser(username);
        }
    }

    /**
     * Registers a new user.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    public void registerUser(String username, String password) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            usersMapper mapper = sqlSession.getMapper(usersMapper.class);
            mapper.registerUser(username, password);
            sqlSession.commit();
        }
    }

    /**
     * Changes a user's username.
     *
     * @param beforeUsername The original username.
     * @param afterUsername  The new username.
     */
    public void changeUsername(String beforeUsername, String afterUsername) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            usersMapper mapper = sqlSession.getMapper(usersMapper.class);
            mapper.changeUserName(beforeUsername, afterUsername);
            sqlSession.commit();
        }
    }

    /**
     * Changes a user's sex.
     *
     * @param id   The user's ID.
     * @param sex  The new sex value.
     */
    public void changeSex(int id, int sex) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            usersMapper mapper = sqlSession.getMapper(usersMapper.class);
            mapper.changeSex(id, sex);
            sqlSession.commit();
        }
    }

    /**
     * Changes a user's introduction.
     *
     * @param id           The user's ID.
     * @param introduction The new user introduction.
     */
    public void changeIntroduction(int id, String introduction) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            usersMapper mapper = sqlSession.getMapper(usersMapper.class);
            mapper.changeIntroduction(id, introduction);
            sqlSession.commit();
        }
    }
}