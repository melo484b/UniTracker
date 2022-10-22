package com.melodev484b.unitracker.ui;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.melodev484b.unitracker.R;
import com.melodev484b.unitracker.db.Repository;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.entity.Course;
import com.melodev484b.unitracker.scheduler.UniTrackerReceiver;
import com.melodev484b.unitracker.util.ChronoManager;

import java.util.List;

public class CourseDetail extends AppCompatActivity {

    TextView titleText, startText, endText, statusText, instructorText, phoneText, emailText, noteText;
    RecyclerView recyclerView;
    int courseId, termId;
    String title, start, end, status, instructor, phone, email, note;
    final String ALERT_MESSAGE = "Your course begins today!";
    Repository repo;
    private boolean dataChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.course_detail_title);
        startText = findViewById(R.id.course_detail_start);
        endText = findViewById(R.id.course_detail_end);
        statusText = findViewById(R.id.course_detail_status);
        instructorText = findViewById(R.id.course_detail_instructor);
        phoneText = findViewById(R.id.course_detail_phone);
        emailText = findViewById(R.id.course_detail_email);
        noteText = findViewById(R.id.course_detail_note);
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
            titleText.setText(title);
            startText.setText(start);
            endText.setText(end);
            statusText.setText(status);
            instructorText.setText(instructor);
            phoneText.setText(phone);
            emailText.setText(email);
            noteText.setText(note);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewRefresh();
        if (dataChanged) {
            dataRefresh();
        }
    }

    private void dataRefresh() {
        Course modifiedCourse = repo.getCourseById(courseId);
        titleText.setText(modifiedCourse.getTitle());
        startText.setText(modifiedCourse.getStartDate());
        endText.setText(modifiedCourse.getEndDate());
        statusText.setText(modifiedCourse.getStatus());
        instructorText.setText(modifiedCourse.getInstructor());
        phoneText.setText(modifiedCourse.getInstructorPhone());
        emailText.setText(modifiedCourse.getInstructorEmail());
        noteText.setText(modifiedCourse.getNote());
        dataChanged = false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.set_course_reminder:
                setReminder();
                return true;
            case R.id.share_course_note:
                shareNote();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setReminder() {
        Long trigger = ChronoManager.toMilliseconds(start);
        Intent intent = new Intent(CourseDetail.this, UniTrackerReceiver.class);
        intent.putExtra("key", ALERT_MESSAGE);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this,
                MainActivity.getIncrementedAlertNumber(), intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        Snackbar message = Snackbar.make(findViewById(android.R.id.content).getRootView(), "Reminder scheduled for " + trigger, LENGTH_LONG);
        message.show();
    }

    private void shareNote() {
        Intent sendNote = new Intent();
        sendNote.setAction(Intent.ACTION_SEND);
        sendNote.putExtra(Intent.EXTRA_TITLE, title);
        sendNote.putExtra(Intent.EXTRA_TEXT, note);
        sendNote.setType("text/plain");
        Intent chooser = Intent.createChooser(sendNote, null);
        startActivity(chooser);
    }

    private void recyclerViewRefresh() {
        recyclerView = findViewById(R.id.course_detail_recycler);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAllAssessmentsWithCourseId(courseId);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void onDeleteCourse(View view) {
        if (courseId != -1) {
            repo.deleteCourse(courseId);
            Intent intent = new Intent(this, TermList.class);
            startActivity(intent);
        }
    }

    public void onAddAssessment(View view) {
        Intent intent = new Intent(this, AssessmentEdit.class);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }

    public void onModifyCourse(View view) {
        dataChanged = true;
        Intent intent = new Intent(this, CourseEdit.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra("title", title);
        intent.putExtra("start", start);
        intent.putExtra("end", end);
        intent.putExtra("status", status);
        intent.putExtra("instructor", instructor);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("note", note);
        intent.putExtra("term_id", termId);
        startActivity(intent);
    }
}