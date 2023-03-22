package com.larry.fc.finalproject.core.domain;

import com.larry.fc.finalproject.core.domain.entity.Schedule;
import com.larry.fc.finalproject.core.domain.entity.User;

import java.time.LocalDateTime;

public class Event {
    private Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }

    public Long getId() {
        return this.schedule.getId();
    }
    public User getWriter() {
        return this.schedule.getWriter();
    }

    public LocalDateTime getStartAt() {
        return schedule.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return schedule.getEndAt();
    }


    //시간이 겹치는 로직 메소드
    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.getStartAt().isBefore(endAt) && startAt.isBefore(this.getEndAt());
    }
    // this.getStartAt().isBefore(endAt)는 this 기간의 종료 시간이 입력 기간의 시작 시간
    // 이전인지 확인하고 조건 startAt.isBefore(this.getEndAt())  입력 기간의 시작 시간이 이 기간의 종료 시간 이전인지 확인합니다.
    // 두 조건이 모두 참이면 두 기간이 겹치고 메서드는 '참'을 반환합니다. 그렇지 않으면 'false'를 반환
}
