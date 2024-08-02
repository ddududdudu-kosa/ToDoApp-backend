package com.todo.story.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.todo.story.model.Stories;
import com.todo.story.model.StoryLikes;
import com.todo.story.model.StoryVisitors;

/*
 * 작성자 : 전민재
 * 기능 : story
 * */

@Mapper
public interface StoriesMapper {
    void insertStory(Stories stories);
    
    int deleteStory(Map<String, Object> params);

    Stories findStoryById(Long storiesId);
    // 방문 기록을 위한 메서드
    int insertVisitor(Map<String, Object> params);
    
    // 좋아요 중복 체크 메서드
    int checkLikeExists(Map<String, Object> params);
    // 본인 스토리 좋아요 시도 체크 메서드
    int checkIsOwner(Map<String, Object> params);
    // 좋아요 추가를 위한 메서드
    void insertLike(Map<String, Object> params);

    // 좋아요 삭제를 위한 메서드
    int deleteLike(Map<String, Object> params);

    // 좋아요 수 업데이트를 위한 메서드
    void updateLikesCount(Map<String, Object> params);
     
    // 스토리의 좋아요 목록
    List<StoryLikes> findLikesByStoryId(Long storiesId);
    // 스토리의 방문자 목록 
    List<StoryVisitors> findVisitorsByStoryId(Long storiesId);

    // 사용자의 모든 스토리를 조회하는 메서드
    List<Stories> findAllStoriesByMemberId(Long memberId);
     // 최근 24시간 이내에 생성된 스토리를 조회하는 메서드
    List<Stories> findActiveStoriesByMemberId(Long memberId);
    
}
