package com.hacknc.uncc.controller;

import com.hacknc.uncc.entity.Registration;
import com.hacknc.uncc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // Endpoint to register for an event
    @PostMapping("/register")
    public ResponseEntity<Registration> registerForEvent(@RequestBody Registration registration) {
        Registration newRegistration = registrationService.registerToEvent(registration.getUser().getId(),registration.getEvent().getEventId());
        return ResponseEntity.ok(newRegistration);
    }

    // Endpoint to cancel a registration
    @PutMapping("/cancel/{registrationId}")
    public ResponseEntity<String> cancelRegistration(@PathVariable Long registrationId) {
        boolean isCancelled = registrationService.cancelRegistration(registrationId);
        if (isCancelled) {
            return ResponseEntity.ok("Registration cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to cancel registration.");
        }
    }

    // Endpoint to get all registrations for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Registration>> getAllRegistrationsForUser(@PathVariable Long userId) {
        List<Registration> registrations = registrationService.getAllRegistrationsForUser(userId);
        return ResponseEntity.ok(registrations);
    }

    // Endpoint to get all registrations for an event
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Registration>> getAllRegistrationsForEvent(@PathVariable Long eventId) {
        List<Registration> registrations = registrationService.getAllRegistrationsForEvent(eventId);
        return ResponseEntity.ok(registrations);
    }
}
