package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Term;

public class TermEdit extends AppCompatActivity {
    EditText editTitle;
    // Change to date pickers
    EditText editStart;
    EditText editEnd;

    int termId;
    String title;
    String startDate;
    String endDate;
    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.term_edit_title);
        editStart = findViewById(R.id.term_edit_start);
        editEnd = findViewById(R.id.term_edit_end);
        termId = getIntent().getIntExtra("term_id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("start");
        endDate = getIntent().getStringExtra("end");
        if (termId != -1) {
            editTitle.setText(title);
            editStart.setText(startDate);
            editEnd.setText(endDate);
        }
    }

    public void onTermEditFloatingButton(View view) {
        Term term;
        int newId;
        if (termId == -1) {
            if (repo.isTermsEmpty()) {
                newId = 1;
            }
            else {
                newId = repo.getmAllTerms().get(repo.getmAllTerms().size() -1).getTermId() + 1;
            }
            term = new Term(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.insert(term);
        }
        else {
            term = new Term(termId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.update(term);
        }
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }
}