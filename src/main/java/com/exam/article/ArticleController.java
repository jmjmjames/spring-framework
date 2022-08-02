package com.exam.article;

import com.exam.annotation.AutoWired;
import com.exam.annotation.Controller;
import com.exam.annotation.GetMapping;
import lombok.Getter;

@Controller
public class ArticleController {

    @AutoWired
    private ArticleService articleService;

    @GetMapping("/usr/article/list")
    public void list() {

    }
}
