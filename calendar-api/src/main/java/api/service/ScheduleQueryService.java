package api.service;

import api.dto.AuthUser;
import api.dto.DtoConverter;
import api.dto.ForListScheduleDto;
import com.larry.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.larry.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.larry.fc.finalproject.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
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
        final Period period = Period.of(date, date);
        return getForListScheduleDtos(authUser, period);   // 일,주,월 이 중복코드제거를 위해 메서드로 만듬.
    }




    //localDate를 기준으로 일주일동안 자료를 반환
    public List<ForListScheduleDto> getSchedulesByWeek(LocalDate startOfWeek, AuthUser authUser) {
        //6일을 더해야 입력한 날짜를 포함해서 총 7일동안의 기간이 섫정된다.
        final Period period = Period.of(startOfWeek, startOfWeek.plusDays(6));
        return getForListScheduleDtos(authUser, period);   // 일,주,월 이 중복코드제거를 위해 메서드로 만듬.
    }




    //yearMonth 기준으로 한달동안 자료를 반환
    public List<ForListScheduleDto> getSchedulesByMonth(YearMonth yearMonth, AuthUser authUser) {
        final Period period = Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
        //atDay(1) 를 쓰면 그 달에 1일을 가져온다.

        return getForListScheduleDtos(authUser, period);   // 일,주,월 이 중복코드제거를 위해 메서드로 만듬.
    }




    //중복코드 메소드로 따로 뺌
    private List<ForListScheduleDto> getForListScheduleDtos(AuthUser authUser, Period period) {
        return Stream.concat(//concat 을 통해 합친다. scheduleRepository 와 engagementRepository를 합친다.
                //내가 쓴 스케쥴
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period)) //특정날짜와 겹치는거만 필터링
                        .map(schedule -> DtoConverter.toForListDto(schedule)),
                //내가 참여한 스케쥴
                engagementRepository
                        .findAllByAttendeeId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.toForListDto(engagement.getSchedule()))
        ).collect(toList());
    }
}
