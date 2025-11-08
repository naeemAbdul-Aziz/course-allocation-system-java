package org.example.service;

import org.example.dto.AllocationRequestDTO;
import org.example.exception.ConflictException;
import org.example.model.Course;
import org.example.model.Student;
import org.example.repository.AllocationRepository;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AllocationServiceIntegrationTest {

    @Autowired
    AllocationService allocationService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AllocationRepository allocationRepository;

    @BeforeEach
    void cleanup() {
        allocationRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void allocate_success_and_listings() {
        Student s = studentRepository.save(new Student("Alice","Smith","alice@example.com"));
        Course c = courseRepository.save(new Course("Intro","CS101",2));

        Long allocId = allocationService.allocate(new AllocationRequestDTO(s.getId(), c.getId()));
        assertThat(allocId).isNotNull();

        var courses = allocationService.getCoursesForStudent(s.getId());
        assertThat(courses).hasSize(1);

        var students = allocationService.getStudentsForCourse(c.getId());
        assertThat(students).hasSize(1);
    }

    @Test
    void allocate_duplicate_throws_conflict() {
        Student s = studentRepository.save(new Student("Bob","Jones","bob@example.com"));
        Course c = courseRepository.save(new Course("Alg","CS201",2));

        allocationService.allocate(new AllocationRequestDTO(s.getId(), c.getId()));

        assertThatThrownBy(() -> allocationService.allocate(new AllocationRequestDTO(s.getId(), c.getId())))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Allocation already exists");
    }

    @Test
    void allocate_capacity_enforced() {
        Student s1 = studentRepository.save(new Student("Carol","White","carol@example.com"));
        Student s2 = studentRepository.save(new Student("Dan","Brown","dan@example.com"));
        Course c = courseRepository.save(new Course("DB","CS301",1));

        allocationService.allocate(new AllocationRequestDTO(s1.getId(), c.getId()));

        assertThatThrownBy(() -> allocationService.allocate(new AllocationRequestDTO(s2.getId(), c.getId())))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Course capacity reached");
    }
}
