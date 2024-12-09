package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.Chat;

import java.util.List;

/**
 * This interface defines the operations for interacting with the "chatList" table in the database.
 * It provides methods to select, add, delete, and modify chat records.
 * It also supports pagination and searching for chat messages.
 */
public interface ChatMapper {

    /**
     * Retrieves all chat records from the database.
     * @return List of all chats.
     */
    @Select("select * from chatList")
    List<Chat> selectAll();

    /**
     * Adds a new chat entry to the database.
     * @param userId The ID of the user posting the chat.
     * @param userName The name of the user posting the chat.
     * @param content The content of the chat message.
     * @param date The date when the chat was posted.
     * @param sex The sex of the user (1 for male, 0 for female, etc.).
     */
    void addChat(@Param("userId") int userId, @Param("userName") String userName, 
                 @Param("content") String content, @Param("date") String date, @Param("sex") int sex);

    /**
     * Retrieves all chats posted by a specific user.
     * @param userId The ID of the user whose chats need to be retrieved.
     * @return List of chats posted by the user.
     */
    @Select("select * from chatList where user_id = #{userId}")
    List<Chat> selectByUserId(@Param("userId") int userId);

    /**
     * Deletes a specific chat message from the database.
     * @param chatId The ID of the chat to be deleted.
     */
    @Delete("delete from chatList where id = #{chatId}")
    void deleteMyTalk(@Param("chatId") int chatId);

    /**
     * Retrieves a limited number of chats for pagination.
     * @param pageNum The starting index for pagination.
     * @return List of chats based on the pagination.
     */
    @Select("select * from chatList order by id desc limit #{pageNum}, 10")
    List<Chat> selectLimit(@Param("pageNum") int pageNum);

    /**
     * Retrieves a specific chat by its ID.
     * @param chatId The ID of the chat.
     * @return The chat object corresponding to the given ID.
     */
    @Select("select * from chatList where id = #{chatId}")
    Chat selectBySuchId(@Param("chatId") int chatId);

    /**
     * Updates the count value for a specific chat.
     * @param count The new count value.
     * @param chatId The ID of the chat to update.
     */
    void updateCount(@Param("count") int count, @Param("chatId") int chatId);

    /**
     * Changes the username of a user in the chat list.
     * @param beforeUserName The current username.
     * @param afterUserName The new username.
     */
    void changeUsername(@Param("beforeUserName") String beforeUserName, @Param("afterUserName") String afterUserName);

    /**
     * Changes the sex of a user in the chat list.
     * @param userId The ID of the user.
     * @param sex The new sex value (1 for male, 0 for female, etc.).
     */
    void changeSex(@Param("userId") int userId, @Param("sex") int sex);

    /**
     * Searches for chat records containing a specific string.
     * @param searchTerm The string to search for in the chat records.
     * @return List of chats containing the search term.
     */
    List<Chat> searchLike(@Param("searchTerm") String searchTerm);
}
