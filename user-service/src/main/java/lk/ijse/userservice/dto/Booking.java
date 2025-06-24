
package lk.ijse.userservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private Long userId;
    private String parkingSlot;
    private LocalDateTime bookingTime;
    private String status;
}
