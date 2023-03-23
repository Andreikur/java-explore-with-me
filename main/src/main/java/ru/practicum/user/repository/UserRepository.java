package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.user.model.User;

@RepositoryRestResource(path = "userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
}
