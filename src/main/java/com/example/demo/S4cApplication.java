package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 환경설정파일 혹은 애플리케이션 메인 클래스에 추가
@EnableScheduling
@SpringBootApplication
public class S4cApplication {

	public static void main(String[] args) {
		SpringApplication.run(S4cApplication.class, args);
	}

}
