package com.example.spring_study.web;

import com.example.spring_study.config.auth.LoginUser;
import com.example.spring_study.config.auth.dto.SessionUser;
import com.example.spring_study.domain.service.PostsService;
import com.example.spring_study.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        //어노테이션으로 재구성하여 파라미터로 받게됨
        if (user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);

        model.addAttribute("post", dto);

        return "posts-update";
    }
}
