package com.konkuk.batnam.repository;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Responder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponderRepository extends JpaRepository<Responder, Long> {
    List<Responder> findAllByAirStrip(AirStrip airStrip);

    void deleteAllByAirStrip(AirStrip airStrip);
}
