package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.WxUsers;

/**
 * This interface defines the operations for interacting with the WX users in the database.
 * It includes methods for registering users, retrieving user details by their openid or user ID,
 * and updating user information such as introduction, username, and sex.
 */
public interface WxUsersMapper {

    /**
     * Registers a new user by inserting their openid, name, and profile picture into the `wx_users` table.
     * @param openid The openid of the user (unique identifier).
     * @param name The name of the user.
     * @param head_pic The URL or path to the user's profile picture.
     */
    @Insert("INSERT INTO wx_users (openid, name, head_pic) VALUES (#{openid}, #{name}, #{head_pic})")
    void registerUser(@Param("openid") String openid, 
                      @Param("name") String name, 
                      @Param("head_pic") String head_pic);

    /**
     * Retrieves a user's details based on their openid.
     * @param openid The openid of the user to search for.
     * @return A `WxUsers` object representing the user's details.
     */
    @Select("select * from wx_users where openid = #{openid}")
    WxUsers seekOpenid(@Param("openid") String openid);

    /**
     * Updates the introduction of a user by their user ID.
     * @param id The ID of the user whose introduction is to be updated.
     * @param introduction The new introduction text to be set for the user.
     */
    void changeIntroduction(@Param("id") int id, 
                            @Param("introduction") String introduction);

    /**
     * Changes the username of a user by their user ID.
     * @param id The ID of the user whose username is to be changed.
     * @param afterUsername The new username to be set for the user.
     */
    void changeUsername(@Param("id") int id, 
                        @Param("afterUsername") String afterUsername);

    /**
     * Changes the sex of a user by their user ID.
     * @param id The ID of the user whose sex is to be changed.
     * @param sex The new sex to be set for the user (e.g., 1 for male, 2 for female).
     */
    void changeSex(@Param("id") int id, 
                   @Param("sex") int sex);

    /**
     * Retrieves a user's details by their user ID.
     * @param id The ID of the user to search for.
     * @return A `WxUsers` object representing the user's details.
     */
    @Select("select * from wx_users where id = #{id}")
    WxUsers searchUserById(@Param("id") int id);
}
