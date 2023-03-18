package com.larry.fc.finalproject.core.domain.entity;

import com.larry.fc.finalproject.core.domain.Event;
import com.larry.fc.finalproject.core.domain.Notification;
import com.larry.fc.finalproject.core.domain.ScheduleType;
import com.larry.fc.finalproject.core.domain.Task;
import com.larry.fc.finalproject.core.util.Period;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;


    //어떤 데이터로 부터  각 enum 타입에 객체를 만들때 필요
    public static Schedule event(String title,
                                 String description, LocalDateTime startAt,
                                 LocalDateTime endAt, User writer) {
        return Schedule.builder()
                       .startAt(startAt)
                       .endAt(endAt)
                       .title(title)
                       .description(description)
                       .writer(writer)
                       .scheduleType(ScheduleType.EVENT)
                       .build();
    }
    public static Schedule task(String title, String description, LocalDateTime taskAt,
                                User writer) {
        return Schedule.builder()
                       .startAt(taskAt)
                       .title(title)
                       .description(description)
                       .writer(writer)
                       .scheduleType(ScheduleType.TASK)
                       .build();
    }

    public static Schedule notification(String title, LocalDateTime notifyAt, User writer) {
        return Schedule.builder()
                       .startAt(notifyAt)
                       .title(title)
                       .writer(writer)
                       .scheduleType(ScheduleType.NOTIFICATION)
                       .build();
    }

    //도메인 변경가능
    public Task toTask() {
        return new Task(this);
    }

    public Event toEvent() {
        return new Event(this);
    }
    //isOverlapped 첫번쨰 메소드는 특정 날짜가 일정 기간과 겹치는지 확인하는 데 사용됩니다
    //isOverlapped 두번째 메서드는 하루의 시작 및 종료 시간을 나타내는 두 개의 LocalDateTime 매개변수를 사용하는 오버로드된 버전입니다.
    // 이 매개변수를 사용하여 하루의 끝(예: 23:59:59.999999999)을 나타내는 LocalDateTime 개체를 만든 다음 이 종료 시간과
    // 같은 날의 시작 시간으로 첫 번째 isOverlapped 메서드를 호출하여 일정이 하루 종일 겹치는지 확인하십시오.
    public boolean isOverlapped(LocalDate date) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(date);
    }


    public Notification toNotification() {
        return new Notification(this);
    }
}
