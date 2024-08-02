package com.todo.story.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 작성자 : 전민재
 * 기능 : story
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDetailsDto {

    private Stories story;
    private List<StoryVisitors> visitors;
    private List<StoryLikes> likes;

    // 생성자, getter, setter 등은 Lombok 어노테이션에 의해 자동으로 처리됩니다.
    // 필요에 따라 추가적인 메서드나 로직을 여기에 구현할 수 있습니다.
}
