package com.ssafy.companion_board.domain;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.companion_board.persistent.repository.CommentRepository;
import com.ssafy.companion_board.persistent.repository.CompanionBoardRepository;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.ParentCommentDto;
import com.ssafy.companion_board.web.dto.request.comment.CreateChildCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.CreateCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentsRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentRequest;
import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentsResponse;
import com.ssafy.tripinfo.web.dto.detail.EventDetailDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public void deleteComment(int commentId) {
		commentRepository.deleteComment(commentId);
	}

	public void createComment(CreateCommentRequest request, String userId) {
		Comment comment;
		if (request instanceof CreateChildCommentRequest childRequest) {
			comment = Comment.builder()
					.articleNo(childRequest.getArticleNo())
					.parentId(childRequest.getParentCommentId())
					.content(childRequest.getContent())
					.userId(childRequest.getUserId())
					.build();
			System.out.println(comment.toString());
			commentRepository.createComment(comment);
		} else if (request instanceof CreateCommentRequest) {
			comment = Comment.builder()
					.articleNo(request.getArticleNo())
					.content(request.getContent())
					.userId(request.getUserId())
					.build();
			commentRepository.createComment(comment);
		}
	}

	public CommentDto getComment(int commentId, String userId) throws SQLException {
		return CommentDto.from(commentRepository.findComment(commentId), userId);		
	}

	public GetCommentsResponse getComments(int articleNo, String userId) throws SQLException {
		List<ParentCommentDto> commentDtos = commentDtosConverter(commentRepository.findComments(articleNo), userId);
		System.out.println("asdfasdfasdfasdfasdfasdfadsasdfasdfasdfasf");
		
		return GetCommentsResponse.from(commentDtos, commentRepository.countComment(articleNo));
		
	}

	public void updateComment(UpdateCommentRequest request) throws SQLException {
		Comment comment = commentRepository.findComment(request.getCommentId());
		comment.updateContent(request.getContent());
		commentRepository.updateComment(comment);
	}
	
	private List<ParentCommentDto> commentDtosConverter(List<Comment> comments, String userId) {
		
		Map<Boolean, List<Comment>> partitionedEntities =
				comments.stream().collect(Collectors.partitioningBy(Comment::isParent));
		System.out.println("여기옴");
		List<Comment> parents = partitionedEntities.get(true);
		List<Comment> children = partitionedEntities.get(false);
		
		List<ParentCommentDto> parentCommentDtos =
				parents.stream()
				.map(parent -> ParentCommentDto.from(parent, userId))
				.collect(Collectors.toList());
		
		for (Comment child : children) {
			parentCommentDtos.stream()
			.filter(
					parentCommentDto ->
					Objects.equals(parentCommentDto.getId(), child.getParentId()))
			.findFirst()
			.ifPresent(
					parentCommentDto ->
					parentCommentDto.addReply(CommentDto.from(child, userId)));
		}
		return parentCommentDtos;
	}
}

