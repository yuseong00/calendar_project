package api.service;

import api.dto.AuthUser;
import api.dto.CreateEventReq;
import com.larry.fc.finalproject.core.domain.RequestStatus;
import com.larry.fc.finalproject.core.domain.entity.Engagement;
import com.larry.fc.finalproject.core.domain.entity.Schedule;
import com.larry.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.larry.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.larry.fc.finalproject.core.exception.CalendarException;
import com.larry.fc.finalproject.core.exception.ErrorCode;
import com.larry.fc.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Larry
 */
@Service
@RequiredArgsConstructor
public class EventService {
    //이벤트 참여자의 다른 인베트와 중복이 되면 안된다. (
//    1-2시까지 회의가 있는데, 추가로 다른 회의를 등록할 수 없음.
    // 추가로 이메일 발송
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;
    private final EmailService emailService;

    @Transactional
    public void create(CreateEventReq req, AuthUser authUser) {
        // attendees 의 스케쥴 시간과 겹치지 않는지?
        final List<Engagement> engagementList =
                //findall을 쓰면 데이터가 얼마나 있는지 알고 무식하게 다 갖고오면 과부하
                //최소한으로 조건까지 쿼리로 걸어서 갖고온다.
                engagementRepository.findAllByAttendeeIdInAndSchedule_EndAtAfter(req.getAttendeeIds(),
                        req.getStartAt());
        if (engagementList.stream()
                //기간이 겹치는지 확인하고 (예를 들어 1-1시까지 회의가 있는데, 추가로 다른 회의를 등록할수 없어야 한다.)
                //필요한 데이터만 넘겨서 의존성을 주입한다.
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt())
                        //요청수락을 한 상태인지 를 검증
                        && e.getStatus() == RequestStatus.ACCEPTED)) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(req.getTitle(),
                req.getDescription(),
                req.getStartAt(),
                req.getEndAt(),
                userService.getOrThrowById(authUser.getId()));
        scheduleRepository.save(eventSchedule);

        req.getAttendeeIds().stream()
                .map(userService::getOrThrowById)
                .forEach(user -> {
                    final Engagement e = engagementRepository.save(new Engagement(eventSchedule, user));
                    emailService.sendEngagement(e);
                });
    }

}
