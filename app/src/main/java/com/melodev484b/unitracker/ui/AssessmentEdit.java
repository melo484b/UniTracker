package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.util.ChronoManager;

import java.time.LocalDate;
import java.util.Calendar;

public class AssessmentEdit extends AppCompatActivity {
    EditText editTitle, editType, editDate;
    int assessmentId, courseId;
    String title, type, date;
    Repository repo;
    private DatePickerDialog datePickerDialog;

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
        initDatePicker();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month++;
            String date = ChronoManager.date(year, month, day);
            editDate.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
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

    public void onSelectDate(View view) {
        datePickerDialog.show();
    }
}