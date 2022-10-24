package com.melodev484b.unitracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.scheduler.UniTrackerReceiver;
import com.melodev484b.unitracker.util.ChronoManager;

public class AssessmentDetail extends AppCompatActivity {

    TextView titleText, typeText, startText, endText;
    int assessmentId, courseId;
    String title, type, start, end;
    final String ALERT_START_MESSAGE = "Your assessment begins today!";
    final String ALERT_END_MESSAGE = "Your assessment ends today!";
    Repository repo;
    private boolean dataChanged = false, alertStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.assessment_detail_title);
        typeText = findViewById(R.id.assessment_detail_type);
        startText = findViewById(R.id.assessment_detail_start);
        endText = findViewById(R.id.assessment_detail_end);
        assessmentId = getIntent().getIntExtra("assessment_id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseId = getIntent().getIntExtra("course_id", -1);
        if (assessmentId != -1) {
            titleText.setText(title);
            typeText.setText(type);
            startText.setText(start);
            endText.setText(end);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataChanged) {
            dataRefresh();
        }
    }

    private void dataRefresh() {
        Assessment modifiedAssessment = repo.getAssessmentById(assessmentId);
        titleText.setText(modifiedAssessment.getTitle());
        typeText.setText(modifiedAssessment.getType());
        startText.setText(modifiedAssessment.getStart());
        endText.setText(modifiedAssessment.getEnd());
        dataChanged = false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.set_assessment_start_alert:
                alertStart = true;
                setReminder(start);
                return true;
            case R.id.set_assessment_end_alert:
                alertStart = false;
                setReminder(end);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setReminder(String date) {
        Long trigger = ChronoManager.toMilliseconds(date);
        Intent intent = new Intent(AssessmentDetail.this, UniTrackerReceiver.class);
        String message = alertStart ? ALERT_START_MESSAGE : ALERT_END_MESSAGE;
        intent.putExtra("key", message);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this,
                MainActivity.getIncrementedAlertNumber(), intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

    public void onDeleteAssessment(View view) {

        if (assessmentId != -1) {
            repo.deleteAssessment(assessmentId);
            Intent intent = new Intent(this, CourseList.class);
            startActivity(intent);
        }

    }

    public void onModifyAssessment(View view) {
        dataChanged = true;
        Intent intent = new Intent(this, AssessmentEdit.class);
        intent.putExtra("assessment_id", assessmentId);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("end", end);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }
}