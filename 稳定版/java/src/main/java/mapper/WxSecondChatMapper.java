package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.SecondChat;

import java.util.List;

/**
 * This interface defines the operations for interacting with the WX second chat records in the database.
 * It provides methods to add, retrieve, delete, and update second-level replies in the `wx_secondChatList` table.
 */
public interface WxSecondChatMapper {

    /**
     * Adds a new second-level chat record to the `wx_secondChatList` table.
     * @param id The unique ID of the second-level chat.
     * @param comment_id The ID of the parent comment.
     * @param user_name The name of the user replying.
     * @param comment_name The name of the user who made the parent comment.
     * @param date The date when the reply was posted.
     * @param content The content of the second-level chat reply.
     * @param comment_user_id The ID of the user who made the parent comment.
     * @param head_pic The profile picture of the user replying.
     */
    @Insert("insert into wx_secondChatList(id, comment_id, user_name, date, content, comment_name, comment_user_id, head_pic) values (#{id}, #{comment_id}, #{user_name}, #{date}, #{content}, #{comment_name}, #{comment_user_id}, #{head_pic})")
    void add(@Param("id") int id, 
             @Param("comment_id") int comment_id, 
             @Param("user_name") String user_name, 
             @Param("comment_name") String comment_name, 
             @Param("date") String date, 
             @Param("content") String content, 
             @Param("comment_user_id") int comment_user_id, 
             @Param("head_pic") String head_pic);

    /**
     * Retrieves all second-level chat records for a specific comment by its comment ID.
     * @param comment_id The ID of the parent comment for which second-level replies are fetched.
     * @return A list of `SecondChat` objects representing the second-level replies.
     */
    @Select("select * from wx_secondChatList where comment_id = #{comment_id} order by unique_id desc")
    List<SecondChat> selectChatList(@Param("comment_id") int comment_id);

    /**
     * Retrieves the count of second-level chat records for a specific comment by its comment ID.
     * @param comment_id The ID of the parent comment to count the second-level replies for.
     * @return The count of second-level replies for the specified comment.
     */
    @Select("select count(*) from wx_secondChatList where comment_id = #{comment_id}")
    int selectCount(@Param("comment_id") int comment_id);

    /**
     * Deletes all second-level chat records for a specific comment by its comment ID.
     * @param comment_id The ID of the parent comment for which second-level replies are to be deleted.
     */
    @Delete("delete from wx_secondChatList where comment_id = #{comment_id}")
    void deleteSecondChat(@Param("comment_id") int comment_id);

    /**
     * Retrieves all second-level chat replies made by a specific user, identified by their ID.
     * @param id The ID of the user whose second-level replies are to be fetched.
     * @return A list of `SecondChat` objects representing the user's second-level replies.
     */
    @Select("select * from wx_secondChatList where id = #{id}")
    List<SecondChat> selectOwnReply(@Param("id") int id);

    /**
     * Deletes a specific second-level chat reply by its unique ID.
     * @param unique_id The unique ID of the second-level chat reply to be deleted.
     */
    @Delete("delete from wx_secondChatList where unique_id = #{unique_id}")
    void deleteOwnReply(@Param("unique_id") int unique_id);

    /**
     * Changes the username of the user who posted a specific second-level chat reply.
     * @param id The ID of the second-level chat reply whose username is to be changed.
     * @param afterUser_name The new username to be set for the user.
     */
    void changeUsername(@Param("id") int id, 
                        @Param("afterUser_name") String afterUser_name);

    /**
     * Changes the name of the user who posted the parent comment in a second-level chat reply.
     * @param comment_user_id The ID of the user who made the parent comment.
     * @param afterComment_name The new commenter's name to be set for the parent comment.
     */
    void changeCommentName(@Param("comment_user_id") int comment_user_id, 
                           @Param("afterComment_name") String afterComment_name);
}
