package com.example.ohlot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpyGoodWordService implements GoodWordService {
    public GoodWordAddRequest addGoodWord_argument;
    public boolean getGoodWords_wasGetGoodWords = false;
    public GoodWordUpdateRequest updateGoodWord_argument;

    @Override
    public GoodWordAddResponse addGoodWord(GoodWordAddRequest request) {
        addGoodWord_argument = request;
        return new GoodWordAddResponse("id", request.getContent());
    }

    @Override
    public List<GoodWordGetResponse> getGoodWords() {
        getGoodWords_wasGetGoodWords = true;

        ArrayList<GoodWordGetResponse> response = new ArrayList<>();
        response.add(new GoodWordGetResponse("id", "content",
                LocalDateTime.of(2022, 2, 2, 22, 22, 22),
                LocalDateTime.of(2022, 2, 2, 22, 22, 22)));

        return response;
    }

    @Override
    public void updateGoodWord(GoodWordUpdateRequest request) {
        this.updateGoodWord_argument = request;
    }
}
