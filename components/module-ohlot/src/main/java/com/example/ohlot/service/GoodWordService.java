package com.example.ohlot.service;

import java.util.List;

public interface GoodWordService {
    GoodWordAddResponse addGoodWord(GoodWordAddRequest request);
    List<GoodWordGetResponse> getGoodWords();
    void updateGoodWord(GoodWordUpdateRequest request);
}
