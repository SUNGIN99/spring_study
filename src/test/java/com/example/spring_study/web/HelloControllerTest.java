package com.example.spring_study.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
        /**
         * @RunWith(SpringRunner.class)
         * 테스트 진행시에 JUnit에 내장된 실행자 외에 다른 실행자를 실행 시킴.
         * 해당 코드에서는 SpringRunner라는 스프링 실행자 사용
         * 즉, 스프링 부트 테스트와 JUnit 사이의 연결자 역할
         *
         * @WebMvcTest
         * 여러 스프링 테스트 어노테이션 중, Web에 집중할 수 있는 어노테이션
         * 선언할 경우 @Controller, @ControllerAdvice 등을 사용 가능
         * @Service, @Component, @Repository 등은 사용 X
         * 컨트롤러만 사용하기에 선언
         *
         * @Autowired
         * 스프링이 관리하는 빈(Bean)을 주입 받음
         *
         * MockMvc
         * 웹 API를 테스트할 때 사용
         * 스프링 MVC테스트의 시작점
         * HTTP GET/POST 등에 대한 API 테스트 가능
         *
         * mvc.perform(get("/hello"))
         * MockMvc 클래스를 통해 /hello 주소로 HTTP GET 요청을 함
         *
         * andExpect(status().isOk())
         * mvc.perform 함수에 대한 결과 검증, HTTP Header 의 Status 를 검증
         * 200, 400, 500 등 상태 코드
         * isOK() 는 200인지 아닌지를 검증
         *
         * andExepct(content.string(hello))
         * mvc.perform 함수에 대한 결과 검증, 응답 본문의 내용을 검증
         * Controller에서 리턴하는 "hello"와 값이 맞는지 검증.
         */
    }


    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}