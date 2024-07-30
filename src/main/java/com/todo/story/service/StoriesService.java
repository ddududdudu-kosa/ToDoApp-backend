package com.todo.story.service;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.todo.story.dao.StoriesMapper;
import com.todo.story.model.Stories;
import com.todo.story.model.StoryLikes;
import com.todo.story.model.StoryVisitors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoriesService {

    private final StoriesMapper storiesMapper; 
    

    public Stories getStoryById(Long storiesId) {
        // 이 메서드는 스토리 정보를 데이터베이스에서 가져오는 로직을 구현해야 합니다.
        // 예시로 단순한 조회 기능을 구현합니다.
        return storiesMapper.findStoryById(storiesId);
    }
    public List<StoryLikes> getLikesForStory(Long storiesId) {
        return storiesMapper.findLikesByStoryId(storiesId);
    }

    public List<StoryVisitors> getVisitsForStory(Long storiesId) {
        return storiesMapper.findVisitorsByStoryId(storiesId);
    }
    
    // 사용자의 모든 스토리를 조회하는 메서드
    public List<Stories> getAllStoriesByMemberId(Long memberId) {
        try {
            return storiesMapper.findAllStoriesByMemberId(memberId);
        } catch (Exception e) {
            log.error("Error retrieving stories for member: " + e.getMessage(), e);
            return Collections.emptyList();  // 예외 발생 시 빈 리스트 반환
        }
    }
    
    // 최근 24시간 이내에 생성된 스토리를 조회하는 메서드
    public List<Stories> getActiveStoriesByMemberId(Long memberId) {
        try {
            return storiesMapper.findActiveStoriesByMemberId(memberId);
        } catch (Exception e) {
            log.error("Error retrieving active stories for member: " + e.getMessage(), e);
            return Collections.emptyList();  // 예외 발생 시 빈 리스트 반환
        }
    }
    
    
    public boolean addStory(Stories stories) {
        try {
            storiesMapper.insertStory(stories);
            log.info("Story added successfully");
            return true;
        } catch (Exception e) {
            log.error("Failed to add story: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStory(Long id, Long memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("memberId", memberId);
        try {
            int rowsDeleted = storiesMapper.deleteStory(params);
            if (rowsDeleted > 0) {
                log.info("Story deleted successfully");
                return true;
            } else {
                log.info("No story to delete or permission denied");
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete story: " + e.getMessage());
            return false;
        }
    }
    
    


    public void recordVisit(Long memberId, Long storiesId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("storiesId", storiesId);
            int result = storiesMapper.insertVisitor(params);
            if (result > 0) {
                log.info("Visit recorded successfully");
            } else {
                log.info("Visit not recorded (may be the owner or already visited)");
            }
        } catch (Exception e) {
            log.error("Failed to record visit: " + e.getMessage());
        }
    }
    
    public boolean toggleLike(Long memberId, Long storiesId, boolean like) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("storiesId", storiesId);
            int alreadyLiked = storiesMapper.checkLikeExists(params); // 좋아요 중복 체크
            int isOwner = storiesMapper.checkIsOwner(params); // 본인 스토리 체크
            
            if (like) {
                if (alreadyLiked == 0 && isOwner == 0) {
                    storiesMapper.insertLike(params);
                    storiesMapper.updateLikesCount(params);
                    log.info("Like added successfully");
                    return true;
                } else {
                    log.info("No like added due to duplication or self-like attempt");
                    return false;
                }
            } else {
                int rowsDeleted = storiesMapper.deleteLike(params);
                if (rowsDeleted > 0) {
                    storiesMapper.updateLikesCount(params);
                    log.info("Like removed successfully");
                    return true;
                } else {
                    log.info("No like to delete or permission denied");
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("Failed to change like status: " + e.getMessage());
            return false;
        }
    }



    
    
    
    
    
}
