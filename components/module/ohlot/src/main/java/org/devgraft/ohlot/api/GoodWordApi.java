package org.devgraft.ohlot.api;

import org.devgraft.ohlot.service.GoodWordAddRequest;
import org.devgraft.ohlot.service.GoodWordAddResponse;
import org.devgraft.ohlot.service.GoodWordGetResponse;
import org.devgraft.ohlot.service.GoodWordService;
import org.devgraft.ohlot.service.GoodWordUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("good-words")
@RestController
public class GoodWordApi {
    private final GoodWordService goodWordService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public GoodWordAddResponse addGoodWord(HttpServletRequest hRequest, @RequestBody GoodWordAddRequest request) {
        return goodWordService.addGoodWord(request);
    }

    @GetMapping
    public List<GoodWordGetResponse> getGoodWords() {
        return goodWordService.getGoodWords();
    }

    @PatchMapping("{id}")
    public void updateGoodWord(@PathVariable UUID id, @RequestParam(name = "content") String content) {
        goodWordService.updateGoodWord(new GoodWordUpdateRequest(id, content));
    }

    @DeleteMapping("{id}")
    public void deleteGoodWord(@PathVariable(name = "id") UUID id) {
        goodWordService.deleteGoodWord(id);
    }
}
