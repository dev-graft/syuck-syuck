package org.devgraft.advice.response;

public class SingleResult<T> extends CommonResult {
    private final T data;

    public SingleResult(boolean success, String message, T data) {
        super(success, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
