package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;

public class CourseEdit extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructor;
    EditText editPhone;
    EditText editEmail;
    EditText editNote;
    int courseId;
    String title;
    String start;
    String end;
    String status;
    String instructor;
    String phone;
    String email;
    String note;
    int termId;
    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.course_edit_title);
        editStart = findViewById(R.id.course_edit_start);
        editEnd = findViewById(R.id.course_edit_end);
        editStatus = findViewById(R.id.course_edit_status);
        editInstructor = findViewById(R.id.course_edit_instructor);
        editPhone = findViewById(R.id.course_edit_phone);
        editEmail = findViewById(R.id.course_edit_email);
        editNote = findViewById(R.id.course_edit_note);
        courseId = getIntent().getIntExtra("course_id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        note = getIntent().getStringExtra("note");
        termId = getIntent().getIntExtra("term_id", -1);

        if (courseId != -1) {
            editTitle.setText(title);
            editStart.setText(start);
            editEnd.setText(end);
            editStatus.setText(status);
            editInstructor.setText(instructor);
            editPhone.setText(phone);
            editEmail.setText(email);
            editNote.setText(note);
        }
    }

    public void onSaveCourse(View view) {
        Course course;
        int newId;
        if (courseId == -1) {
            if (repo.isCoursesEmpty()) {
                newId = 1;
            }
            else {
                newId = repo.getmAllCourses().get(repo.getmAllCourses().size() -1).getCourseId() + 1;
            }
            // Get term ID
            course = new Course(newId, editTitle.getText().toString(), editStart.getText().toString(),
                    editEnd.getText().toString(), editStatus.getText().toString(),
                    editInstructor.getText().toString(), editPhone.getText().toString(),
                    editEmail.getText().toString(), editNote.getText().toString(), termId);
            repo.insert(course);
        }
        else {
            course = new Course(courseId, editTitle.getText().toString(), editStart.getText().toString(),
                    editEnd.getText().toString(), editStatus.getText().toString(),
                    editInstructor.getText().toString(), editPhone.getText().toString(),
                    editEmail.getText().toString(), editNote.getText().toString(), termId);
            repo.update(course);
        }
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }
}