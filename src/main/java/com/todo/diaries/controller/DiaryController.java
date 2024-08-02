package com.todo.diaries.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import com.todo.config.security.dto.CustomUserDetails;
import com.todo.diaries.domain.dto.DiaryDTO;
import com.todo.diaries.domain.dto.DiaryDetail;
import com.todo.diaries.service.DiaryService;
import com.todo.diaries.service.S3Service;
import com.todo.member.model.MemberDTO;
import com.todo.member.service.MemberService;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;
    
    @Autowired
    private S3Service s3Service;
    
    private static final Logger logger = Logger.getLogger(DiaryController.class.getName());
    
    @Autowired
    private MemberService memberService;
    
    private MemberDTO getMember(String userEmail) {
        return memberService.findByEmail(userEmail);
     }

    @GetMapping
    public List<DiaryDTO> getAllDiaries() {
        return diaryService.getAllDiaries();
    }

    @GetMapping("/{id}")
    public DiaryDTO getDiaryById(@PathVariable("id") Long id) {
        return diaryService.getDiaryById(id);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> createDiary(@RequestParam(value = "file", required = false) MultipartFile file, @RequestPart("diaryDTO") DiaryDTO diaryDTO, Authentication authentication) {
        try {
            // 인증된 사용자 정보 가져오기
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long authenticatedMemberId = getMember(userDetails.getUsername()).getId();  // 인증된 사용자의 ID를 조회

            // diaryDTO에 memberId 설정
            diaryDTO.setMemberId(authenticatedMemberId);
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
    
    
    @GetMapping("/recentList")
    public ResponseEntity<List<DiaryDetail>> getRecentDiaries(
    	@RequestParam(name = "page",defaultValue = "1") int page,
        @RequestParam(name = "size",defaultValue = "10") int size) {
        List<DiaryDetail> diaries = diaryService.getAllRecentDiaries(page, size);
        if (diaries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(diaries);
    }
    
    public ResponseEntity<Map<String, Object>> checkDiary(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String selectedDateStr = request.get("selectedDate");

        // 날짜 형식을 java.sql.Date 객체로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date selectedDate;
        try {
            selectedDate = new java.sql.Date(dateFormat.parse(selectedDateStr).getTime());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid date format"));
        }

        MemberDTO member = memberService.findByEmail(email);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid user"));
        }

        DiaryDTO diary = diaryService.findByMemberIdAndDate(member.getId(), selectedDate);
        if (diary != null) {
            return ResponseEntity.ok(Map.of("diaryExists", true, "diaryId", diary.getId()));
        } else {
            return ResponseEntity.ok(Map.of("diaryExists", false));
        }
    }
}
