package com.example.myBoard.service;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import com.example.myBoard.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleDto> findAll() {
        List<ArticleDto> dtoList = new ArrayList<>();
        dtoList = articleRepository.findAll()
                .stream()
                .map(x -> ArticleDto.from(x))
                .toList();
        return dtoList;
    }

    public void insertArticle(ArticleDto dto) {
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        articleRepository.save(article);
    }

    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null){
            return null;
        } else
        return ArticleDto.from(articleRepository.findById(id).orElse(null));
    }

    public void updateArticle(ArticleDto dto) {
        Article article = Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public Page<Article> pagingList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
