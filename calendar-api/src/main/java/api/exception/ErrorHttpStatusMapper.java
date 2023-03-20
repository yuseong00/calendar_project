package api.exception;

import com.larry.fc.finalproject.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ErrorHttpStatusMapper {
    //단일 모듈이었으면 GlobalExceptionHandler 여기서 컨트롤이 가능하다.
    //abstract로 만들면 생성자로 만들필요 없다.
    public static HttpStatus mapToStatus(ErrorCode errorCode) {
        switch (errorCode) {
            case ALREADY_EXISTS_USER:
            case VALIDATION_FAIL:
            case BAD_REQUEST:
            case EVENT_CREATE_OVERLAPPED_PERIOD:
                return HttpStatus.BAD_REQUEST;
            case PASSWORD_NOT_MATCH:
            case USER_NOT_FOUND:
                return HttpStatus.UNAUTHORIZED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
