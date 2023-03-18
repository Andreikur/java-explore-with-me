package ru.practicum.endpointHit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.endpointHit.model.EndpointHit;

@RepositoryRestResource(path = "endpoint_hit")
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
}
