package com.meetusar.task.controller;

import com.meetusar.task.dto.TaskDto;
import com.meetusar.task.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService taskService;
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@Valid @RequestBody TaskDto dto) {
        TaskDto created = taskService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> all() {
        return ResponseEntity.ok(taskService.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateStatus(@PathVariable Long id,
                                                @RequestBody TaskDto dto) {
        TaskDto updated = taskService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
