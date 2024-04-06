package com.hacknc.uncc.service;

import com.hacknc.uncc.entity.Event;
import com.hacknc.uncc.entity.Registration;
import com.hacknc.uncc.entity.RegistrationStatus;
import com.hacknc.uncc.entity.User;
import com.hacknc.uncc.repository.EventRepository;
import com.hacknc.uncc.repository.RegistrationRepository;
import com.hacknc.uncc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository,
                               UserRepository userRepository,
                               EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Registration registerToEvent(Integer userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationTime(LocalDateTime.now());
        registration.setStatus(RegistrationStatus.SUCCESS);
        return registrationRepository.save(registration);
    }

    public boolean cancelRegistration(Long registrationId) {
        Optional<Registration> registrationOpt = registrationRepository.findById(registrationId);
        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            registration.setStatus(RegistrationStatus.CANCELLED);
            registrationRepository.save(registration);
            return true;
        }
        return false;
    }

    public List<Registration> getAllRegistrationsForEvent(Long eventId) {
        return registrationRepository.findByEventEventId(eventId);
    }


    public List<Registration> getAllRegistrationsForUser(Long userId) {
        return registrationRepository.findByUserId(userId);
    }


}
