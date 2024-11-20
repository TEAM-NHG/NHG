package com.ssafy.companion_board.domain;

import java.sql.SQLException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.companion_board.persistent.repository.CommentRepository;
import com.ssafy.companion_board.persistent.repository.CompanionBoardRepository;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.request.comment.CreateCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentsRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentRequest;
import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
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

	public void createComment(CreateCommentRequest request) {
		Comment comment = Comment.builder()
						.articleNo(request.getArticleNo())
						.content(request.getContent())
						.userId(request.getUserId())
						.build();
		commentRepository.createComment(comment);
	}

	public CommentDto getComment(int commentId) throws SQLException {
		return CommentDto.from(commentRepository.findComment(commentId));		
	}

	public GetCommentsResponse getComments(int articleNo) throws SQLException {
		System.out.println(commentRepository.findComments(articleNo).get(0).getId());
		return GetCommentsResponse.from(commentRepository.findComments(articleNo), commentRepository.countComment(articleNo));
		
	}

	public void updateComment(UpdateCommentRequest request) throws SQLException {
		Comment comment = commentRepository.findComment(request.getCommentId());
		comment.updateContent(request.getContent());
		commentRepository.updateComment(comment);
	}
	
}

