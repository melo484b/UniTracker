package com.melodev484b.unitracker.ui;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;

import java.util.List;

public class TermDetail extends AppCompatActivity {
    TextView titleText, startText, endText;
    int termId;
    String title, start, end;
    Repository repo;
    List<Course> courses;
    final String FAILURE_MESSAGE = "Remove related courses first!";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewRefresh();
    }

    private void recyclerViewRefresh() {
        RecyclerView recyclerView = findViewById(R.id.course_detail_recycler);
        Repository repo = new Repository(getApplication());
        courses = repo.getmAllCoursesWithTermId(termId);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteTerm(View view) {
        if (courses.isEmpty()) {
            if (termId != -1) {
                repo.deleteTerm(termId);
            }
            Intent intent = new Intent(this, TermList.class);
            startActivity(intent);
        }
        else {
            Snackbar message = Snackbar.make(view, FAILURE_MESSAGE, LENGTH_SHORT);
            message.show();
        }
    }

    public void onAddCourse(View view) {
        Intent intent = new Intent(this, CourseEdit.class);
        intent.putExtra("term_id", termId);
        startActivity(intent);
    }
}