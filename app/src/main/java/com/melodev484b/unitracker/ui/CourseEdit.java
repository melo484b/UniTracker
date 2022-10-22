package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Course;
import com.melodev484b.unitracker.util.ChronoManager;

import java.util.Calendar;

public class CourseEdit extends AppCompatActivity {
    EditText editTitle, editInstructor, editPhone, editEmail, editNote;
    TextView startDate, endDate;
    String title, start, end, status, instructor, phone, email, note;
    final String IN_PROGRESS_STATUS = "In Progress", COMPLETED_STATUS = "Completed", DROPPED_STATUS = "Dropped", PLANNED_STATUS = "Planned", ERROR_STATUS = "Error";
    int termId, courseId;
    Repository repo;
    private DatePickerDialog datePickerDialog;
    private boolean settingStartDate = true;
    RadioButton inProgress, completed, dropped, planned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.course_edit_title);
        startDate = findViewById(R.id.course_edit_start);
        endDate = findViewById(R.id.course_edit_end);
        inProgress = findViewById(R.id.course_edit_in_progress_radio);
        completed = findViewById(R.id.course_edit_completed_radio);
        dropped = findViewById(R.id.course_edit_dropped_radio);
        planned = findViewById(R.id.course_edit_planned_radio);
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
            startDate.setText(start);
            endDate.setText(end);
            setStatus(status);
            editInstructor.setText(instructor);
            editPhone.setText(phone);
            editEmail.setText(email);
            editNote.setText(note);
        }
        initDatePicker();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = ChronoManager.date(year, month, day);
            if (settingStartDate) {
                startDate.setText(date);
            } else {
                endDate.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String getStatus() {
        if (inProgress.isChecked()) {
            return IN_PROGRESS_STATUS;
        }
        else if (completed.isChecked()) {
            return COMPLETED_STATUS;
        }
        else if (dropped.isChecked()) {
            return DROPPED_STATUS;
        }
        else if (planned.isChecked()) {
            return PLANNED_STATUS;
        }
        else {
            return ERROR_STATUS;
        }
    }

    private void setStatus(String new_status) {
        switch (new_status) {
            case IN_PROGRESS_STATUS:
                inProgress.setChecked(true);
                break;
            case COMPLETED_STATUS:
                completed.setChecked(true);
                break;
            case DROPPED_STATUS:
                dropped.setChecked(true);
                break;
            case PLANNED_STATUS:
                planned.setChecked(true);
                break;
            default:
                status = ERROR_STATUS;
                break;
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
            course = new Course(newId, editTitle.getText().toString(), startDate.getText().toString(),
                    endDate.getText().toString(), getStatus(),
                    editInstructor.getText().toString(), editPhone.getText().toString(),
                    editEmail.getText().toString(), editNote.getText().toString(), termId);
            repo.insert(course);
        }
        else {
            course = new Course(courseId, editTitle.getText().toString(), startDate.getText().toString(),
                    endDate.getText().toString(), getStatus(),
                    editInstructor.getText().toString(), editPhone.getText().toString(),
                    editEmail.getText().toString(), editNote.getText().toString(), termId);
            repo.update(course);
        }
        finish();
    }

    public void onSelectStart(View view) {
        settingStartDate = true;
        datePickerDialog.show();
    }

    public void onSelectEnd(View view) {
        settingStartDate = false;
        datePickerDialog.show();
    }

    public void onInProgressRadio(View view) {
        setStatus(IN_PROGRESS_STATUS);
    }

    public void onCompletedRadio(View view) {
        setStatus(COMPLETED_STATUS);
    }

    public void onPlannedRadio(View view) {
        setStatus(PLANNED_STATUS);
    }

    public void onDroppedRadio(View view) {
        setStatus(DROPPED_STATUS);
    }
}