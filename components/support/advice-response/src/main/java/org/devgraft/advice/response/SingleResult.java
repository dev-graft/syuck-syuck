package org.devgraft.advice.response;

import java.time.LocalDateTime;

public class SingleResult<T> extends CommonResult {
    private final T data;

    public SingleResult(boolean success, String message, LocalDateTime timestamp, T data) {
        super(success, message, timestamp);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
