package com.larry.fc.finalproject.core.exception;

import lombok.Getter;

@Getter
public class CalendarException extends RuntimeException{
    // custom한 예외처리를 하나로 만들고 , 에러코드로 담아서 보낸다.
    //웬만하면 예외처리를 자체적으로 처리하는 생각해서 enum으로 만들어서 처리하는게 낫다.
    private final ErrorCode errorCode;

    public CalendarException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }



}

