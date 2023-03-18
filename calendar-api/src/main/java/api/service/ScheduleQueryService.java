package api.service;

import api.dto.AuthUser;
import api.dto.DtoConverter;
import api.dto.ForListScheduleDto;
import com.larry.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.larry.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Larry
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryService {
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    //일별 조회
    public List<ForListScheduleDto> getSchedulesByDay(LocalDate date, AuthUser authUser) {
        return Stream.concat(//concat 을 통해 합친다. scheduleRepository 와 engagementRepository를 합친다.
                //내가 쓴 스케쥴
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(date)) //특정날짜와 겹치는거만 필터링
                        .map(schedule -> DtoConverter.toForListDto(schedule)),
                //내가 참여한 스케쥴
                engagementRepository
                        .findAllByAttendeeId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(date))
                        .map(engagement -> DtoConverter.toForListDto(engagement.getSchedule()))
        ).collect(toList()); //stream 값을 list 값으로 변환 한다.
    }

}
