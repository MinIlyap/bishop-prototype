package ru.prilepskij.i.bishop_prototype.controller;

import jakarta.validation.Valid;
import org.example.annotation.WeylandWatchingYou;
import org.example.model.Task;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bishop")
public class BishopController {

    private final TaskService taskService;

    public BishopController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    @WeylandWatchingYou(logToConsole = true, enableKafka = true, kafkaTopic = "audit-topic")
    public ResponseEntity<String> submitTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.submitTask(task));
    }

}