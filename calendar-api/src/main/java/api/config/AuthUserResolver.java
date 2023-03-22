package api.config;

import api.dto.AuthUser;
import com.larry.fc.finalproject.core.exception.CalendarException;
import com.larry.fc.finalproject.core.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static api.service.LoginService.LOGIN_SESSION_KEY;

public class AuthUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    } //supportsParameter의 역할은 parameter가 AuthUser 의 타입인지 확인하는 메서드이다.

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Long userId = (Long)webRequest.getAttribute(LOGIN_SESSION_KEY, webRequest.SCOPE_SESSION);
        if(userId==null){
            throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
        return AuthUser.of(userId);
    }
    //resolveArgument 를 통해 로직을 수행
    //webRequest.SCOPE_SESSION은 NativeWebRequest 객체에서 검색되는 속성의 범위
}

//스프링은 빈을 프록시해서 사용한다.
//특정 컨트롤에 메소드 안에 그 인자 하나를 파라메터 로 메타 정의했다.
//webRequest.SCOPE_SESSION은 NativeWebRequest 객체에서 검색되는 속성의 범위