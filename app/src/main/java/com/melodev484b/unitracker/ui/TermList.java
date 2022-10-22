package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewRefresh();
    }

    private void recyclerViewRefresh() {
        RecyclerView recyclerView = findViewById(R.id.term_recycler);
        Repository repo = new Repository(getApplication());
        ArrayList<Term> terms = (ArrayList<Term>) repo.getmAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
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

    public void onAddTerm(View view) {
        Intent intent = new Intent(this, TermEdit.class);
        startActivity(intent);
    }
}