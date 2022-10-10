package com.melodev484b.unitracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    int courseId;
    String title;
    String startDate;
    String endDate;
    // Courses may be "In progress", "Completed", "Dropped", or "Planned"
    String status;
    String instructor;
    String instructorPhone;
    String instructorEmail;
    String note;
    // ID of the associated term
    int termId;

    public Course() {
    }

    public Course(int courseId, String title, String startDate, String endDate, String status,
                  String instructor, String instructorPhone, String instructorEmail, String note,
                  int termId) {
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructor = instructor;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.termId = termId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", instructor='" + instructor + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", note='" + note + '\'' +
                ", termId=" + termId +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
