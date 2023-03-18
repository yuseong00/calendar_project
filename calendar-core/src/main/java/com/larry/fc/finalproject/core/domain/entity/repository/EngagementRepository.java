package com.larry.fc.finalproject.core.domain.entity.repository;

import com.larry.fc.finalproject.core.domain.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByAttendeeIdInAndSchedule_EndAtAfter(List<Long> attendeeIds, LocalDateTime startAt);
    //findBy[EntityProperty]InAnd[EntityProperty]GreaterThan을 따릅니다.
    //attendeeId' 및 'schedule_EndAtAfter'는 엔터티 속성이다.
    //schedule'의 'endAt' 속성이 주어진 'startAt' 시간 이후인 모든 참여를 찾는 데 사용
    //After 키워드는 endAt의 값이 특정 시간 이후여야 함을 지정
    List<Engagement> findAllByAttendeeId(Long id);

}
