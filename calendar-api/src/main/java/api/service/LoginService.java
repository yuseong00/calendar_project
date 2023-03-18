package api.service;

import api.dto.LoginReq;
import api.dto.SignUpReq;
import com.larry.fc.finalproject.core.domain.entity.User;
import com.larry.fc.finalproject.core.dto.UserCreateReq;
import com.larry.fc.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;


    public void signUp(SignUpReq signUpReq, HttpSession session) {
     /*
         1. UserService 에 create 요청 (이미 존재하는 유저 검증은 UserService 담당)
         2. session 에 담고 리턴
         */
        final User user = userService.create(
                new UserCreateReq(
                        signUpReq.getName(),
                        signUpReq.getEmail(),
                        signUpReq.getPassword(),
                        signUpReq.getBirthday()));
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());

    }

    public void login(LoginReq loginReq , HttpSession session){
       /*
        세션 값이 있으면 리턴
        없으면 비밀번호 체크 후 로그인
       */

        final Long userId=(Long)session.getAttribute(LOGIN_SESSION_KEY);
        if(userId != null){
            return;
        }
        //Optional 사용해서 널체크 해준다.
        final Optional<User> user =userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());
        if(user.isPresent()){
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        }else{
            throw new RuntimeException("password or email not match");
        }



    }




    public void logout(HttpSession session){
       /*
        세션 제거
         */

        session.removeAttribute(LOGIN_SESSION_KEY);

    }
}
