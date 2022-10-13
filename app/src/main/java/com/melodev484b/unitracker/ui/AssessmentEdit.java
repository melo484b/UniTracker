package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.assessment_edit_title);
        editType = findViewById(R.id.assessment_edit_type);
        editDate = findViewById(R.id.assessment_edit_date);
        assessmentId = getIntent().getIntExtra("assessment_id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("course_id", -1);
        if (assessmentId != -1) {
            editTitle.setText(title);
            editType.setText(type);
            editDate.setText(date);
        }
    }

    public void onSaveAssessment(View view) {
        Assessment assessment;
        int newId;
        if (assessmentId == -1) {
            if (repo.isAssessmentsEmpty()) {
                newId = 1;
            }
            else {
                newId = repo.getmAllAssessments().get(repo.getmAllAssessments().size() -1).getAssessmentId() + 1;
            }
            assessment = new Assessment(newId, editTitle.getText().toString(), editType.getText().toString(), editDate.getText().toString(), courseId);
            repo.insert(assessment);
        }
        else {
            assessment = new Assessment(assessmentId, editTitle.getText().toString(), editType.getText().toString(), editDate.getText().toString(), courseId);
            repo.update(assessment);
        }
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
    }
}