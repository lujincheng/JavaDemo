package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.utils.JwtUtil;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createJwtToken() {
		String token = JwtUtil.createJwtToken("111");
		System.out.println(token);
		System.out.println(JwtUtil.checkToken(token));
	}
}
