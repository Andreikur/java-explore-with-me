package ru.practicum.endpointHit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoint_hit", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String app;

    //@NotEmpty
    private String uri;

    //@NotEmpty
    private String ip;

    //@NotEmpty
    @Column(name = "timestamp_endpoint")
    private LocalDateTime timestamp;
}
