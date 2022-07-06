package org.devgraft;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {
    @GetMapping("ping")
    public String ping() {
        return "ping";
    }
}
