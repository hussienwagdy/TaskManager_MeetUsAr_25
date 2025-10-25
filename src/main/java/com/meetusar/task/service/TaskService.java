package com.meetusar.task.service;

import com.meetusar.task.dto.TaskDto;
import com.meetusar.task.entity.Task;
import com.meetusar.task.entity.User;
import com.meetusar.task.exception.ResourceNotFoundException;
import com.meetusar.task.repository.TaskRepository;
import com.meetusar.task.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User currentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public TaskDto create(TaskDto dto) {
        User user = currentUser();
        Task t = new Task(dto.getTitle(), dto.getDescription(), dto.getStatus(), user);
        Task saved = taskRepository.save(t);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<TaskDto> list() {
        User user = currentUser();
        List<Task> tasks = taskRepository.findByOwner(user);
        return tasks.stream().map(t -> {
            TaskDto d = new TaskDto();
            d.setId(t.getId());
            d.setTitle(t.getTitle());
            d.setDescription(t.getDescription());
            d.setStatus(t.getStatus());
            return d;
        }).collect(Collectors.toList());
    }

    @Override
    public TaskDto updateStatus(Long id, String status) {
        User user = currentUser();
        Task t = taskRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for user"));
        t.setStatus(status);
        Task saved = taskRepository.save(t);
        TaskDto dto = new TaskDto();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setDescription(saved.getDescription());
        dto.setStatus(saved.getStatus());
        return dto;
    }

    @Override
    public void delete(Long id) {
        User user = currentUser();
        Task t = taskRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for user"));
        taskRepository.delete(t);
    }
}
