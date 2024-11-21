package com.ssafy.admin.domain;

import com.ssafy.admin.persistent.repository.AdminRepository;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.tripinfo.persistent.entity.*;
import com.ssafy.companion_board.persistent.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    // --- AttractionImage CRUD ---
    public void insertAttractionImage(AttractionImage attractionImage) {
        adminRepository.insertAttractionImage(attractionImage);
    }

    public AttractionImage selectAttractionImage(int id) {
        return adminRepository.selectAttractionImage(id);
    }

    public List<AttractionImage> selectAllAttractionImages() {
        return adminRepository.selectAllAttractionImages();
    }

    public void updateAttractionImage(AttractionImage attractionImage) {
        adminRepository.updateAttractionImage(attractionImage);
    }

    public void deleteAttractionImage(int id) {
        adminRepository.deleteAttractionImage(id);
    }

    public List<Map<String, String>> selectAttractionImageColumns() {
        return adminRepository.selectAttractionImageColumns();
    }

    // --- Attractions CRUD ---
    public void insertAttractions(Attractions attractions) {
        adminRepository.insertAttractions(attractions);
    }

    public Attractions selectAttractions(int id) {
        return adminRepository.selectAttractions(id);
    }

    public List<Attractions> selectAllAttractions() {
        return adminRepository.selectAllAttractions();
    }

    public void updateAttractions(Attractions attractions) {
        adminRepository.updateAttractions(attractions);
    }

    public void deleteAttractions(int id) {
        adminRepository.deleteAttractions(id);
    }

    public List<Map<String, String>> selectAttractionsColumns() {
        return adminRepository.selectAttractionsColumns();
    }

    // --- CompanionBoard CRUD ---
    public void insertCompanionBoard(CompanionBoard companionBoard) {
        adminRepository.insertCompanionBoard(companionBoard);
    }

    public CompanionBoard selectCompanionBoardById(int articleNo) {
        return adminRepository.selectCompanionBoardById(articleNo);
    }

    public List<CompanionBoard> selectAllCompanionBoards() {
        return adminRepository.selectAllCompanionBoards();
    }

    public void updateCompanionBoard(CompanionBoard companionBoard) {
        adminRepository.updateCompanionBoard(companionBoard);
    }

    public void deleteCompanionBoard(int articleNo) {
        adminRepository.deleteCompanionBoard(articleNo);
    }

    public List<Map<String, String>> selectCompanionBoardColumns() {
        return adminRepository.selectCompanionBoardColumns();
    }

    // --- ContentType CRUD ---
    public void insertContentType(ContentTypes contentTypes) {
        adminRepository.insertContentType(contentTypes);
    }

    public ContentTypes selectContentTypeById(int contentTypeId) {
        return adminRepository.selectContentTypeById(contentTypeId);
    }

    public List<ContentTypes> selectAllContentTypes() {
        return adminRepository.selectAllContentTypes();
    }

    public void updateContentType(ContentTypes contentTypes) {
        adminRepository.updateContentType(contentTypes);
    }

    public void deleteContentType(int contentTypeId) {
        adminRepository.deleteContentType(contentTypeId);
    }

    public List<Map<String, String>> selectContentTypesColumns() {
        return adminRepository.selectContentTypesColumns();
    }

    // --- Gugun CRUD ---
    public void insertGugun(Guguns gugun) {
        adminRepository.insertGugun(gugun);
    }

    public Guguns selectGugunById(int no) {
        return adminRepository.selectGugunById(no);
    }

    public List<Guguns> selectAllGuguns() {
        return adminRepository.selectAllGuguns();
    }

    public void updateGugun(Guguns gugun) {
        adminRepository.updateGugun(gugun);
    }

    public void deleteGugun(int no) {
        adminRepository.deleteGugun(no);
    }

    public List<Map<String, String>> selectGugunsColumns() {
        return adminRepository.selectGugunsColumns();
    }

    // --- Member CRUD ---
    public void insertMember(Member member) {
        adminRepository.insertMember(member);
    }

    public Member selectMemberById(String id) {
        return adminRepository.selectMemberById(id);
    }

    public List<Member> selectAllMembers() {
        return adminRepository.selectAllMembers();
    }

    public void updateMember(Member member) {
        adminRepository.updateMember(member);
    }

    public void deleteMember(String id) {
        adminRepository.deleteMember(id);
    }

    public List<Map<String, String>> selectMembersColumns() {
        return adminRepository.selectMembersColumns();
    }

    // --- Sido CRUD ---
    public void insertSido(Sidos sido) {
        adminRepository.insertSido(sido);
    }

    public Sidos selectSidoById(int no) {
        return adminRepository.selectSidoById(no);
    }

    public List<Sidos> selectAllSidos() {
        return adminRepository.selectAllSidos();
    }

    public void updateSido(Sidos sido) {
        adminRepository.updateSido(sido);
    }

    public void deleteSido(int no) {
        adminRepository.deleteSido(no);
    }

    public List<Map<String, String>> selectSidosColumns() {
        return adminRepository.selectSidosColumns();
    }

    // --- Comment CRUD ---
    public void insertComment(Comment comment) {
        adminRepository.insertComment(comment);
    }

    public Comment selectCommentById(int id) {
        return adminRepository.selectCommentById(id);
    }

    public List<Comment> selectCommentsByArticleNo(int articleNo) {
        return adminRepository.selectCommentsByArticleNo(articleNo);
    }

    public void updateComment(Comment comment) {
        adminRepository.updateComment(comment);
    }

    public void deleteComment(int id) {
        adminRepository.deleteComment(id);
    }

    public List<Map<String, String>> selectCommentsColumns() {
        return adminRepository.selectCommentsColumns();
    }
}


