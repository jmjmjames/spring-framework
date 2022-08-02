package com.exam;

import com.exam.article.ArticleController;

public class Container {
    private static final ArticleController articleController;

    static {
        articleController = new ArticleController();
    }

    public static ArticleController getArticleController() {
        return articleController;
    }
}
