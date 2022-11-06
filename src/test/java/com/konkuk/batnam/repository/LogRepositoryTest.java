package com.konkuk.batnam.repository;

import com.konkuk.batnam.domain.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DataJpaTest
public class LogRepositoryTest {
    @Autowired
    LogRepository logRepository;

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();
    }

    @Test
    void getByDate() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            logRepository.save(Log.builder()
                    .objectName("oName")
                    .sector(null)
                    .resultURL("url")
                    .captureURL("url")
                    .result("re")
                    .build());
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth()+7, 0, 0);
        int i = logRepository.countAllByLogDateBetween(start, end);
        Assertions.assertEquals(count, i);
    }
}
