package com.todo.diaries.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.diaries.domain.dto.DiaryDTO;
import com.todo.diaries.domain.dto.DiaryDetail;
import com.todo.diaries.mapper.DiaryMapper;

@Service

public class DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    public List<DiaryDTO> getAllDiaries() {
        return diaryMapper.getAllDiaries();
    }

    public DiaryDTO getDiaryById(Long id) {
        return diaryMapper.getDiaryById(id);
    }

    @Transactional
    public void createDiary(DiaryDTO diaryDTO) {
    	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        diaryDTO.setCreateat(currentTime);
    	System.out.println("1111111111111111Diary to be saved: " + diaryDTO);
        try {
        	System.out.println("22222222222222");
            diaryMapper.createDiary(diaryDTO);
            System.out.println("sssssssssssssssssssssDiary saved: " + diaryDTO);
        } catch (Exception e) {
            System.out.println("Error while saving diary: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateDiary(DiaryDTO diaryDTO) {
        diaryMapper.updateDiary(diaryDTO);
    }

    public void deleteDiary(Long id) {
        diaryMapper.deleteDiary(id);
    }
     
    public List<DiaryDetail> getAllRecentDiaries(int page, int size) {
        int offset = (page - 1) * size;
        return diaryMapper.findAllRecentDiaries(offset, size);
    }
    
    public DiaryDTO findByMemberIdAndDate(Long memberId, Date dDate) {
        return diaryMapper.findByMemberIdAndDate(memberId, dDate);
    }
    


}
