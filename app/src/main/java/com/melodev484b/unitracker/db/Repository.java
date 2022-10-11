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
    // Objects
    private Assessment mAssessment;
    private Course mCourse;
    private Term mTerm;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        UniTrackerDatabaseBuilder db = UniTrackerDatabaseBuilder.getDatabase(application);
        mAssessmentDao = db.assessmentDao();
        mCourseDao = db.courseDao();
        mTermDao = db.termDao();
    }

    // Assessment
    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDao.update(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Assessment getAssessmentById(int id) {
        databaseExecutor.execute(() -> {
            mAssessment = mAssessmentDao.getAssessmentById(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssessment;
    }

    public List<Assessment> getAllAssessmentsWithCourseId(int id) {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDao.getAllAssessmentsWithCourseId(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment> getmAllAssessments() {
        databaseExecutor.execute(() -> {
           mAllAssessments = mAssessmentDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public boolean isAssessmentsEmpty() {
        getmAllAssessments();
        return mAllAssessments.isEmpty();
    }

    // Course
    public void insert(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDao.update(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Course getCourseById(int id) {
        databaseExecutor.execute(() -> {
            mCourse = mCourseDao.getCourseById(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mCourse;
    }

    public List<Course> getmAllCoursesWithTermId(int id) {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCoursesWithTermId(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Course> getmAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public boolean isCoursesEmpty() {
        getmAllCourses();
        return mAllCourses.isEmpty();
    }

    // Term
    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDao.update(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Term getTermById(int id) {
        databaseExecutor.execute(() -> {
            mTerm = mTermDao.getTermById(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTerm;
    }

    public List<Term> getmAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public boolean isTermsEmpty() {
        getmAllTerms();
        return mAllTerms.isEmpty();
    }

}
