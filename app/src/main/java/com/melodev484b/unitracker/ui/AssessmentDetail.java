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

    TextView titleText;
    TextView typeText;
    TextView dateText;
    TextView courseText;
    int assessmentId;
    String title;
    String type;
    String date;
    int courseId;
    Repository repo;
    final String SUCCESS_MESSAGE = "Assessment removed";
    final String FAILURE_MESSAGE = "Failed to remove Assessment";
    String displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.assessment_detail_title);
        typeText = findViewById(R.id.assessment_detail_type);
        dateText = findViewById(R.id.assessment_detail_date);
        courseText = findViewById(R.id.assessment_detail_course);
        assessmentId = getIntent().getIntExtra("assessment_id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("course_id", -1);
        if (assessmentId != -1) {
            titleText.setText(title);
            typeText.setText(type);
            dateText.setText(date);
            courseText.setText(String.valueOf(courseId));
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