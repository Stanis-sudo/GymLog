package com.stanissudo.gymlog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.stanissudo.gymlog.database.entities.GymLog;
import com.stanissudo.gymlog.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);
    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();
    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE username = :userName")
    LiveData<User> getUserByName(String userName);
    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE id = :userId")
    LiveData<User> getUserNameById(int userId);

    @Query("DELETE FROM " + GymLogDatabase.USER_TABLE)
    void deleteAll();
}
