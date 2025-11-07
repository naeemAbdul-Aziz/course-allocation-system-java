package org.example.service;

import org.example.dto.CourseDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDTO create(CourseDTO dto) {
        if (dto.getCapacity() < 0) throw new IllegalArgumentException("Capacity must be non-negative");
        Course c = new Course(dto.getTitle(), dto.getCourseCode(), dto.getCapacity());
        Course saved = courseRepository.save(c);
        return toDto(saved);
    }

    public CourseDTO findById(Long id) {
        Course c = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return toDto(c);
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) throw new ResourceNotFoundException("Course not found");
        courseRepository.deleteById(id);
    }

    private CourseDTO toDto(Course c) {
        return new CourseDTO(c.getId(), c.getTitle(), c.getCourseCode(), c.getCapacity());
    }
}
