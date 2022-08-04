package com.exam.article;

import com.exam.Container;
import com.exam.article.dto.ArticleDto;
import com.exam.mymap.MyMap;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleServiceTest {

    ArticleService articleService;
    MyMap myMap;
    int TEST_DATA_SIZE = 100;

    // @BeforeAll 붙인 아래 메서드는
    @BeforeAll
    void BeforeAll() {
        myMap = Container.getObj(MyMap.class);
        myMap.setDevMode(true);  // 모든 DB 처리시에, 처리되는 SQL을 콘솔에 출력
        articleService = Container.getObj(ArticleService.class);
    }

    @BeforeEach
    void beforeEach() {
        // 게시물 테이블을 깔끔하게 삭제한다.
        // DELETE FROM article; // 보다 TRUNCATE article; 로 삭제하는게 더 깔끔하고 흔적이 남지 않는다.
        truncateArticleTable();
        // 게시물 3개를 만든다.
        // 테스트에 필요한 샘플데이터를 만든다고 보면 된다.
        makeArticleTestData();
    }

    private void truncateArticleTable() {
        // 테이블을 깔끔하게 지워준다.
        myMap.run("TRUNCATE article");
    }

    private void makeArticleTestData() {
        IntStream.rangeClosed(1, TEST_DATA_SIZE).forEach(no -> {
            boolean isBlind = false;
            String title = "제목%d".formatted(no);
            String body = "내용%d".formatted(no);

            myMap.run("""
                    INSERT INTO article
                    SET createdDate = NOW(),
                    modifiedDate = NOW(),
                    title = ?,
                    `body` = ?,
                    isBlind = ?
                    """, title, body, isBlind);
        });
    }

    @Test
    @DisplayName("IOC 작동 확인")
    void getObj() {
        assertThat(articleService).isNotNull();
    }

    @Test
    @DisplayName("DB_에서_Articles__조회")
    void getArticles() {
        List<ArticleDto> articleDtoList = articleService.getArticles();
        assertThat(articleDtoList.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @Test
    @DisplayName("DB_에서_Articles_count__조회")
    void getArticlesCount() {
        // selectLong 메서드 이용
        long articlesCount = articleService.getArticlesCount();

        assertThat(articlesCount).isEqualTo(TEST_DATA_SIZE);
    }

    @Test
    @DisplayName("DB_에서_Article_INSERT")
    void write() {
        long newArticleId = articleService.write("제목 new", "내용 new", false);

        ArticleDto articleDto = articleService.getArticleById(newArticleId);

        assertThat(articleDto.getId()).isEqualTo(newArticleId);
        assertThat(articleDto.getTitle()).isEqualTo("제목 new");
        assertThat(articleDto.getBody()).isEqualTo("내용 new");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isEqualTo(false);
    }

    @Test
    @DisplayName("DB_에서_Article_UPDATE")
    void modify() {
        // given
//        Util.sleep(5000);
        articleService.modify(1, "제목 new", "내용 new", true);

        // when
        ArticleDto articleDto = articleService.getArticleById(1);

        // then
        assertThat(articleDto.getId()).isEqualTo(1);
        assertThat(articleDto.getTitle()).isEqualTo("제목 new");
        assertThat(articleDto.getBody()).isEqualTo("내용 new");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isEqualTo(true);

        // DB에서 받아온 게시물 수정날짜와 자바에서 계산한 현재 날짜를 비교하여(초단위)
        // 그것이 1초 이하로 차이가 난다면
        // 수정날짜가 갱신되었다 라고 볼 수 있음
        long diffSeconds = ChronoUnit.SECONDS.between(articleDto.getModifiedDate(), LocalDateTime.now());
        assertThat(diffSeconds).isLessThanOrEqualTo(1L);
    }

    @Test
    void delete() {
        // given
        articleService.delete(1);
        // when
        ArticleDto articleDto = articleService.getArticleById(1);
        // then
        assertThat(articleDto).isNull();
    }
}
