package com.hacknc.uncc.repository;

import com.hacknc.uncc.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByEventEventId(Long eventId);

    List<Registration> findByUserId(Long userId);
}
