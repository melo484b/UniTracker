package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.melodev484b.unitracker.R;

public class MainActivity extends AppCompatActivity {

    private static int alertNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTermButton(View view) {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    public void onAssessmentButton(View view) {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
    }

    public void onCourseButton(View view) {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }

    public static int getIncrementedAlertNumber() {
        alertNumber++;
        return alertNumber;
    }
}