package com.larry.fc.finalproject.core.domain.entity;

import com.larry.fc.finalproject.core.domain.Event;
import com.larry.fc.finalproject.core.domain.RequestStatus;
import com.larry.fc.finalproject.core.domain.ScheduleType;
import com.larry.fc.finalproject.core.util.Period;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "engagements")
public class Engagement extends BaseEntity {


    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public Engagement(Schedule eventSchedule, User attendee) {
        assert eventSchedule.getScheduleType() == ScheduleType.EVENT;
        this.schedule = eventSchedule;
        this.status = RequestStatus.REQUESTED;
        this.attendee = attendee;
    }

//    public boolean isOverlapped(LocalDate date) {   // schedulequeryservice 리택토링 하면서 정리
//        return this.schedule.isOverlapped(date);
//    }

    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }
    public Event getEvent() {
        return schedule.toEvent();
    }
//    public User getAttendee() {
//        return attendee;
//    }
//    public RequestStatus getStatus() {
//        return status;
//    }

}
