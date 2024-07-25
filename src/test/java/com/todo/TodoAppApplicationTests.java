package com.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TodoAppApplicationTests {

	@Autowired
	TestMapper testMapper;
	
	@Test
	@DisplayName("테스트 테이블의 1번째 row의 'NO'컬럼의 값이 '테스트 입력 데이터'인지 확인")
	void testDBConnectionByMyBatis() {
		List<Map<String, Object>> list = testMapper.findAll();
		log.info("테스트 테이블 값 : {}",list.toString());
		
		// 테스트 테이블의 1번째 row의 'NO'컬럼의 값이 '테스트 입력 데이터'인지 확인
		assertThat(list.get(0).get("NO")).isEqualTo("테스트 입력 데이터");
	}

}
