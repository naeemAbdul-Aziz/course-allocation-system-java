package org.example.controller;

import org.example.dto.CourseDTO;
import org.example.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        CourseDTO created = courseService.create(dto);
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(created);
    }

    @GetMapping
    public List<CourseDTO> all() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public CourseDTO get(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
