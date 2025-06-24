package lk.ijse.parkingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    private boolean available = true;

    private int slotCount;

    @Column(nullable = false)
    private String status = "AVAILABLE"; // e.g., AVAILABLE, OCCUPIED, RESERVED, UNAVAILABLE
}
