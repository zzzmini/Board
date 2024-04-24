package com.example.myBoard.dto;

import com.example.myBoard.entity.Article;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public static ArticleDto from(Article article) {
        return new ArticleDto(article.getId(), article.getTitle(), article.getContent());
    }
}
