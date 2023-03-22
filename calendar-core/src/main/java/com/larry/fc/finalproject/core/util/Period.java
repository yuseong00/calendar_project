package com.larry.fc.finalproject.core.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Larry
 */
@Getter
public class Period {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    private Period(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static Period of(LocalDateTime startAt, LocalDateTime endAt) {
        return new Period(startAt, endAt == null ? startAt : endAt);
    }
    //주별조회, 월별조회시 예외처리 발생
    public static Period of(LocalDate startAt, LocalDate endAt) {
        return new Period(startAt.atStartOfDay(), endAt == null ? startAt.atStartOfDay() : endAt.atStartOfDay());
    }

    public static Period of(LocalDateTime startAt) {
        return new Period(startAt, startAt);
    }

    //입력한 날짜의 시작과 끝에  db에 있는 시작과, 끝의 있는 교차하는 정보를 반환한다.
    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    //아래 메서드가 필요한이유는 일별조회, 주간조회,월간조회를 하게 되면 입력된시간으로 하면
    // 과부하, 관리하기가 힘들기에 이를 일별로 치환을 하는 isOverlapped 메소드가 필요하다. .
    public boolean isOverlapped(LocalDate date) {
        return isOverlapped(date.atStartOfDay(),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999999999)));
                //하루의 끝을 나타태기 위해 시간을 23:59:59.999999999로 설정
    }
    //isOverlapped 메서드는 하루의 시작 및 종료 시간을 나타내는 두 개의 LocalDateTime 매개변수를 사용하는 오버로드된 버전입니다.
    //그니까 내가 21일 1~3시를 입력하면 21일 하루 전체를 date 값으로 치환을 해버린다. 단순하게 시간을 하루전체로 치환한다.
    //주어진 시간 범위를 하루 전체로 대체하여 모든 시간을 확인하지 않아도 된다.

    //주별,월별 조회시 필요
    public boolean isOverlapped(Period period) {
        return isOverlapped(period.startAt, period.endAt);
    }
}
