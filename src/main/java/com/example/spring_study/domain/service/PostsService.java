package com.example.spring_study.domain.service;

import com.example.spring_study.domain.posts.Posts;
import com.example.spring_study.domain.posts.PostsRepository;
import com.example.spring_study.web.dto.PostsResponseDto;
import com.example.spring_study.web.dto.PostsSaveRequestDto;
import com.example.spring_study.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository; // @Autowired사용 X

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        PostsResponseDto responseDto = new PostsResponseDto(entity);
        return responseDto;
    }

}
