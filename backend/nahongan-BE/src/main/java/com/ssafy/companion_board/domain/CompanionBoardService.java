package com.ssafy.companion_board.domain;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.companion_board.persistent.repository.CompanionBoardRepository;
import com.ssafy.companion_board.web.dto.request.DeleteArticleRequest;
import com.ssafy.companion_board.web.dto.request.GetArticleListRequest;
import com.ssafy.companion_board.web.dto.request.GetArticleRequest;
import com.ssafy.companion_board.web.dto.request.UpdateArticleRequest;
import com.ssafy.companion_board.web.dto.request.WriteArticleRequest;
import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.companion_board.web.dto.response.UpdateArticleResponse;
import com.ssafy.companion_board.web.dto.response.WriteArticleResponse;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.persistent.repository.MemberRepository;
import com.ssafy.tripinfo.persistent.entity.Attractions;
import com.ssafy.tripinfo.persistent.repository.TripInfoRepository;
import com.ssafy.tripinfo.web.dto.request.GetGugunBySidoCodeRequest;
import com.ssafy.tripinfo.web.dto.request.SearchAttractionRequest;
import com.ssafy.tripinfo.web.dto.response.GetFilterContentResponse;
import com.ssafy.tripinfo.web.dto.response.GetGugunBySidoCodeResponse;
import com.ssafy.tripinfo.web.dto.response.SearchAttractionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanionBoardService {

    private final CompanionBoardRepository companionBoardRepository;
    private final MemberRepository memberRepository;
    
    int LIST_SIZE = 10;


	public GetArticleResponse getArticle(int articleNo) throws Exception {
		companionBoardRepository.updateHit(articleNo);
		GetArticleResponse article = companionBoardRepository.findArticle(articleNo);
		Member member = memberRepository.findById(article.getUserId());
		article.setImage(member.getImg());
		return article;
	}

	public GetArticleListResponse getArticles(GetArticleListRequest request, int pgno) {
		// TODO Auto-generated method stub
    	int offset = LIST_SIZE*(pgno-1);
    	List<GetArticleResponse> articles = companionBoardRepository.findAllArticle(request.getKey(), request.getWord(), offset, LIST_SIZE);
    	int count = companionBoardRepository.countArticle(request.getKey(), request.getWord());
    	
		return GetArticleListResponse.builder()
				.articles(articles)
				.currentPage(pgno)
				.lastPage(count/LIST_SIZE+1)
				.build();
	}

	public UpdateArticleResponse updateArticle(UpdateArticleRequest request) throws SQLException {
		GetArticleResponse article = companionBoardRepository.findArticle(request.getArticleNo());
		CompanionBoard companionBoard  = CompanionBoard.builder()
			.userId(article.getUserId())
			.subject(request.getSubject())
			.content(request.getContent())
			.articleNo(article.getArticleNo())
			.hit(article.getHit())
			.registerTime(article.getRegisterTime())
			.build();
		companionBoard.update(request.getSubject(), request.getContent());
		companionBoardRepository.updateArticle(companionBoard);
		return UpdateArticleResponse.from(companionBoard);
	}
	
	public void deleteArticle(DeleteArticleRequest request) {
		companionBoardRepository.deleteArticle(request.getArticleNo());
	}
	
	public void writeArticle(WriteArticleRequest request, String id) {
		CompanionBoard article = CompanionBoard.builder()
			.userId(id)
			.subject(request.getSubject())
			.content(request.getContent())
			.build();
		companionBoardRepository.createArticle(article);
	}
	//페이지 끝번호 넣기, writeArticle 해결
}
