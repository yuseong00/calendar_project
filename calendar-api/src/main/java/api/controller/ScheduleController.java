package api.controller;

import api.dto.*;
import api.service.EventService;
import api.service.NotificationService;
import api.service.ScheduleQueryService;
import api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

    private final TaskService taskService;
    private final EventService eventService;
    private final NotificationService notificationService;
    private final ScheduleQueryService scheduleQueryService;



    @PostMapping("/tasks")  //할일
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateTaskReq taskCreateReq,
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
    @PostMapping("/events")
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateEventReq eventCreateReq,
                                           AuthUser authUser) {
        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> createTask(
            @Valid @RequestBody CreateNotificationReq notificationCreateReq, AuthUser authUser) {
        notificationService.create(notificationCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/day")
    // 일별조회는 tasks ,events,notifications 싹 다 묶어서 조회를 해야 한다.
    public List<ForListScheduleDto> getSchedulesByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 주석은 date 매개변수의 값이 ISO 날짜(예: "2023-03-18")로
// 형식화되어야 함을 지정합니다. 이 주석은 Spring이 date 매개변수의 문자열 값을 LocalDate 객체로 자동 변환    ) {
        return scheduleQueryService.getSchedulesByDay(date == null ? LocalDate.now() : date, authUser);
    }

    @GetMapping("/week")
    public List<ForListScheduleDto> getSchedulesByWeek(
            AuthUser authUser,           //startOfWeek 특정주의 첫날짜로 주어지면 그 날짜로부터 일주일치를 반환한다.
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek
    ) {
        return scheduleQueryService.getSchedulesByWeek(startOfWeek == null ? LocalDate.now() : startOfWeek, authUser);
    }

    @GetMapping("/month")
    public List<ForListScheduleDto> getSchedulesByMonth(
            AuthUser authUser,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") String yearMonth //2020-08
    ) {
        return scheduleQueryService.getSchedulesByMonth(yearMonth == null ? YearMonth.now() : YearMonth.parse(yearMonth), authUser);
    }

}
