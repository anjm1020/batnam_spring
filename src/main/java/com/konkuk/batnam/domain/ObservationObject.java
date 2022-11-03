package com.konkuk.batnam.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ObservationObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id")
    private Long id;

    @Column(name = "object_type", columnDefinition = "VARCHAR(50)")
    private String type;

    @Column(name = "object_name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Builder.Default
    @Column(name = "object_count", columnDefinition = "INTEGER")
    private Integer count = 0;

    @CreatedDate
    @Column(name = "object_first_date")
    private LocalDateTime firstDate;

    @LastModifiedDate
    @Column(name = "object_last_date")
    private LocalDateTime lastDate;

    public void addCount() {
        this.count++;
    }
}
