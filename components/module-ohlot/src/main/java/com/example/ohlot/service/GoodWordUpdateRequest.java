package com.example.ohlot.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodWordUpdateRequest {
    private String id;
    private String content;
}
