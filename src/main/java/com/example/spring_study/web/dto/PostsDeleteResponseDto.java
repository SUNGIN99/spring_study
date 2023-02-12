package com.example.spring_study.web.dto;

import com.example.spring_study.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsDeleteResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsDeleteResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
