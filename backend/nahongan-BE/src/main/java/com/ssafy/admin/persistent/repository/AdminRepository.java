package com.ssafy.admin.persistent.repository;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.tripinfo.persistent.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdminRepository {
    // --- AttractionImage CRUD ---
    void insertAttractionImage(AttractionImage attractionImage);
    AttractionImage selectAttractionImage(int id);
    List<AttractionImage> selectAllAttractionImages();
    void updateAttractionImage(AttractionImage attractionImage);
    void deleteAttractionImage(int id);
    List<Map<String, String>> selectAttractionImageColumns();

    // --- Attractions CRUD ---
    void insertAttractions(Attractions attractions);
    Attractions selectAttractions(int id);
    List<Attractions> selectAllAttractions();
    void updateAttractions(Attractions attractions);
    void deleteAttractions(int id);
    List<Map<String, String>> selectAttractionsColumns();

    // --- CompanionBoard CRUD ---
    void insertCompanionBoard(CompanionBoard companionBoard);
    CompanionBoard selectCompanionBoardById(int articleNo);
    List<CompanionBoard> selectAllCompanionBoards();
    void updateCompanionBoard(CompanionBoard companionBoard);
    void deleteCompanionBoard(int articleNo);
    List<Map<String, String>> selectCompanionBoardColumns();

    // --- ContentType CRUD ---
    void insertContentType(ContentTypes contentTypes);
    ContentTypes selectContentTypeById(int contentTypeId);
    List<ContentTypes> selectAllContentTypes();
    void updateContentType(ContentTypes contentTypes);
    void deleteContentType(int contentTypeId);
    List<Map<String, String>> selectContentTypesColumns();

    // --- Gugun CRUD ---
    void insertGugun(Guguns gugun);
    Guguns selectGugunById(int no);
    List<Guguns> selectAllGuguns();
    void updateGugun(Guguns gugun);
    void deleteGugun(int no);
    List<Map<String, String>> selectGugunsColumns();

    // --- Member CRUD ---
    void insertMember(Member member);
    Member selectMemberById(String id);
    List<Member> selectAllMembers();
    void updateMember(Member member);
    void deleteMember(String id);
    List<Map<String, String>> selectMembersColumns();

    // --- Sido CRUD ---
    void insertSido(Sidos sido);
    Sidos selectSidoById(int no);
    List<Sidos> selectAllSidos();
    void updateSido(Sidos sido);
    void deleteSido(int no);
    List<Map<String, String>> selectSidosColumns();

    // --- Comment CRUD ---
    void insertComment(Comment comment);
    Comment selectCommentById(int id);
    List<Comment> selectCommentsByArticleNo(int articleNo);
    void updateComment(Comment comment);
    void deleteComment(int id);
    List<Map<String, String>> selectCommentsColumns();
}