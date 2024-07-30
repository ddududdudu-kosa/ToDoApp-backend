package com.todo.member.model;

import lombok.Data;

@Data
public class UserProfile {
    private Long id;         // 사용자 ID
    private String nickname; // 사용자 닉네임
    private String profileImg; // 프로필 이미지 URL
    private String privacy;  // 개인정보 설정 (오픈, 비공개 등)
    private String relationType; // 관계 유형 (본인, 팔로잉, 오픈)

}