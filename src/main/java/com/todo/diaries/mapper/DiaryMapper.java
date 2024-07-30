package com.todo.diaries.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.todo.diaries.domain.dto.DiaryDTO;

@Mapper
public interface DiaryMapper {
    List<DiaryDTO> getAllDiaries();

    DiaryDTO getDiaryById(@Param("id") Long id);

    void createDiary(DiaryDTO diary);

    void updateDiary(DiaryDTO diary);

    void deleteDiary(Long id);
}