package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;
import com.melodev484b.unitracker.util.ChronoManager;

import java.util.Calendar;

public class CourseEdit extends AppCompatActivity {
    EditText editTitle, editStart, editEnd, editStatus, editInstructor, editPhone, editEmail, editNote;
    String title, start, end, status, instructor, phone, email, note;
    int termId, courseId;
    Repository repo;
    private DatePickerDialog datePickerDialog;
    private boolean settingStartDate = true;


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
        initDatePicker();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month++;
            String date = ChronoManager.date(year, month, day);
            if (settingStartDate) {
                editStart.setText(date);
            } else {
                editEnd.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
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

    public void onSelectStart(View view) {
        settingStartDate = true;
        datePickerDialog.show();
    }

    public void onSelectEnd(View view) {
        settingStartDate = false;
        datePickerDialog.show();
    }
}