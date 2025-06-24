package lk.ijse.parkingservice.controller;


import lk.ijse.parkingservice.entity.ParkingSpace;
import lk.ijse.parkingservice.service.ParkingSpaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpaceController {

    private final ParkingSpaceService service;

    public ParkingSpaceController(ParkingSpaceService service) {
        this.service = service;
    }

    @PostMapping
    public ParkingSpace addSpace(@RequestBody ParkingSpace space) {
        return service.addSpace(space);
    }

    @GetMapping
    public List<ParkingSpace> getAll() {
        return service.getAllSpaces();
    }

    @GetMapping("/{id}")
    public ParkingSpace getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ParkingSpace update(@PathVariable Long id, @RequestBody ParkingSpace space) {
        return service.updateSpace(id, space);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSpace(id);
    }

    @PostMapping("/{id}/reserve")
    public ParkingSpace reserve(@PathVariable Long id) {
        return service.reserveSpace(id);
    }

    @PostMapping("/{id}/release")
    public ParkingSpace release(@PathVariable Long id) {
        return service.releaseSpace(id);
    }

    @PutMapping("/{id}/status")
    public ParkingSpace updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status.toUpperCase()); // Status: AVAILABLE, OCCUPIED, RESERVED, etc.
    }

    @GetMapping("/filter")
    public List<ParkingSpace> filter(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean available) {
        return service.filterSpaces(location, available);
    }

    @GetMapping("/search")
    public List<ParkingSpace> searchByLocation(@RequestParam String location) {
        return service.searchByLocation(location);
    }

    @GetMapping("/available")
    public List<ParkingSpace> getAvailableSpaces() {
        return service.getAvailableSpaces();
    }
}
