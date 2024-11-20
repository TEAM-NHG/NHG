package com.ssafy.companion_board.persistent.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.persistent.entity.ContentTypes;
import com.ssafy.tripinfo.persistent.entity.Guguns;
import com.ssafy.tripinfo.persistent.entity.Sidos;

@Mapper
@Repository
public interface CommentRepository {
	Comment findComment(@Param("commentId") int commentId) throws SQLException;
	
	Comment findCommentByUserId(@Param("userId") String userId) throws SQLException;
	
	List<Comment> findComments(@Param("articleNo") int articleNo) throws SQLException;
	 
	int createComment(Comment comment);
	
	int deleteComment(@Param("commentId") int commentId);
	
	int updateComment(Comment comment);
	
	int countComment(@Param("articleNo") int articleNo);
} 