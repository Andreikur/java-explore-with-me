package ru.practicum.endpointHit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "endpoint_hit", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private String app;

    @Column
    @NotEmpty
    private String uri;

    @Column
    @NotEmpty
    private String ip;

    @NotEmpty
    @Column(name = "timestamp_endpoint")
    private String timestamp;
}
