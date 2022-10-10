package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;

public class AssessmentEdit extends AppCompatActivity {
    EditText editTitle;
    EditText editType;
    EditText editDate;
    EditText editCourse;
    int assessmentId;
    String title;
    String type;
    String date;
    int courseId;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        editTitle = findViewById(R.id.assessment_edit_title);
        editType = findViewById(R.id.assessment_edit_type);
        editDate = findViewById(R.id.assessment_edit_date);
        editCourse = findViewById(R.id.assessment_edit_course);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("courseId", -1);
        if (assessmentId != -1) {
            editTitle.setText(title);
            editType.setText(type);
            editDate.setText(date);
            editCourse.setText(repo.getCourseById(courseId).getTitle());
        }
        repo = new Repository(getApplication());
    }

    // Select courseId from course
    public void onAssessmentEditSaveButton(View view) {
        Assessment assessment;
        int newId;
        if (assessmentId == -1) {
            if (repo.isAssessmentsEmpty()) {
                newId = 1;
            }
            else {
                newId = repo.getmAllAssessments().get(repo.getmAllAssessments().size() -1).getAssessmentId() + 1;
            }
            assessment = new Assessment(newId, editTitle.getText().toString(), editType.getText().toString(), editDate.getText().toString(), 999);
            repo.insert(assessment);
        }
        else {
            assessment = new Assessment(assessmentId, editTitle.getText().toString(), editType.getText().toString(), editDate.getText().toString(), courseId);
            repo.update(assessment);
        }
    }
}