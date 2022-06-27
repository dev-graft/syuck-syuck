package org.devgraft.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DemoApi {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("join")
    public String join() {
        return "join";
    }
}
