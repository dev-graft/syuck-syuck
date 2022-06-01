package support;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResult {
    boolean success;
    Integer status;
    String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public static CommonResult success() {
        return new CommonResult(true, 200, "Success", LocalDateTime.now());
    }

    public static CommonResult error(HttpStatus errorStatus) {
        return new CommonResult(false, errorStatus.value(), errorStatus.getReasonPhrase(), LocalDateTime.now());
    }

    public static CommonResult error(int errorStatus, String errorMessage) {
        return new CommonResult(false, errorStatus, errorMessage, LocalDateTime.now());
    }
}
