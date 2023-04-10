package ru.practicum.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.location.model.Location;

@RepositoryRestResource(path = "locationRepository")
public interface LocationRepository  extends JpaRepository<Location, Long> {
}
