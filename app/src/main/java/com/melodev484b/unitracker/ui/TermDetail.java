package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;

import java.util.List;

public class TermDetail extends AppCompatActivity {
    TextView titleText;
    TextView startText;
    TextView endText;
    int termId;
    String title;
    String start;
    String end;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.term_detail_title);
        startText = findViewById(R.id.term_detail_start);
        endText = findViewById(R.id.term_detail_end);
        termId = getIntent().getIntExtra("term_id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        if (termId != -1) {
            titleText.setText(title);
            startText.setText(start);
            endText.setText(end);
        }

        // Set up RecyclerView
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.course_detail_recycler);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getmAllCoursesWithTermId(termId);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }
}