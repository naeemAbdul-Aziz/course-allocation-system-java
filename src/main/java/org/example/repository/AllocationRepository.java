package org.example.repository;

import org.example.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    long countByCourseId(Long courseId);
    List<Allocation> findByStudentId(Long studentId);
    List<Allocation> findByCourseId(Long courseId);
}
