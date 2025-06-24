
package lk.ijse.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Data
public class Booking {
    @Id
    private Long id;
    private Long userId;
    private String parkingSlot;
    private LocalDateTime bookingTime;
    private String status;
}
