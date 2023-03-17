package com.larry.fc.finalproject.core.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//상속을 한 클래스가 부모의 속성만 따서 자식클래스가 된다.
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

//AuditingEntityListener 가 jpa 영속화 되기전에 @CreatedDate   @LastModifiedDate
//설정을 해준다.
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();


    @LastModifiedDate
    private LocalDateTime updatedAt;


    public BaseEntity(Long id) {
        this.id = id;
    }
}
