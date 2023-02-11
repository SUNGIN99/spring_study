package com.example.spring_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStudyApplication.class, args);
	}
	/**
	 * 23.02.04
	 * @SpringBootApplication 이 스프링 부트의 자동 설정, 스프링 Bean읽기와 생성을 모두 자동으로 설정함.
	 * 해당 어노테이션이 있는 위치부터 설정을 읽어 가기 때문에 프로젝트의 가장 최상단에 항상 위치해야함.
	 *
	 *
	 * Spring.Application.run으로 인해 내장 WAS(Web Application Server)를 실행
	 * 내장 WAS란 외부에 WAS를 두지 않고 애플리케이션 실행시 내부에서 WAS를 실행하는 것.
	 * (서버에 톰캣을 설치할 필요 X, 스프링 부트로 만들어진 Jar파일로 실행)
	 *
	 * WAS 권장 이유 : 언제 어디서나 같은 환경에서 스프링 부트를 배포 가능하기 때문
	 * 외장 WAS 사용 시에 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야 함. 새로운 서버 추가 시에 모든 서버가 같은 WAS환경을 구축해야함
	 *
	 *
	 */
}
