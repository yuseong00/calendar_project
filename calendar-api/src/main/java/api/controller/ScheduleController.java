package api.controller;

import api.dto.AuthUser;
import api.dto.EventCreateReq;
import api.dto.TaskCreateReq;
import api.service.EventService;
import api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

    private final TaskService taskService;
    private final EventService eventService;


    @PostMapping("/tasks")  //할일
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateReq taskCreateReq,
                                           HttpSession session,
                                           AuthUser authUser){
//        //유저의 정보를 갖고온다
//        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
//        //유저 유효검증
//        if(userId==null){
//            throw new RuntimeException("bad request. no session");
//        }
//        taskService.create(taskCreateReq, AuthUser.of(userId));

        //이전에는 세션을 가지고 와서 로그인 유효검증처리를 한다.
        //만약에 컨트롤 메서드를 호출하기이전에 AuthUser 매서드 인자로 선언이 되어있다면
        //AuthUser를 넣어주기 위해서 세션에서 이미 유효검증처리를 한다.
        //AuthUser 를 인자로 만들어 준다. 그 역할을 한다.
        //argumentResolver 하는 역할이다.
        //argumentResolver의 사전적 의미는 동적방식으로 메서도 또는 함수 인수를 처리하고 해결하는데
        //사용되는 메커니즘이다. 권한을


        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();


    }
    @PostMapping("/event")
    public ResponseEntity<Void> createTask(@RequestBody EventCreateReq eventCreateReq,
                                           AuthUser authUser) {
        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }





}
