package com.exam.article;

import com.exam.annotation.Controller;
import com.exam.annotation.GetMapping;

@Controller
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/usr/article/list")
    public void list() {

    }
}
