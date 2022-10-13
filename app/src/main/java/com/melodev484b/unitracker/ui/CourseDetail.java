package com.melodev484b.unitracker.ui;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;

import java.util.List;

public class CourseDetail extends AppCompatActivity {

    TextView titleText;
    TextView startText;
    TextView endText;
    TextView statusText;
    TextView instructorText;
    TextView phoneText;
    TextView emailText;
    TextView noteText;
    RecyclerView recyclerView;
    int courseId;
    String title;
    String start;
    String end;
    String status;
    String instructor;
    String phone;
    String email;
    String note;
    Repository repo;
    final String SUCCESS_MESSAGE = "Course removed.";
    final String FAILURE_MESSAGE = "Failed to remove Course";
    String displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.course_detail_title);
        startText = findViewById(R.id.course_detail_start);
        endText = findViewById(R.id.course_detail_end);
        statusText = findViewById(R.id.course_detail_status);
        instructorText = findViewById(R.id.course_detail_instructor);
        phoneText = findViewById(R.id.course_detail_phone);
        emailText = findViewById(R.id.course_detail_email);
        noteText = findViewById(R.id.course_detail_note);
        courseId = getIntent().getIntExtra("course_id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        note = getIntent().getStringExtra("note");

        if (courseId != -1) {
            titleText.setText(title);
            startText.setText(start);
            endText.setText(end);
            statusText.setText(status);
            instructorText.setText(instructor);
            phoneText.setText(phone);
            emailText.setText(email);
            noteText.setText(note);
        }


        // Set up RecyclerView
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.course_detail_recycler);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAllAssessmentsWithCourseId(courseId);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void onDeleteCourse(View view) {
        Snackbar message = Snackbar.make(view, "", LENGTH_SHORT);
        if (courseId != -1) {
            repo.deleteCourse(courseId);
            Intent intent = new Intent(this, CourseList.class);
            startActivity(intent);
        }
        else {
            displayMessage = FAILURE_MESSAGE;
            message.show();
        }
    }

    public void onAddAssessment(View view) {
        Intent intent = new Intent(this, AssessmentEdit.class);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }
}