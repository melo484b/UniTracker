package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.util.ChronoManager;

import java.util.Calendar;

public class AssessmentEdit extends AppCompatActivity {
    EditText editTitle;
    TextView assessmentDate;
    int assessmentId, courseId;
    String title, type, date;
    Repository repo;
    private DatePickerDialog datePickerDialog;
    RadioButton performance, objective;
    private final String PERFORMANCE_TYPE = "Performance", OBJECTIVE_TYPE = "Objective", ERROR_TYPE = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.assessment_edit_title);
        assessmentDate = findViewById(R.id.assessment_edit_date);
        performance = findViewById(R.id.assessment_edit_performance_radio);
        objective = findViewById(R.id.assessment_edit_objective_radio);
        assessmentId = getIntent().getIntExtra("assessment_id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("course_id", -1);
        setType(PERFORMANCE_TYPE);
        if (assessmentId != -1) {
            editTitle.setText(title);
            setType(type);
            assessmentDate.setText(date);
        }
        initDatePicker();
    }

    private String getType() {
        if (performance.isChecked()) {
            return PERFORMANCE_TYPE;
        }
        else if (objective.isChecked()) {
            return OBJECTIVE_TYPE;
        }
        else {
            return ERROR_TYPE;
        }
    }

    private void setType(String newType) {
        switch (newType) {
            case PERFORMANCE_TYPE:
                performance.setChecked(true);
                break;
            case OBJECTIVE_TYPE:
                objective.setChecked(true);
                break;
            default:
                type = ERROR_TYPE;
                break;
        }
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = ChronoManager.date(year, month, day);
            assessmentDate.setText(date);
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
            assessment = new Assessment(newId, editTitle.getText().toString(), getType(), assessmentDate.getText().toString(), courseId);
            repo.insert(assessment);
        }
        else {
            assessment = new Assessment(assessmentId, editTitle.getText().toString(), getType(), assessmentDate.getText().toString(), courseId);
            repo.update(assessment);
        }
        finish();
    }

    public void onSelectDate(View view) {
        datePickerDialog.show();
    }

    public void onPerformanceRadioSelected(View view) {
        setType(PERFORMANCE_TYPE);
    }

    public void onObjectiveRadioSelected(View view) {
        setType(OBJECTIVE_TYPE);
    }
}