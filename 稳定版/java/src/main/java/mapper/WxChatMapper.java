package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.Chat;

import java.util.List;

/**
 * This interface defines the operations for interacting with the WX chat records in the database.
 * It provides methods to fetch, add, delete, update, and search for chat records in the `wx_chatList` table.
 */
public interface WxChatMapper {

    /**
     * Retrieves all chat records from the `wx_chatList` table.
     * @return A list of `Chat` objects representing all chat records.
     */
    @Select("select * from wx_chatList")
    List<Chat> selectAll();

    /**
     * Adds a new chat record to the `wx_chatList` table.
     * @param user_id The ID of the user sending the chat message.
     * @param user_name The name of the user sending the chat message.
     * @param content The content of the chat message.
     * @param date The date when the chat message was sent.
     * @param sex The sex of the user (e.g., 1 for male, 2 for female).
     * @param head_pic The URL of the user's profile picture.
     */
    void addChat(@Param("user_id") int user_id, 
                 @Param("user_name") String user_name, 
                 @Param("content") String content, 
                 @Param("date") String date, 
                 @Param("sex") int sex, 
                 @Param("head_pic") String head_pic);

    /**
     * Retrieves all chat records of a specific user by their user ID.
     * @param id The user ID of the user whose chat records are to be fetched.
     * @return A list of `Chat` objects representing the chat records of the specified user.
     */
    @Select("select * from wx_chatList where user_id = #{id}")
    List<Chat> selectByUserId(@Param("id") int id);

    /**
     * Deletes a specific chat record by its ID.
     * @param id The ID of the chat record to be deleted.
     */
    @Delete("delete from wx_chatList where id = #{id}")
    void deleteMyTalk(@Param("id") int id);

    /**
     * Retrieves a paginated list of chat records, ordered by ID in descending order.
     * @param pageNum The starting index of the records to retrieve (used for pagination).
     * @return A list of `Chat` objects representing the chat records for the given page number.
     */
    @Select("select * from wx_chatList order by id desc limit #{pageNum}, 10")
    List<Chat> selectLimit(@Param("pageNum") int pageNum);

    /**
     * Retrieves a specific chat record by its ID.
     * @param id The ID of the chat record to be retrieved.
     * @return A `Chat` object representing the chat record with the specified ID.
     */
    @Select("select * from wx_chatList where id = #{id}")
    Chat selectBySuchId(@Param("id") int id);

    /**
     * Updates the count of a specific chat record (e.g., for likes or comments).
     * @param count The new count to be set for the chat record.
     * @param id The ID of the chat record to be updated.
     */
    void updateCount(@Param("count") int count, @Param("id") int id);

    /**
     * Changes the username of a specific user in the chat records.
     * @param user_id The ID of the user whose username is to be changed.
     * @param afterUser_name The new username to be set for the user.
     */
    void changeUsername(@Param("user_id") int user_id, 
                        @Param("afterUser_name") String afterUser_name);

    /**
     * Changes the sex of a specific user in the chat records.
     * @param user_id The ID of the user whose sex is to be changed.
     * @param sex The new sex value to be set for the user (e.g., 1 for male, 2 for female).
     */
    void changeSex(@Param("user_id") int user_id, @Param("sex") int sex);

    /**
     * Searches for chat records that contain the specified search term.
     * @param s The search term to look for in the chat records.
     * @return A list of `Chat` objects containing the search term.
     */
    List<Chat> searchLike(@Param("s") String s);
}
