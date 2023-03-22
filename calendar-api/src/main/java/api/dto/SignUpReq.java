package api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Larry
 */
@Data
public class SignUpReq {
     @NotBlank //String 에서 중요한건 공백확인, not null도 확인한다.보통 string 에서는 not black로 한다.
    private final String name;

     @Email
     @NotBlank
    private final String email;

     @Size(min=6, message = "6자리 이상 입력해 주세요")
     @NotBlank
    private final String password;

     @NotNull
    private final LocalDate birthday;
}
