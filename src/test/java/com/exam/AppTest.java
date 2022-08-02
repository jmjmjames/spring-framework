package com.exam;

import com.exam.article.ArticleController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void junit_assertThat() {
        int rs = 10 + 20;

        assertThat(rs).isEqualTo(30);
    }

    @Test
    void iocArticleController() {
        ArticleController articleController = Container.getArticleController();
        assertThat(articleController).isNotNull();
    }

    @Test
    @DisplayName("싱글톤 체크")
    void iocArticleController_SingleTon() {
        ArticleController articleController1 = Container.getArticleController();
        ArticleController articleController2 = Container.getArticleController();
        assertThat(articleController1).isEqualTo(articleController2);
    }
}
