package lk.ijse.vehicleservice.service;

import lk.ijse.vehicleservice.entity.Vehicle;
import lk.ijse.vehicleservice.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle save(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public List<Vehicle> getAll() {
        return repository.findAll();
    }

    public Vehicle getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vehicle> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Vehicle update(Long id, Vehicle updated) {
        return repository.findById(id).map(v -> {
            v.setLicensePlate(updated.getLicensePlate());
            v.setVehicleType(updated.getVehicleType());
            v.setUserId(updated.getUserId());
            return repository.save(v);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Vehicle simulateEntry(Long id) {
        Vehicle vehicle = getById(id);
        // simulate entry logic, e.g., log entry time or flag
        System.out.println("Vehicle " + vehicle.getLicensePlate() + " entered.");
        return vehicle;
    }

    public Vehicle simulateExit(Long id) {
        Vehicle vehicle = getById(id);
        // simulate exit logic, e.g., log exit time or flag
        System.out.println("Vehicle " + vehicle.getLicensePlate() + " exited.");
        return vehicle;
    }

}
