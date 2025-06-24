package lk.ijse.parkingservice.service;

import lk.ijse.parkingservice.entity.ParkingSpace;
import lk.ijse.parkingservice.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceService(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    public ParkingSpace addSpace(ParkingSpace space) {
        return repository.save(space);
    }

    public List<ParkingSpace> filterSpaces(String location, Boolean available) {
        if (location != null && available != null) {
            return repository.findByLocationContainingIgnoreCaseAndAvailable(location, available);
        } else if (location != null) {
            return repository.findByLocationContainingIgnoreCase(location);
        } else if (available != null) {
            return repository.findByAvailable(available);
        } else {
            return repository.findAll();
        }
    }

    public List<ParkingSpace> getAllSpaces() {
        return repository.findAll();
    }

    public Optional<ParkingSpace> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteSpace(Long id) {
        repository.deleteById(id);
    }

    public ParkingSpace updateSpace(Long id, ParkingSpace updated) {
        return repository.findById(id).map(existing -> {
            existing.setLocation(updated.getLocation());
            existing.setAvailable(updated.isAvailable());
            existing.setSlotCount(updated.getSlotCount());
            existing.setStatus(updated.getStatus());
            return repository.save(existing);
        }).orElse(null);
    }

    public List<ParkingSpace> searchByLocation(String location) {
        return repository.findByLocationContainingIgnoreCase(location);
    }

    public List<ParkingSpace> getAvailableSpaces() {
        return repository.findByAvailable(true);
    }

    // --- NEW METHODS ---

    public ParkingSpace reserveSpace(Long id) {
        return repository.findById(id).map(space -> {
            space.setAvailable(false);
            space.setStatus("RESERVED");
            return repository.save(space);
        }).orElse(null);
    }

    public ParkingSpace releaseSpace(Long id) {
        return repository.findById(id).map(space -> {
            space.setAvailable(true);
            space.setStatus("AVAILABLE");
            return repository.save(space);
        }).orElse(null);
    }

    public ParkingSpace updateStatus(Long id, String status) {
        return repository.findById(id).map(space -> {
            space.setStatus(status);
            space.setAvailable(!status.equalsIgnoreCase("OCCUPIED") && !status.equalsIgnoreCase("RESERVED"));
            return repository.save(space);
        }).orElse(null);
    }
}
