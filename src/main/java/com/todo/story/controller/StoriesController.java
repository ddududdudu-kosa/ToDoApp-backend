package com.todo.story.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.config.security.dto.CustomUserDetails;
import com.todo.member.model.MemberDTO;
import com.todo.member.service.MemberService;
import com.todo.story.model.Stories;
import com.todo.story.model.StoryDetailsDto;
import com.todo.story.model.StoryLikes;
import com.todo.story.model.StoryVisitors;
import com.todo.story.service.StoriesService;

import lombok.extern.slf4j.Slf4j;

/*
 * 작성자 : 전민재
 * 기능 : story
 * */

@Slf4j
@RestController
@RequestMapping("/stories")
public class StoriesController {

    @Autowired
    private StoriesService storiesService;
	@Autowired
	private MemberService memberService;
	

    @PostMapping("/create")
    public ResponseEntity<?> addStory(@RequestBody Stories stories) {
        boolean result = storiesService.addStory(stories);
        if (result) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body("Failed to add story");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable("id") Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        log.info("ㅁㅁ" + userDetails.toString());
        boolean result = storiesService.deleteStory(id, getMember(userDetails.getUsername()).getId());
        log.info("ㅁㅁ" + getMember(userDetails.getUsername()).getId());
        if (result) {
            return ResponseEntity.ok().body("Story deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete story");
        }
    }
    

    @GetMapping("/{storiesId}")
    public ResponseEntity<?> getStory(@PathVariable("storiesId") Long storiesId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = getMember(userDetails.getUsername()).getId();

        try {
            Stories story = storiesService.getStoryById(storiesId); // 스토리 정보 조회
            log.info("ㅁㅁ" + story.toString());
            log.info("ㅁㅁㅁ" + story.getMemberId());
            if (story != null) {
                log.info("ㅁㅁ방문" );
                if (!story.getMemberId().equals(memberId)) {
                    log.info("ㅁㅁ내꺼아니면 인서트" );
                    storiesService.recordVisit(memberId, storiesId); // 방문 기록
                    log.info("ㅁㅁ내꺼아니면 인서트 완료" );
                }
                return ResponseEntity.ok(story);
            } else {
            	log.info("ㅁㅁ내꺼면 상세조회 방문자목록,좋아요목록은 다른api예정");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the story");
        }
    }


    @PostMapping("/{storiesId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable("storiesId") Long storiesId, @RequestBody Map<String, Boolean> like, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = getMember(userDetails.getUsername()).getId();
        boolean result = storiesService.toggleLike(memberId, storiesId, like.get("like"));
        if (result) {
            return ResponseEntity.ok().body("Like status changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to change like status");
        }
    }
    
    @GetMapping("/{storiesId}/details")
    public ResponseEntity<?> getStoryDetails(@PathVariable("storiesId") Long storiesId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = getMember(userDetails.getUsername()).getId();

        try {
            Stories story = storiesService.getStoryById(storiesId);
            if (story != null && story.getMemberId().equals(memberId)) {
                List<StoryVisitors> visits = storiesService.getVisitsForStory(storiesId);
                List<StoryLikes> likes = storiesService.getLikesForStory(storiesId);
                StoryDetailsDto storyDetails = new StoryDetailsDto(story, visits, likes);
                return ResponseEntity.ok(storyDetails);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the story details");
        }
    }

    @GetMapping("/{storiesId}/likes")
    public ResponseEntity<?> getStoryLikes(@PathVariable("storiesId") Long storiesId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = getMember(userDetails.getUsername()).getId();  // 사용자 ID 확인

        try {
            Stories story = storiesService.getStoryById(storiesId);  // 스토리 정보 조회
            if (story != null && story.getMemberId().equals(memberId)) {  // 스토리 소유자 확인
                List<StoryLikes> likes = storiesService.getLikesForStory(storiesId);  // 좋아요 정보 조회
                return ResponseEntity.ok(likes);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");  // 소유자가 아닐 경우 접근 거부
            }
        } catch (Exception e) {
            log.error("Error fetching likes for story: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the likes for the story");
        }
    }


    
    // 사용자의 모든 스토리를 조회하는 엔드포인트
    @GetMapping("/my/{memberId}")
    public ResponseEntity<?> getUserStories(@PathVariable("memberId") Long memberId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long authenticatedMemberId = getMember(userDetails.getUsername()).getId();  // 인증된 사용자의 ID를 조회

        if (!authenticatedMemberId.equals(memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");  // ID가 일치하지 않으면 접근 거부
        }

        try {
            List<Stories> stories = storiesService.getAllStoriesByMemberId(memberId);
            if (stories.isEmpty()) {
                return ResponseEntity.noContent().build();  // 스토리가 없는 경우 No Content 상태 코드 반환
            } else {
                return ResponseEntity.ok(stories);  // 스토리 리스트 반환
            }
        } catch (Exception e) {
            log.error("Error retrieving stories for member: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the stories");
        }
    }
    
    // 24시간 활성 스토리들 조회
    @GetMapping("/user/{memberId}/active") 
    public ResponseEntity<?> getRecentStories(@PathVariable("memberId") Long memberId) {
        try {
            List<Stories> stories = storiesService.getActiveStoriesByMemberId(memberId);
            if (stories.isEmpty()) {
                return ResponseEntity.noContent().build();  // 스토리가 없는 경우 No Content 상태 코드 반환
            } else {
                return ResponseEntity.ok(stories);  // 스토리 리스트 반환
            }
        } catch (Exception e) {
            log.error("Error retrieving recent stories for member: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the recent stories");
        }
    }

    
    
    

    private MemberDTO getMember(String userEmail) {
    	return memberService.findByEmail(userEmail);
    }
   
    

}
