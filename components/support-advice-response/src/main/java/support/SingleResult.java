package support;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;
    public SingleResult() {
        this.data = null;
    }

    public SingleResult(boolean success, Integer status, String message, LocalDateTime timestamp, T data) {
        super(success, status, message, timestamp);
        this.data = data;
    }

    public static <T>SingleResult<T> success(T data) {
        return new SingleResult<>(true, 200, "Success", LocalDateTime.now(), data);
    }

    public static <T>SingleResult<T> success(T data, int status) {
        return new SingleResult<>(true, status, "Success", LocalDateTime.now(), data);
    }
}
