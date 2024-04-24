package com.example.myBoard.service;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    @DisplayName("전체데이터_읽기")
    void findAll() {
        //Given
        int totalCount =3;
        //When
        List<ArticleDto> lists =  articleService.findAll();
        int actualCount = lists.size();
        //Then
        assertThat(actualCount).isEqualTo(totalCount);
    }

    @Test
    @DisplayName("자료입력_테스트")
    void insertArticle() {
        //Given
        ArticleDto expectDto = ArticleDto.builder()
                .id(4L)
                .title("라라라")
                .content("444")
                .build();
        //When
        articleService.insertArticle(new ArticleDto(null,"라라라","444"));
        //Then
        assertThat(articleService.findById(4L).getTitle()).isEqualTo("라라라");
        assertThat(articleService.findById(4L).getContent()).isEqualTo("444");
    }

    @Test
    @DisplayName("단건자료검색_테스트")
    void findById() {
        //Given
        ArticleDto expectDto = ArticleDto.builder()
                .id(2L)
                .title("나나나")
                .content("222")
                .build();
        //When
        ArticleDto actualDto = articleService.findById(2L);
        //Then
        assertThat(actualDto.toString()).isEqualTo(expectDto.toString());
    }

    @Test
    @DisplayName("데이터수정_테스트")
    void updateArticle() {
        //Given
        // 1번 자료 수정(1, 가가가, 111) -> (1, 타이틀, 내용)
        ArticleDto expectDto = ArticleDto.builder()
                .id(1L)
                .title("타이틀")
                .content("내용")
                .build();
        //When
        articleService.updateArticle(new ArticleDto(1L, "타이틀", "내용"));
        ArticleDto actualDto = articleService.findById(1L);
        //Then
        assertThat(actualDto.toString()).isEqualTo(expectDto.toString());
    }

    @Test
    @DisplayName("자료삭제_테스트")
    void deleteById() {
        //Given
        //1번 자료 삭제후 null인지 확인
        Long deleteId = 1L;
        //When
        articleService.deleteById(deleteId);
        //Then
        ArticleDto dto = articleService.findById(1L);
        assertThat(dto).isEqualTo(null);
    }
}