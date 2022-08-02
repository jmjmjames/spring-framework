package com.exam.article;

import com.exam.annotation.AutoWired;
import com.exam.annotation.Service;

@Service
public class ArticleService {
    @AutoWired
    ArticleRepository articleRepository;
}
