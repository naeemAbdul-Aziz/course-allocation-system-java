package org.example.service;

import org.example.dto.AllocationRequestDTO;
import org.example.dto.CourseDTO;
import org.example.dto.StudentDTO;
import org.example.exception.ConflictException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Allocation;
import org.example.model.Course;
import org.example.model.Student;
import org.example.repository.AllocationRepository;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AllocationService(AllocationRepository allocationRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.allocationRepository = allocationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Long allocate(AllocationRequestDTO req) {
        if (req.getStudentId() == null || req.getCourseId() == null) {
            throw new IllegalArgumentException("studentId and courseId are required");
        }

        Student student = studentRepository.findById(req.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // C-2 Duplicate prevention
        boolean exists = allocationRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());
        if (exists) {
            throw new ConflictException("Allocation already exists for given student and course");
        }

        // C-1 Capacity enforcement
        long current = allocationRepository.countByCourseId(course.getId());
        if (current >= course.getCapacity()) {
            throw new ConflictException("Course capacity reached");
        }

        Allocation allocation = new Allocation(student, course);
        Allocation saved = allocationRepository.save(allocation);
        return saved.getId();
    }

    public void deallocate(Long id) {
        if (!allocationRepository.existsById(id)) throw new ResourceNotFoundException("Allocation not found");
        allocationRepository.deleteById(id);
    }

    public List<CourseDTO> getCoursesForStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) throw new ResourceNotFoundException("Student not found");
        return allocationRepository.findByStudentId(studentId).stream().map(a -> new CourseDTO(a.getCourse().getId(), a.getCourse().getTitle(), a.getCourse().getCourseCode(), a.getCourse().getCapacity())).collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsForCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) throw new ResourceNotFoundException("Course not found");
        return allocationRepository.findByCourseId(courseId).stream().map(a -> new StudentDTO(a.getStudent().getId(), a.getStudent().getFirstName(), a.getStudent().getLastName(), a.getStudent().getEmail())).collect(Collectors.toList());
    }
}
