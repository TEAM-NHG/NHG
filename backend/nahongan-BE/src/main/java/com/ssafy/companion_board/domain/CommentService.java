package com.ssafy.companion_board.domain;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ssafy.companion_board.web.dto.CommentNotice;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentNoticeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.repository.CommentRepository;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.ParentCommentDto;
import com.ssafy.companion_board.web.dto.request.comment.CreateChildCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.CreateCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentReadRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentRequest;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentsResponse;

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
		} else if (request != null) {
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

	public void updateComment(UpdateCommentRequest request) throws SQLException {
		Comment comment = commentRepository.findComment(request.getCommentId());
		comment.updateContent(request.getContent());
		commentRepository.updateComment(comment);
	}
	
	public void updateCommentRead(UpdateCommentReadRequest request) throws SQLException {
		commentRepository.updateArticleOwnerRead(request.getCommentId(), request.getUserId());
		commentRepository.updateParentCommentOwnerRead(request.getCommentId(), request.getUserId());
	}

	public GetCommentsResponse getComments(int articleNo, String userId) throws SQLException {
		List<ParentCommentDto> commentDtos = commentDtosConverter(commentRepository.findComments(articleNo));
		return GetCommentsResponse.from(commentDtos, commentRepository.countComment(articleNo));

	}

	private List<ParentCommentDto> commentDtosConverter(List<CommentDto> commentDtos) {
		// 부모 댓글과 자식 댓글로 분리
		Map<Boolean, List<CommentDto>> partitionedDtos = commentDtos.stream()
				.collect(Collectors.partitioningBy(comment -> comment.getParentId() == 0));

		List<CommentDto> parents = partitionedDtos.get(true);  // 부모 댓글
		List<CommentDto> children = partitionedDtos.get(false); // 자식 댓글

		// 부모 댓글 DTO로 변환
		List<ParentCommentDto> parentCommentDtos = parents.stream()
				.map(parent -> ParentCommentDto.builder()
						.id(parent.getId())
						.articleNo(parent.getArticleNo())
						.isOwner(parent.getIsOwner())
						.userId(parent.getUserId())
						.parentId(parent.getParentId())
						.content(parent.getContent())
						.createdAt(parent.getCreatedAt())
						.updatedAt(parent.getUpdatedAt())
						.isArticleOwnerRead(parent.isArticleOwnerRead())
						.isCommentOwnerRead(parent.isCommentOwnerRead())
						.image(parent.getImage())
						.replies(new ArrayList<>()) // 초기 대댓글 리스트
						.build())
				.collect(Collectors.toList());

		// 자식 댓글을 부모 댓글에 매핑
		mapChildrenToParents(parentCommentDtos, children);

		return parentCommentDtos;
	}

	private void mapChildrenToParents(List<ParentCommentDto> parentCommentDtos, List<CommentDto> children) {
		for (CommentDto child : children) {
			parentCommentDtos.stream()
					.filter(parentCommentDto -> Objects.equals(parentCommentDto.getId(), child.getParentId()))
					.findFirst()
					.ifPresent(parentCommentDto -> parentCommentDto.getReplies().add(child));
		}
	}

	public GetCommentNoticeResponse getCommentNotice(String userId) {
		List<CommentNotice> notices = Stream.concat(
						commentRepository.findCommentByUserArticle(userId)
								.stream()
								.map(comment -> CommentNotice.from(comment, "article")),
						commentRepository.findChildCommentByUserParentComment(userId)
								.stream()
								.map(comment -> CommentNotice.from(comment, "comment"))
				)
				.sorted(Comparator.comparing(CommentNotice::getCreatedAt)) // createdAt 필드 기준 정렬
				.collect(Collectors.toList());

		return GetCommentNoticeResponse.builder().comments(notices).build();
	}


}

