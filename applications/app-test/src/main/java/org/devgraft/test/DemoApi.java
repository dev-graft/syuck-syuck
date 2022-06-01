package org.devgraft.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequestMapping("demo")
@RestController
public class DemoApi {
    @GetMapping
    public String hello() {
        return "Hello";
    }


    @GetMapping("t")
    public Test test1() {
        return new Test("t1");
    }

    @GetMapping("t3")
    public void test3() {
    }

    @AllArgsConstructor
    @Getter
    class Test implements Serializable {
        String message;
    }
}
