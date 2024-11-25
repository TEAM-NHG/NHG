package com.ssafy.tripinfo.persistent.repository;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.tripinfo.persistent.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TripInfoRepository {
	//시 정보 쿼리
	List<Sidos> findAllSido() throws SQLException;
	
	String findSidoNameBySidoCode(int sidoCode) throws SQLException;
	
	String findGugunNameByGugunNo(int no) throws SQLException;
	
	//각 시에 맞는 구군정보 쿼리
	List<Guguns> findGugunBySido(@Param("sidoCode") String sidoCode);
	
	Guguns findGugunBySidoNameAndGugunName(@Param("sidoName") String sidoName
									,@Param("gugunName") String gugunName);
	 
	//컨텐츠타입 쿼리
	List<ContentTypes> findAllContentType();
	
	//어트랙션 전체 개수 쿼리
	int countAttraction(
			@Param("sidoCode") String sidoCode, 
	        @Param("gugunCode") String gugunCode, 
	        @Param("contentTypeId") String contentTypeId, 
	        @Param("keyword") String keyword);
	
	//필터 어트랙션 검색 쿼리
	List<Attractions> findAttractionByFilter(
	        @Param("sidoCode") String sidoCode, 
	        @Param("gugunCode") String gugunCode, 
	        @Param("contentTypeId") String contentTypeId, 
	        @Param("keyword") String keyword, 
	        @Param("offset") int offset, 
	        @Param("size") int size
	);

	Attractions findAttractionByNo(@Param("no") int no);

	void saveImages(@Param("images") List<AttractionImage> images);

	void modifyAttractions(Attractions attraction);

	List<AttractionImage> findImageByContentId(int contentId);
} 