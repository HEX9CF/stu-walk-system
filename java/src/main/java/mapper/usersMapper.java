package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.user; 

/**
 * User mapper interface for database operations.
 */
public interface UserMapper {
    /**
     * Searches for a user by username.
     *
     * @param username The username to search for.
     * @return The user object if found.
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    User searchUser(@Param("username") String username);

    /**
     * Registers a new user.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    void registerUser(@Param("username") String username, @Param("password") String password);

    /**
     * Changes a user's username.
     *
     * @param beforeUsername The original username.
     * @param afterUsername  The new username.
     */
    void changeUserName(@Param("beforeUsername") String beforeUsername, @Param("afterUsername") String afterUsername);

    /**
     * Changes a user's sex.
     *
     * @param id   The user's ID.
     * @param sex  The new sex value.
     */
    void changeSex(@Param("id") int id, @Param("sex") int sex);

    /**
     * Changes a user's introduction.
     *
     * @param id           The user's ID.
     * @param introduction The new user introduction.
     */
    void changeIntroduction(@Param("id") int id, @Param("introduction") String introduction);
}