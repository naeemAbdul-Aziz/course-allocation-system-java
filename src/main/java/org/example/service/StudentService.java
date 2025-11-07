package org.example.service;

import org.example.dto.StudentDTO;
import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.example.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO create(StudentDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        Student s = new Student(dto.getFirstName(), dto.getLastName(), dto.getEmail());
        Student saved = studentRepository.save(s);
        return toDto(saved);
    }

    public StudentDTO findById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return toDto(s);
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    private StudentDTO toDto(Student s) {
        return new StudentDTO(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail());
    }
}
