package lk.ijse.parkingservice.repository;


import lk.ijse.parkingservice.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findByLocationContainingIgnoreCase(String location);
    List<ParkingSpace> findByAvailable(boolean available);
   List<ParkingSpace> findByLocationContainingIgnoreCaseAndAvailable(String location, boolean available);

}
