package org.example.controller;

import org.example.dto.StudentDTO;
import org.example.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        StudentDTO created = studentService.create(dto);
        return ResponseEntity.created(URI.create("/api/students/" + created.getId())).body(created);
    }

    @GetMapping
    public List<StudentDTO> all() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO get(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
