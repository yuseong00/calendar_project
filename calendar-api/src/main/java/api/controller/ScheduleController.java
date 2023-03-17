package api.controller;

import api.dto.AuthUser;
import api.dto.TaskCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

    private final TaskService taskService;

    @PostMapping("/tasks")  //할일
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateReq taskCreateReq,
                                           HttpSession session){
        //유저의 정보를 갖고온다
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        //유저 유효검증
        if(userId==null){
            throw new RuntimeException("bad request. no session");
        }
        taskService.create(taskCreateReq, AuthUser.of(userId));
        return ResponseEntity.ok().build();


    }




}
