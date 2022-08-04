package com.exam.article;

import com.exam.annotation.AutoWired;
import com.exam.annotation.Service;
import com.exam.article.dto.ArticleDto;

import java.util.List;

@Service
public class ArticleService {
    @AutoWired
    ArticleRepository articleRepository;

    public List<ArticleDto> getArticles() {
        return articleRepository.getArticles();
    }

    public ArticleDto getArticleById(long id) {
        return articleRepository.getArticleById(id);
    }

    public long getArticlesCount() {
        return articleRepository.getArticlesCount();
    }

    public long write(String title, String body, boolean isBlind) {
        return articleRepository.write(title, body, isBlind);
    }

    public void modify(long id, String title, String body, boolean isBlind) {
        articleRepository.modify(id, title, body, isBlind);
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }
}
