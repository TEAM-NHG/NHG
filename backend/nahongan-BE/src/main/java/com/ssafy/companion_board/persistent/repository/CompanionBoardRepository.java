package com.ssafy.companion_board.persistent.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.persistent.entity.ContentTypes;
import com.ssafy.tripinfo.persistent.entity.Guguns;
import com.ssafy.tripinfo.persistent.entity.Sidos;

@Mapper
@Repository
public interface CompanionBoardRepository {
	GetArticleResponse findArticle(@Param("articleNo") int articleNo) throws SQLException;
	
	List<GetArticleResponse> findAllArticle(
			@Param("key") String key, 
			@Param("word") String word, 
			@Param("offset") int offset, 
	        @Param("size") int size
	);
	
	int updateHit(int articleNo) throws SQLException;
	 
	int createArticle(CompanionBoard companionBoard);
	
	int deleteArticle(@Param("articleNo") int articleNo);
	
	int countArticle(@Param("key") String key, @Param("word") String word);
	
	int updateArticle(CompanionBoard companionBoard);
} 