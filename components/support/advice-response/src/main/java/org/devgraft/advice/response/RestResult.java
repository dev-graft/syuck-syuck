package org.devgraft.advice.response;

import java.time.LocalDateTime;

public class RestResult<T> extends SingleResult<T> {
    private final int status;
    private final String path;
    private final String requestId;

    public RestResult(boolean success, String message, LocalDateTime timestamp, T data, int status, String path, String requestId) {
        super(success, message, timestamp, data);
        this.status = status;
        this.path = path;
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public String getRequestId() {
        return requestId;
    }
}
