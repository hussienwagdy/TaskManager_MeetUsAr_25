package com.meetusar.task.repository;

import com.meetusar.task.entity.Task;
import com.meetusar.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwner(User owner);
    Optional<Task> findByIdAndOwner(Long id, User owner);
}
