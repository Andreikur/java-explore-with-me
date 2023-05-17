package ru.practicum.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.user.model.User;

import java.util.List;

@RepositoryRestResource(path = "userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT b FROM User b ")
    List<User> findAllPage(Pageable pageable);
}
