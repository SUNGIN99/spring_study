package com.example.spring_study.web;

import com.example.spring_study.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    /**
     *
     * @RestController
     *      - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줌.
     *      - @Controller 어노테이션 사용시 @ResponseBody를 각 메소드마다 선언했던것을 하지않도록 해줌.
     *      - @RequestBody 어노테이션은 HttpRequest의 본문 requestBody의 내용을 자바 객체로 매핑하는 역할
     *      - @ResponseBody 어노테이션은 자바 객체를 HttpResponse의 본문 responseBody의 내용으로 매핑하는 역할
     *
     * @GetMapping
     *      - HTTP 메소드인 Get의 요청을 받을 수 있는 API 생성
     *      - 이전 형태 : @RequestMapping(method = RequestMethod.GET)으로 사용
     *      - /hello로 요청이 오면 문자열 hello를 반환하는 기능4
     */

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
