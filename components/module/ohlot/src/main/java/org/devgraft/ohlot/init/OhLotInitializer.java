package org.devgraft.ohlot.init;

import org.devgraft.ohlot.service.GoodWordAddRequest;
import org.devgraft.ohlot.service.GoodWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class OhLotInitializer {
    private final GoodWordService goodWordService;
    @PostConstruct
    public void init() {
        goodWordService.addGoodWord(new GoodWordAddRequest("안녕하세요!"));
        goodWordService.addGoodWord(new GoodWordAddRequest("오늘 하루도 행복하세요!"));
        goodWordService.addGoodWord(new GoodWordAddRequest("지금 기분은 어떠신가요?"));
        goodWordService.addGoodWord(new GoodWordAddRequest("하루가 빙글빙글 돌아가요"));
        goodWordService.addGoodWord(new GoodWordAddRequest("슉슉"));
    }
}
