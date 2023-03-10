package com.example.spring_study.web;

import com.example.spring_study.domain.posts.Posts;
import com.example.spring_study.domain.posts.PostsRepository;
import com.example.spring_study.web.dto.PostsDeleteResponseDto;
import com.example.spring_study.web.dto.PostsSaveRequestDto;
import com.example.spring_study.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_????????????() throws Exception{
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                            .title(title)
                                            .content(content)
                                            .author("author")
                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        /** mockmvc ???????????????
         * // when
         *         ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
         *
         *         // then
         *         assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
         *         assertThat(responseEntity.getBody()).isGreaterThan(0L);
         */

        //when  mockmvc??????
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts???_????????????() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updatedId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto =
                PostsUpdateRequestDto.builder()
                        .title(expectedTitle)
                        .content(expectedContent)
                        .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        /** mockmvc?????????
         *
         * //when
         *         ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
         *
         *         // then
         *         assertThat(responseEntity.getStatusCode()).
         *                 isEqualTo(HttpStatus.OK);
         *         assertThat(responseEntity.getBody()).isGreaterThan(0L);
         */

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void posts_??????() throws Exception{
        //given
        // 1) ??? ???????????? ????????? ???????????? ?????? ??????
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long deleteId = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteId;

        // when
        // 2) ???????????? ???????????? ???????????? ID?????? ???????????? ?????? api ??????
        ResponseEntity<PostsDeleteResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, PostsDeleteResponseDto.class);

        // then
        // 3) ?????? ???????????? ???????????? ?????????, ???????????? ???????????? ?????? ResponseDto ????????? ?????? ???????????? ????????? ????????? ?????? ????????? ??????
        assertThat(responseEntity.getStatusCode()).
                isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(deleteId);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo("title");
        assertThat(responseEntity.getBody().getAuthor()).isEqualTo("author");
        assertThat(responseEntity.getBody().getContent()).isEqualTo("content");

        // 4) ??????????????? ?????? ?????? ?????? ?????????, ???????????? ?????? ??????????????? ???????????? ????????????!
        List<Posts> all = postsRepository.findAll();
        assertThat(all.isEmpty());
    }
}