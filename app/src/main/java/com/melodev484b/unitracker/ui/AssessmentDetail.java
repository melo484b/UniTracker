package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;

public class AssessmentDetail extends AppCompatActivity {

    TextView titleText, typeText, dateText;
    int assessmentId, courseId;
    String title, type, date;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.assessment_detail_title);
        typeText = findViewById(R.id.assessment_detail_type);
        dateText = findViewById(R.id.assessment_detail_date);
        assessmentId = getIntent().getIntExtra("assessment_id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("course_id", -1);
        if (assessmentId != -1) {
            titleText.setText(title);
            typeText.setText(type);
            dateText.setText(date);
        }
    }

    public void onDeleteAssessment(View view) {

        if (assessmentId != -1) {
            repo.deleteAssessment(assessmentId);
            Intent intent = new Intent(this, AssessmentList.class);
            startActivity(intent);
        }

    }
}