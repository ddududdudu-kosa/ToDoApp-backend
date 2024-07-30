package com.todo.diaries.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todo.diaries.domain.dto.DiaryDTO;
import com.todo.diaries.service.DiaryService;
import com.todo.diaries.service.S3Service;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;
    
    @Autowired
    private S3Service s3Service;
    
    private static final Logger logger = Logger.getLogger(DiaryController.class.getName());

    @GetMapping
    public List<DiaryDTO> getAllDiaries() {
        return diaryService.getAllDiaries();
    }

    @GetMapping("/{id}")
    public DiaryDTO getDiaryById(@PathVariable Long id) {
        return diaryService.getDiaryById(id);
    }
/*
    @PostMapping
    public void createDiary(@RequestBody DiaryDTO diaryDTO) {
    	System.out.println("Diary to be saved: " + diaryDTO);
        // Insert logic here
        diaryService.createDiary(diaryDTO);
        System.out.println("Diary to be saved 2: " + diaryDTO);
        // Insert logic here
    }
    
    @PostMapping
    public ResponseEntity<String> createDiary(@RequestBody DiaryDTO diaryDTO) {
    	String imageUrl = s3Service.uploadFile(file);
        diaryDTO.setDiaryimg(imageUrl);
        // dDate를 LocalDate로 변환 후 다시 Date로 변환
        LocalDate localDate = LocalDate.parse(diaryDTO.getDDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date sqlDate = Date.valueOf(localDate);
        diaryDTO.setDDate(sqlDate);
        // 현재 시간 설정
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        diaryDTO.setCreateat(currentTimestamp);
        diaryDTO.setStatus("A");        

        diaryService.createDiary(diaryDTO);
        return ResponseEntity.ok("Diary created successfully");
    }*/
    
    @PostMapping("/register")
    public ResponseEntity<String> createDiary(@RequestParam(value = "file", required = false) MultipartFile file, @RequestPart("diaryDTO") DiaryDTO diaryDTO) {
        try {
            // 파일이 있는 경우에만 업로드 처리
            if (file != null && !file.isEmpty()) {
                String imageUrl = s3Service.uploadFile(file);
                diaryDTO.setDiaryimg(imageUrl);
                logger.info("Image URL: " + imageUrl);
            } else {
                logger.info("No file uploaded");
            }

            // dDate를 LocalDate로 변환 후 다시 Date로 변환
            LocalDate localDate = LocalDate.parse(diaryDTO.getDDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date sqlDate = Date.valueOf(localDate);
            diaryDTO.setDDate(sqlDate);

            // 현재 시간 설정
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            diaryDTO.setCreateat(currentTimestamp);
            diaryDTO.setStatus("A");

            // 다이어리 항목 생성
            diaryService.createDiary(diaryDTO);
            return ResponseEntity.ok("Diary created successfully");
        } catch (Exception e) {
            logger.severe("Diary creation failed: " + e.getMessage());
            return ResponseEntity.status(500).body("Diary creation failed: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public void updateDiary(@PathVariable Long id, @RequestBody DiaryDTO diaryDTO) {
        diaryDTO.setId(id);
        diaryService.updateDiary(diaryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
    }
}
