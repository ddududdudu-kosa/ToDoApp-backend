package com.todo.member.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.todo.member.dao.MemberMapper;
import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;
import com.todo.member.model.UserProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AmazonS3 s3Client;
	
	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;
	
	@Override
	public void insertMember(MultipartFile file, JoinDTO joinDTO) throws IOException {
        String username = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        
        if(file != null) {
        	String savedFileName = "";
    		String uploadPath = "member/";

    		String originalFileName = file.getOriginalFilename();
            
            // 확장자 추출
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            UUID uuid = UUID.randomUUID();
            savedFileName = uuid.toString() + fileExtension;
            
            //파일경로: 업로드폴더 + uuid.확장자
    		String filePath = uploadPath + savedFileName;
    		s3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), null));
    		String url = s3Client.getUrl(bucketName, filePath).toString();
    		joinDTO.setProfileImg(url);
    		log.info("파일 url 이름 : {}", url);
        }
		
        MemberDTO member = memberMapper.findByEmail(username);

        if (member != null) {
        	// 이미 동일한 이메일로 가입한 이력이 있다는 의미
            return;
        }
        
        joinDTO.setPassword(bCryptPasswordEncoder.encode(password));
        joinDTO.setRole("ROLE_USER");
        log.info("insert 세팅 : {}", joinDTO);
        memberMapper.insertMember(joinDTO);
		
	}

	@Override
	public MemberDTO findByEmail(String email) {
		return memberMapper.findByEmail(email);
	}

	@Override
	public List<UserProfile> getRelevantUserProfiles(Long userId) {
	     return memberMapper.findRelevantUsers(userId);
	}
	  
	  
}
