package com.melodev484b.unitracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.melodev484b.unitracker.entity.Assessment;
import com.melodev484b.unitracker.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseId ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE courseId == :courseId")
    Course getCourseById(int courseId);
}
