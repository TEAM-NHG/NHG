package com.ssafy.companion_board.persistent.repository;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.companion_board.web.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.companion_board.persistent.entity.Comment;

@Mapper
@Repository
public interface CommentRepository {
	Comment findComment(@Param("commentId") int commentId) throws SQLException;
	
	Comment findCommentByUserId(@Param("userId") String userId) throws SQLException;
	
	List<CommentDto> findComments(@Param("articleNo") int articleNo) throws SQLException;
	 
	int createComment(Comment comment);
	
	int deleteComment(@Param("commentId") int commentId);
	
	int updateComment(Comment comment);
	
	int countComment(@Param("articleNo") int articleNo);
	
	List<CommentDto> findCommentByUserArticle(String userId);
	
	List<CommentDto> findChildCommentByUserParentComment(String userId);
	
	void updateArticleOwnerRead(int commentId, String userId);
	void updateParentCommentOwnerRead(int commentId, String userId);
} 