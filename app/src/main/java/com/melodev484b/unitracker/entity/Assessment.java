package com.melodev484b.unitracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String title;
    // Assessments may be "Performance" or "Objective"
    private String type;
    private String date;
    // ID of the associated Course
    private int courseId;

    public Assessment(int assessmentId, String title, String type, String date) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.type = type;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
