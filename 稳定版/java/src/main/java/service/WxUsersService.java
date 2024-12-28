package service;

import mapper.WxUsersMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.WxUsers;
import util.SqlSessionFactoryUtil;

/**
 * WxUsersService provides methods for managing user operations related to WeChat users.
 * It interacts with the database through MyBatis.
 */
public class WxUsersService {
    // SqlSessionFactory to manage MyBatis sessions
    private final SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Registers a new WeChat user.
     *
     * @param openId     The openid of the user.
     * @param name       The name of the user.
     * @param headPic    The user's profile picture URL or path.
     */
    public void registerUser(String openId, String name, String headPic) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxUsersMapper mapper = sqlSession.getMapper(WxUsersMapper.class);
            mapper.registerUser(openId, name, headPic);
            sqlSession.commit();
        }
    }

    /**
     * Finds a WeChat user by their openid.
     *
     * @param openId The openid of the user.
     * @return The corresponding WxUsers object.
     */
    public WxUsers seekOpenId(String openId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxUsersMapper mapper = sqlSession.getMapper(WxUsersMapper.class);
            return mapper.seekOpenid(openId);
        }
    }

    /**
     * Changes the introduction of the user.
     *
     * @param userId     The ID of the user whose introduction is to be updated.
     * @param introduction The new introduction for the user.
     */
    public void changeIntroduction(int userId, String introduction) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxUsersMapper mapper = sqlSession.getMapper(WxUsersMapper.class);
            mapper.changeIntroduction(userId, introduction);
            sqlSession.commit();
        }
    }

    /**
     * Changes the sex of the user.
     *
     * @param userId The ID of the user whose sex is to be updated.
     * @param sex    The new sex of the user (1 for male, 2 for female).
     */
    public void changeSex(int userId, int sex) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxUsersMapper mapper = sqlSession.getMapper(WxUsersMapper.class);
            mapper.changeSex(userId, sex);
            sqlSession.commit();
        }
    }

    /**
     * Changes the username of the user.
     *
     * @param userId     The ID of the user whose username is to be updated.
     * @param newUsername The new username for the user.
     */
    public void changeUsername(int userId, String newUsername) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            WxUsersMapper mapper = sqlSession.getMapper(WxUsersMapper.class);
            mapper.changeUsername(userId, newUsername);
            sqlSession.commit();
        }
    }
}
