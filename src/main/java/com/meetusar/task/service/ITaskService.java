package com.meetusar.task.service;

import com.meetusar.task.dto.TaskDto;

import java.util.List;

public interface ITaskService {
    TaskDto create(TaskDto dto);
    List<TaskDto> list();
    TaskDto updateStatus(Long id, String status);
    void delete(Long id);
}
