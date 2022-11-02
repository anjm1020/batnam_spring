package com.konkuk.batnam.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @CreatedDate
    @Column(name = "log_date")
    private LocalDateTime logDate;

    @Column(name = "log_capture_image_url",columnDefinition = "VARCHAR(2083)")
    private String captureURL;

    @Column(name = "log_result_imgae_url", columnDefinition = "VARCHAR(2083)")
    private String resultURL;

    @Column(name = "log_result",columnDefinition = "VARCHAR(255)")
    private String result;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @OneToOne
    @JoinColumn(name = "object_id")
    private ObservationObject object;
}
