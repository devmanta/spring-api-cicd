package com.example.demo.domain.posts;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void getPosts() {
        //given
        String title = "this is title";
        String content = "this is content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("heejin@heejin.com")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);

        System.out.println("post = " + post);

        postsRepository.deleteAll();
    }
}
