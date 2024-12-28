package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.User;

/**
 * This interface defines the operations for interacting with the user records in the database.
 * It provides methods to search, register, and update user details such as username, sex, and introduction.
 */
public interface UserMapper {

    /**
     * Searches for a user by their username.
     * @param username The username of the user to be searched.
     * @return A `User` object representing the found user, or `null` if no user is found.
     */
    @Select("select * from users where username = #{username}")
    User searchUser(@Param("username") String username);

    /**
     * Registers a new user in the database with the provided username and password.
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    void registerUser(@Param("username") String username, @Param("password") String password);

    /**
     * Changes the username of an existing user.
     * @param beforeUsername The current username that needs to be updated.
     * @param afterUsername The new username that will replace the current one.
     */
    void changeUsername(@Param("beforeUsername") String beforeUsername, 
                        @Param("afterUsername") String afterUsername);

    /**
     * Changes the sex of an existing user.
     * @param id The ID of the user whose sex is to be updated.
     * @param sex The new sex value for the user (e.g., 1 for male, 2 for female).
     */
    void changeSex(@Param("id") int id, @Param("sex") int sex);

    /**
     * Changes the introduction of an existing user.
     * @param id The ID of the user whose introduction is to be updated.
     * @param introduction The new introduction text for the user.
     */
    void changeIntroduction(@Param("id") int id, @Param("introduction") String introduction);
}
