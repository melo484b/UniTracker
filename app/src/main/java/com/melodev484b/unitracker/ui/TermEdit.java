package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Term;
import com.melodev484b.unitracker.util.ChronoManager;

import java.util.Calendar;

public class TermEdit extends AppCompatActivity {
    EditText editTitle;
    TextView termStart, termEnd;
    int termId;
    String title, startDate, endDate;
    Repository repo;
    private DatePickerDialog datePickerDialog;
    private boolean settingStartDate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.term_edit_title);
        termStart = findViewById(R.id.term_edit_start);
        termEnd = findViewById(R.id.term_edit_end);
        termId = getIntent().getIntExtra("term_id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("start");
        endDate = getIntent().getStringExtra("end");
        if (termId != -1) {
            editTitle.setText(title);
            termStart.setText(startDate);
            termEnd.setText(endDate);
        }
        initDatePicker();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = ChronoManager.date(year, month, day);
            if (settingStartDate) {
                termStart.setText(date);
            } else {
                termEnd.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    public void onSaveTerm(View view) {
        Term term;
        int newId;
        if (termId == -1) {
            if (repo.isTermsEmpty()) {
                newId = 1;
            }
            else {
                newId = repo.getmAllTerms().get(repo.getmAllTerms().size() -1).getTermId() + 1;
            }
            term = new Term(newId, editTitle.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
            repo.insert(term);
        }
        else {
            term = new Term(termId, editTitle.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
            repo.update(term);
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
}