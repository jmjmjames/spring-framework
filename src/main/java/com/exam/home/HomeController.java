package com.exam.home;

import com.exam.Rq;
import com.exam.annotation.Controller;
import com.exam.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/usr/home/main")
    public void showMain(Rq rq) {
        rq.writeln("메인 페이지");
    }
}
