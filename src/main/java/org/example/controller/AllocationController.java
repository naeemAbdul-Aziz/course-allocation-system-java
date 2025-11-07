package org.example.controller;

import org.example.dto.AllocationRequestDTO;
import org.example.dto.CourseDTO;
import org.example.dto.StudentDTO;
import org.example.service.AllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/api/allocations")
    public ResponseEntity<Void> allocate(@RequestBody AllocationRequestDTO req) {
        Long id = allocationService.allocate(req);
        return ResponseEntity.created(URI.create("/api/allocations/" + id)).build();
    }

    @DeleteMapping("/api/allocations/{id}")
    public ResponseEntity<Void> deallocate(@PathVariable Long id) {
        allocationService.deallocate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/students/{id}/courses")
    public List<CourseDTO> getCoursesForStudent(@PathVariable Long id) {
        return allocationService.getCoursesForStudent(id);
    }

    @GetMapping("/api/courses/{id}/students")
    public List<StudentDTO> getStudentsForCourse(@PathVariable Long id) {
        return allocationService.getStudentsForCourse(id);
    }
}
