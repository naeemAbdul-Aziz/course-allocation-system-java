package org.example.dto;

public class CourseDTO {
    private Long id;
    private String title;
    private String courseCode;
    private int capacity;

    public CourseDTO() {}

    public CourseDTO(Long id, String title, String courseCode, int capacity) {
        this.id = id;
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
