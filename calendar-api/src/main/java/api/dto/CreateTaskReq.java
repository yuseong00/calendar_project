package api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Larry
 */
@Data
public class CreateTaskReq {  //할일
    @NotBlank
    private final String title; // 제목
    private final String description; //내용
    @NotNull
    private final LocalDateTime taskAt;  //일정
}
