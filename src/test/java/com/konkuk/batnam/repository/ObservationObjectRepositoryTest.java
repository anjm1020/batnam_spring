package com.konkuk.batnam.repository;

import com.konkuk.batnam.domain.ObservationObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@DataJpaTest
class ObservationObjectRepositoryTest {

    @Autowired
    ObservationObjectRepository objectRepository;

    @Test
    @DisplayName("create test")
    void create() {
        ObservationObject object = ObservationObject.builder()
                .type("BIRD")
                .name("비둘기")
                .build();

        ObservationObject saved = objectRepository.save(object);

        Assertions.assertThat(saved.getId()).isEqualTo(object.getId());
    }

    @Test
    @DisplayName("update test")
    void update() throws InterruptedException {
        ObservationObject object = ObservationObject.builder()
                .type("BIRD")
                .name("비둘기")
                .build();

        ObservationObject saved = objectRepository.save(object);

        Integer count = saved.getCount()+1;
        saved.setCount(count);
        ObservationObject updated = objectRepository.save(saved);

        Assertions.assertThat(updated.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(updated.getCount()).isEqualTo(1);

    }

    @Test
    @DisplayName("delete test")
    void delete() {
        ObservationObject object = ObservationObject.builder()
                .type("BIRD")
                .name("비둘기")
                .build();

        ObservationObject saved = objectRepository.save(object);
    }

    @Test
    @DisplayName("read test")
    void read() {

    }
}