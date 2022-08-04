package com.exam.article;

import com.exam.Rq;
import com.exam.annotation.AutoWired;
import com.exam.annotation.Controller;
import com.exam.annotation.GetMapping;
import lombok.Getter;

@Controller
public class ArticleController {

    @AutoWired
    private ArticleService articleService;

    @GetMapping("/usr/article/list/{boardCode}") // /usr/article/list/free 와 같이 관련된 요청을 처리하는 함수이다.
    // 아래 showList 는 Get /usr/article/list 으로 요청이 왔을 때 실행 되어야 하는 함수이다.
    public void showList(Rq rq) {
        rq.writeln("게시물 리스트");
    }

    @GetMapping("/usr/article/detail/{boardCode}/{id}")
    public void showDetail(Rq rq) {
        rq.writeln("게시물 상세페이지<br>");

        // long id = rq.getLongPathValueByIndex(1, -1);
        String boardCode = rq.getParam("boardCode", ""); // 곧 기능 구현
        long id = rq.getLongParam("id", -1); // 곧 기능 구현

        rq.writeln("%s 게시판, %d번 게시물".formatted(boardCode, id));
    }

    @GetMapping("/usr/article/modify/{boardCode}/{id}")
    public void showModify(Rq rq) {
        rq.writeln("게시물 수정페이지<br>");

        // long id = rq.getLongPathValueByIndex(1, -1);
        String boardCode = rq.getParam("boardCode", ""); // 곧 기능 구현
        long id = rq.getLongParam("id", -1); // 곧 기능 구현

        rq.writeln("%s 게시판, %d번 게시물".formatted(boardCode, id));
    }
}
