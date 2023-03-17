package api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Larry
 */
@Data
public class TaskCreateReq {
    private final String title; // 제목
    private final String description; //내용
    private final LocalDateTime taskAt;  //일정
}
