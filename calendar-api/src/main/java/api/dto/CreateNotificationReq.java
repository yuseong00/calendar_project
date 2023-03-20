package api.dto;

import com.larry.fc.finalproject.core.domain.type.TimeUnit;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;



@Data
public class CreateNotificationReq {   // 알림
    private final String title;
    private final LocalDateTime notifyAt;
    private final RepeatInfo repeatInfo;  // 반복설정

    public List<LocalDateTime> getRepeatTimes() {
        //repeatInfo를 기반으로 알림을 반복할 횟수와 각 알림 사이의 시간 간격에 대한 정보를 포함하는
        // LocalDatetimes 목록을 반환
        if (repeatInfo == null) {              // 반복설정이 없으면 일회성 이벤트
            return Collections.singletonList(notifyAt);
        }

        return IntStream.range(0, repeatInfo.times)
                //range(0, repeatInfo.times) 은 반복설정값!! 3일에 한번알람할거다 아님, 1일에 한번이다.
                .mapToObj(i -> {
                            //i의 값은 range(0, repeatInfo.times)가 반복되면서 나타내는 i값
                            long increment = (long) repeatInfo.interval.intervalValue * i;
                            switch (repeatInfo.interval.timeUnit) {
                                case DAY:
                                    return notifyAt.plusDays(increment);
                                case WEEK:
                                    return notifyAt.plusWeeks(increment);
                                case MONTH:
                                    return notifyAt.plusMonths(increment);
                                case YEAR:
                                    return notifyAt.plusYears(increment);
                                default:
                                    throw new RuntimeException("bad request. not matched time unit");
                            }
                        }
                )
                .collect(toList());
    }

    @Data
    public static class RepeatInfo {
        private final Interval interval;   //기간 ( 3주에 한번, 3일에 한번,, 등)
        private final int times;   // 몇번을 돌린건지
    }

    @Data
    public static class Interval {
        private final int intervalValue;    // 숫자
        private final TimeUnit timeUnit;   //시간에 대한 개념 일,주,월,년
    }
}
