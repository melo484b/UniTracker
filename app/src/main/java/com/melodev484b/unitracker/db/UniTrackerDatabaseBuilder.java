package com.melodev484b.unitracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.melodev484b.unitracker.dao.AssessmentDao;
import com.melodev484b.unitracker.dao.CourseDao;
import com.melodev484b.unitracker.dao.TermDao;
import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.entity.Course;
import com.melodev484b.unitracker.entity.Term;

@Database(entities = {Assessment.class, Course.class, Term.class}, version = 2, exportSchema = false)
public abstract class UniTrackerDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract TermDao termDao();

    private static volatile UniTrackerDatabaseBuilder INSTANCE;
    static UniTrackerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UniTrackerDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UniTrackerDatabaseBuilder.class, "UniTracker.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
