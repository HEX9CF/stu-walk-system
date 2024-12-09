package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.SecondChat;

import java.util.List;

/**
 * This interface defines the operations for interacting with the second chat records in the database.
 * It provides methods to add, select, delete, and update second-level chat records, including changing usernames and comment names.
 */
public interface SecondChatMapper {

    /**
     * Adds a new second-level chat entry to the database.
     * @param id The ID of the second-level chat entry.
     * @param comment_id The ID of the associated comment.
     * @param user_name The username of the user posting the reply.
     * @param comment_name The name of the user being replied to.
     * @param date The date when the reply was posted.
     * @param content The content of the reply.
     */
    @Insert("insert into secondChatList(id, comment_id, user_name, date, content, comment_name) values (#{id}, #{comment_id}, #{user_name}, #{date}, #{content}, #{comment_name})")
    void add(@Param("id") int id, 
             @Param("comment_id") int comment_id, 
             @Param("user_name") String user_name, 
             @Param("comment_name") String comment_name, 
             @Param("date") String date, 
             @Param("content") String content);

    /**
     * Selects all second-level chat entries associated with a particular comment.
     * @param comment_id The ID of the comment for which replies are being fetched.
     * @return A list of `SecondChat` objects that are replies to the specified comment.
     */
    @Select("select * from secondChatList where comment_id = #{comment_id} order by unique_id desc")
    List<SecondChat> selectChatList(@Param("comment_id") int comment_id);

    /**
     * Selects the count of second-level chat entries for a given comment.
     * @param comment_id The ID of the comment for which the number of replies is being counted.
     * @return The number of second-level chat entries for the specified comment.
     */
    @Select("select count(*) from secondChatList where comment_id = #{comment_id}")
    int selectCount(@Param("comment_id") int comment_id);

    /**
     * Deletes all second-level chat entries associated with a given comment.
     * @param comment_id The ID of the comment whose replies should be deleted.
     */
    @Delete("delete from secondChatList where comment_id = #{comment_id}")
    void deleteSecondChat(@Param("comment_id") int comment_id);

    /**
     * Selects all second-level chat entries posted by a particular user.
     * @param id The ID of the user whose replies are being fetched.
     * @return A list of `SecondChat` objects posted by the specified user.
     */
    @Select("select * from secondChatList where id = #{id}")
    List<SecondChat> selectOwnReply(@Param("id") int id);

    /**
     * Deletes a specific second-level chat entry by its unique ID.
     * @param unique_id The unique ID of the second-level chat entry to be deleted.
     */
    @Delete("delete from secondChatList where unique_id = #{unique_id}")
    void deleteOwnReply(@Param("unique_id") int unique_id);

    /**
     * Updates the username in the second-level chat entries.
     * @param beforeUser_name The current username that needs to be replaced.
     * @param afterUser_name The new username that will replace the old one.
     */
    void changeUsername(@Param("beforeUser_name") String beforeUser_name, 
                        @Param("afterUser_name") String afterUser_name);

    /**
     * Updates the commenter's name in the second-level chat entries.
     * @param beforeComment_name The current commenter's name that needs to be replaced.
     * @param afterComment_name The new commenter's name that will replace the old one.
     */
    void changeCommentName(@Param("beforeComment_name") String beforeComment_name, 
                           @Param("afterComment_name") String afterComment_name);
}
