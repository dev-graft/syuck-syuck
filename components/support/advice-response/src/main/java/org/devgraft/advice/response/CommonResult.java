package org.devgraft.advice.response;

public class CommonResult {
    private final boolean success;
    private final String message;

    public CommonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
