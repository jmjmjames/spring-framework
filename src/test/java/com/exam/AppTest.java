package com.exam;

import com.exam.article.ArticleController;
import com.exam.article.ArticleRepository;
import com.exam.article.ArticleService;
import com.exam.home.HomeController;
import com.exam.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void junit_assertThat() {
        int rs = 10 + 20;

        assertThat(rs).isEqualTo(30);
    }

    @Test
    void iocArticleController() {
        ArticleController articleController = Container.getObj(ArticleController.class);
        assertThat(articleController).isNotNull();
    }

    @Test
    @DisplayName("싱글톤 체크")
    void iocArticleController_SingleTon() {
        ArticleController articleController1 = Container.getObj(ArticleController.class);
        ArticleController articleController2 = Container.getObj(ArticleController.class);
        assertThat(articleController1).isEqualTo(articleController2);
    }

    @Test
    @DisplayName("싱글톤 체크")
    void iocHomeController_SingleTon() {
        HomeController homeController1 = Container.getObj(HomeController.class);
        HomeController homeController2 = Container.getObj(HomeController.class);
        assertThat(homeController1).isEqualTo(homeController2);
    }

    @Test
    @DisplayName("IOC_Controller들을_스캔하여_수집")
    void getControllerNames() {
        List<String> names = Container.getControllerNames();

        assertThat(names).contains("home");
        assertThat(names).contains("article");
    }

    @Test
    void iocArticleService() {
        ArticleService articleService = Container.getObj(ArticleService.class);
        assertThat(articleService).isNotNull();
    }

    @Test
    void iocArticleService_Singleton() {
        ArticleService articleService1 = Container.getObj(ArticleService.class);
        ArticleService articleService2 = Container.getObj(ArticleService.class);

        assertThat(articleService1).isEqualTo(articleService2);
    }

    @Test
    @DisplayName("컨트롤러에 서비스 주입 확인")
    void iocArticleController_ArticleServiceInjection() {
        ArticleController articleController = Container.getObj(ArticleController.class);
        ArticleService articleService = Util.reflection
                .getFieldValue(articleController, "articleService", null);

        assertThat(articleService).isNotNull();
    }

    @Test
    @DisplayName("서비스에 리포지토리 주입 확인")
    void iocArticleService_ArticleRepositoryInjection() {
        ArticleService articleService = Container.getObj(ArticleService.class);
        ArticleRepository articleRepository = Util.reflection.getFieldValue(articleService, "articleRepository", null);

        assertThat(articleRepository).isNotNull();
    }

    @Test
    public void ControllerManager__라우트정보_개수() {
        Map<String, RouteInfo> routeInfos = ControllerManager.getRouteInfosForTest();

        assertThat(routeInfos.size()).isEqualTo(4);
    }
}
