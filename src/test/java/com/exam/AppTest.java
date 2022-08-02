package com.exam;

import com.exam.article.ArticleController;
import com.exam.home.HomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    @DisplayName("싱글톤 체크")
    void iocHomeController_SingleTon() {
        HomeController homeController1 = Container.getHomeController();
        HomeController homeController2 = Container.getHomeController();
        assertThat(homeController1).isEqualTo(homeController2);
    }

    @Test
    @DisplayName("IOC_Controller들을_스캔하여_수집")
    void getControllerNames() {
        List<String> names = Container.getControllerNames();

        assertThat(names).contains("home");
        assertThat(names).contains("article");
    }
}
