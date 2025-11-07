package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses", uniqueConstraints = {@UniqueConstraint(columnNames = {"course_code"})})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Column(nullable = false)
    private int capacity;

    public Course() {
    }

    public Course(String title, String courseCode, int capacity) {
        this.title = title;
        this.courseCode = courseCode;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
