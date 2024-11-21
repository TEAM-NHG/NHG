package com.ssafy.admin.web.controller;
import com.ssafy.admin.domain.AdminService;
import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.tripinfo.persistent.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // --- AttractionImage CRUD ---
    @PostMapping("/attraction-images")
    public ResponseEntity<String> createAttractionImage(@RequestBody AttractionImage attractionImage) {
        adminService.insertAttractionImage(attractionImage);
        return new ResponseEntity<>("Attraction Image created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/attraction-images/{id}")
    public ResponseEntity<AttractionImage> getAttractionImage(@PathVariable int id) {
        AttractionImage attractionImage = adminService.selectAttractionImage(id);
        return new ResponseEntity<>(attractionImage, HttpStatus.OK);
    }

    @GetMapping("/attraction-images")
    public ResponseEntity<List<AttractionImage>> getAllAttractionImages() {
        List<AttractionImage> attractionImages = adminService.selectAllAttractionImages();
        return new ResponseEntity<>(attractionImages, HttpStatus.OK);
    }

    @PutMapping("/attraction-images/{id}")
    public ResponseEntity<String> updateAttractionImage(@PathVariable int id, @RequestBody AttractionImage attractionImage) {
        attractionImage.setId(id);
        adminService.updateAttractionImage(attractionImage);
        return new ResponseEntity<>("Attraction Image updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/attraction-images/{id}")
    public ResponseEntity<String> deleteAttractionImage(@PathVariable int id) {
        adminService.deleteAttractionImage(id);
        return new ResponseEntity<>("Attraction Image deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- Attractions CRUD ---
    @PostMapping("/attractions")
    public ResponseEntity<String> createAttractions(@RequestBody Attractions attractions) {
        adminService.insertAttractions(attractions);
        return new ResponseEntity<>("Attractions created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/attractions/{id}")
    public ResponseEntity<Attractions> getAttractions(@PathVariable int id) {
        Attractions attractions = adminService.selectAttractions(id);
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/attractions")
    public ResponseEntity<List<Attractions>> getAllAttractions() {
        List<Attractions> attractions = adminService.selectAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PutMapping("/attractions/{id}")
    public ResponseEntity<String> updateAttractions(@PathVariable int id, @RequestBody Attractions attractions) {
        attractions.setNo(id);
        adminService.updateAttractions(attractions);
        return new ResponseEntity<>("Attractions updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/attractions/{id}")
    public ResponseEntity<String> deleteAttractions(@PathVariable int id) {
        adminService.deleteAttractions(id);
        return new ResponseEntity<>("Attractions deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- CompanionBoard CRUD ---
    @PostMapping("/companion-boards")
    public ResponseEntity<String> createCompanionBoard(@RequestBody CompanionBoard companionBoard) {
        adminService.insertCompanionBoard(companionBoard);
        return new ResponseEntity<>("Companion Board created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/companion-boards/{articleNo}")
    public ResponseEntity<CompanionBoard> getCompanionBoard(@PathVariable int articleNo) {
        CompanionBoard companionBoard = adminService.selectCompanionBoardById(articleNo);
        return new ResponseEntity<>(companionBoard, HttpStatus.OK);
    }

    @GetMapping("/companion-boards")
    public ResponseEntity<List<CompanionBoard>> getAllCompanionBoards() {
        List<CompanionBoard> companionBoards = adminService.selectAllCompanionBoards();
        return new ResponseEntity<>(companionBoards, HttpStatus.OK);
    }

    @PutMapping("/companion-boards/{articleNo}")
    public ResponseEntity<String> updateCompanionBoard(@PathVariable int articleNo, @RequestBody CompanionBoard companionBoard) {
        companionBoard.setArticleNo(articleNo);
        adminService.updateCompanionBoard(companionBoard);
        return new ResponseEntity<>("Companion Board updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/companion-boards/{articleNo}")
    public ResponseEntity<String> deleteCompanionBoard(@PathVariable int articleNo) {
        adminService.deleteCompanionBoard(articleNo);
        return new ResponseEntity<>("Companion Board deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- ContentType CRUD ---
    @PostMapping("/content-types")
    public ResponseEntity<String> createContentType(@RequestBody ContentTypes contentTypes) {
        adminService.insertContentType(contentTypes);
        return new ResponseEntity<>("Content Type created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/content-types/{contentTypeId}")
    public ResponseEntity<ContentTypes> getContentType(@PathVariable int contentTypeId) {
        ContentTypes contentTypes = adminService.selectContentTypeById(contentTypeId);
        return new ResponseEntity<>(contentTypes, HttpStatus.OK);
    }

    @GetMapping("/content-types")
    public ResponseEntity<List<ContentTypes>> getAllContentTypes() {
        List<ContentTypes> contentTypesList = adminService.selectAllContentTypes();
        return new ResponseEntity<>(contentTypesList, HttpStatus.OK);
    }

    @PutMapping("/content-types/{contentTypeId}")
    public ResponseEntity<String> updateContentType(@PathVariable int contentTypeId, @RequestBody ContentTypes contentTypes) {
        contentTypes.setContentTypeId(contentTypeId);
        adminService.updateContentType(contentTypes);
        return new ResponseEntity<>("Content Type updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/content-types/{contentTypeId}")
    public ResponseEntity<String> deleteContentType(@PathVariable int contentTypeId) {
        adminService.deleteContentType(contentTypeId);
        return new ResponseEntity<>("Content Type deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- Gugun CRUD ---
    @PostMapping("/guguns")
    public ResponseEntity<String> createGugun(@RequestBody Guguns gugun) {
        adminService.insertGugun(gugun);
        return new ResponseEntity<>("Gugun created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/guguns/{no}")
    public ResponseEntity<Guguns> getGugun(@PathVariable int no) {
        Guguns gugun = adminService.selectGugunById(no);
        return new ResponseEntity<>(gugun, HttpStatus.OK);
    }

    @GetMapping("/guguns")
    public ResponseEntity<List<Guguns>> getAllGuguns() {
        List<Guguns> guguns = adminService.selectAllGuguns();
        return new ResponseEntity<>(guguns, HttpStatus.OK);
    }

    @PutMapping("/guguns/{no}")
    public ResponseEntity<String> updateGugun(@PathVariable int no, @RequestBody Guguns gugun) {
        gugun.setGugunCode(no);
        adminService.updateGugun(gugun);
        return new ResponseEntity<>("Gugun updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/guguns/{no}")
    public ResponseEntity<String> deleteGugun(@PathVariable int no) {
        adminService.deleteGugun(no);
        return new ResponseEntity<>("Gugun deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- Member CRUD ---
    @PostMapping("/members")
    public ResponseEntity<String> createMember(@RequestBody Member member) {
        adminService.insertMember(member);
        return new ResponseEntity<>("Member created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable String id) {
        Member member = adminService.selectMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = adminService.selectAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<String> updateMember(@PathVariable String id, @RequestBody Member member) {
        member.setId(id);
        adminService.updateMember(member);
        return new ResponseEntity<>("Member updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable String id) {
        adminService.deleteMember(id);
        return new ResponseEntity<>("Member deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- Sido CRUD ---
    @PostMapping("/sidos")
    public ResponseEntity<String> createSido(@RequestBody Sidos sido) {
        adminService.insertSido(sido);
        return new ResponseEntity<>("Sido created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/sidos/{no}")
    public ResponseEntity<Sidos> getSido(@PathVariable int no) {
        Sidos sido = adminService.selectSidoById(no);
        return new ResponseEntity<>(sido, HttpStatus.OK);
    }

    @GetMapping("/sidos")
    public ResponseEntity<List<Sidos>> getAllSidos() {
        List<Sidos> sidos = adminService.selectAllSidos();
        return new ResponseEntity<>(sidos, HttpStatus.OK);
    }

    @PutMapping("/sidos/{no}")
    public ResponseEntity<String> updateSido(@PathVariable int no, @RequestBody Sidos sido) {
        sido.setNo(no);
        adminService.updateSido(sido);
        return new ResponseEntity<>("Sido updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/sidos/{no}")
    public ResponseEntity<String> deleteSido(@PathVariable int no) {
        adminService.deleteSido(no);
        return new ResponseEntity<>("Sido deleted successfully", HttpStatus.NO_CONTENT);
    }

    // --- Comment CRUD ---
    @PostMapping("/comments")
    public ResponseEntity<String> createComment(@RequestBody Comment comment) {
        adminService.insertComment(comment);
        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable int id) {
        Comment comment = adminService.selectCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getComments(@RequestParam int articleNo) {
        List<Comment> comments = adminService.selectCommentsByArticleNo(articleNo);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable int id, @RequestBody Comment comment) {
        comment.setId(id);
        adminService.updateComment(comment);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        adminService.deleteComment(id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.NO_CONTENT);
    }
}


