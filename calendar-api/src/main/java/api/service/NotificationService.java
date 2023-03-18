package api.service;

import api.dto.AuthUser;
import api.dto.NotificationCreateReq;
import com.larry.fc.finalproject.core.domain.entity.Schedule;
import com.larry.fc.finalproject.core.domain.entity.User;
import com.larry.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.larry.fc.finalproject.core.util.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Larry
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional //알림을 생성하는 메서드이다.
    public void create(NotificationCreateReq req, AuthUser authUser) {
        //유저정보를 가져온다.
        final User writer = userService.getOrThrowById(authUser.getId());
        //getRepeatTimes 는 알림이 생성되어야 하는 시간을 나타내는 LocalDateTime 개체 목록을 반환
        req.getRepeatTimes()
                .forEach(notifyAt ->
                        scheduleRepository.save(Schedule.notification(req.getTitle(), notifyAt, writer)));
    }
}
