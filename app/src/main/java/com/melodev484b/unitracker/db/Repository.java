package com.melodev484b.unitracker.db;

import android.app.Application;

import com.melodev484b.unitracker.dao.AssessmentDao;
import com.melodev484b.unitracker.dao.CourseDao;
import com.melodev484b.unitracker.dao.TermDao;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.entity.Course;
import com.melodev484b.unitracker.entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    // DAO
    private AssessmentDao mAssessmentDao;
    private CourseDao mCourseDao;
    private TermDao mTermDao;
    // Lists
    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        UniTrackerDatabaseBuilder db = UniTrackerDatabaseBuilder.getDatabase(application);
        mAssessmentDao = db.assessmentDao();
        mCourseDao = db.courseDao();
        mTermDao = db.termDao();
    }

    // Assessment CRUD
    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDao.update(assessment);
        });
    }

    public List<Assessment> getmAllAssessments() {
        databaseExecutor.execute(() -> {
           mAllAssessments = mAssessmentDao.getAllAssessments();
        });
        try {
            Thread.sleep(750);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    // Course CRUD
    public void insert(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDao.update(course);
        });
    }

    public List<Course> getmAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCourses();
        });
        try {
            Thread.sleep(750);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    // Term CRUD
    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDao.insert(term);
        });
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDao.update(term);
        });
    }

    public List<Term> getmAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(750);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

}
