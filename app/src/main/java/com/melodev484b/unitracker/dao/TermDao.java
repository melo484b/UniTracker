package com.melodev484b.unitracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.melodev484b.unitracker.entity.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM terms WHERE termId == :termId")
    void deleteById(int termId);

    @Query("SELECT * FROM terms ORDER BY termId ASC")
    List<Term> getAllTerms();

    @Query("SELECT * FROM terms WHERE termId == :termId")
    Term getTermById(int termId);
}
