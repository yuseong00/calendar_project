package api.exception;

import com.larry.fc.finalproject.core.exception.CalendarException;
import com.larry.fc.finalproject.core.exception.ErrorCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static api.exception.ErrorHttpStatusMapper.mapToStatus;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CalendarException.class)
    public ResponseEntity<ErrorResponse> handle(CalendarException ex) {
        final ErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode, errorCode.getMessage()),
                mapToStatus(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //validation 예외처리
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        final ErrorCode code = ErrorCode.VALIDATION_FAIL;
        return new ResponseEntity<>(new ErrorResponse(code,
                                    //필드에러를 꺼내와서
                Optional.ofNullable(ex.getBindingResult().getFieldError())
                                    //필드에러로부터 메시지로 바꾼다.
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    //만약 null 값이면 기본으로 getMessage 값을 보여줘
                        .orElse(code.getMessage())),
                mapToStatus(code));
        //한마디로 기존 valid 처리시 에러처리항목에 있으면 기본 메시지로 전달하고 만약에 기본에러처리메시지에 없는
        // 에러가 발생하였을때는 내가 custom한 에러처리로 진행 한다. !!
    }

    @Data
    public static class ErrorResponse {
        private final ErrorCode errorCode;
        private final String errorMessage;
    }
}
