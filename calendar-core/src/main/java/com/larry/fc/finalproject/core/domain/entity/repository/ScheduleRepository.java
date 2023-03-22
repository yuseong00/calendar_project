package com.larry.fc.finalproject.core.domain.entity.repository;

import com.larry.fc.finalproject.core.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByWriter_Id(Long id);


    //메소드 이름의 컨벤션만 지기면 쿼리를 만들어 준다.?
    //PA에서 특정 메서드 명명 규칙을 따르는 경우 명시적으로 쿼리를 작성할 필요 없이
    // 자동으로 해당 SQL 쿼리를 생성합니다.이를 "메서드 이름에서 쿼리 생성"이라고 합니다.
    //"findAllByWriter_Id"는 "writer" 특성에 지정된 "id" 값이 있는 모든 일정 엔터티를
    // 검색하는 쿼리를 생성하도록 JPA에 지시한다.
//이는 메서드 이름이 "findBy<attributeName>" 규칙을 따르기 때문입니다.
// 여기서 "attributeName"은 쿼리하려는 속성의 이름이고 "_Id"는 " 작가" 속성.
}
