package api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Larry
 */
@Data
public class CreateEventReq {   //이벤트
    private final String title;
    private final String description;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final List<Long> attendeeIds;
}
