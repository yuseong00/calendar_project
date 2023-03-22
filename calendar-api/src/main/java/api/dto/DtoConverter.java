package api.dto;

import com.larry.fc.finalproject.core.domain.entity.Schedule;
import com.larry.fc.finalproject.core.exception.CalendarException;
import com.larry.fc.finalproject.core.exception.ErrorCode;

/**
 * @author Larry
 */
public abstract class DtoConverter {
//abstract 로 쓰게 되면 생성자 호출을 안한다.
    public static ForListScheduleDto toForListDto(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return ForListEventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return ForListTaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return ForListNotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}
