/**
 * @author Stan Permiakov
 * @version 1.0
 * @since 2025-07-28
 */

package com.stanissudo.gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.stanissudo.gymlog.database.GymLogDatabase;

import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a log entry for a user's gym activity.
 * Each log includes an exercise, weight lifted, repetitions, and a timestamp.
 */
@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String exercise;
    private double weight;
    private int repetitions;
    private LocalDateTime logDate;

    /**
     * Constructs a GymLog entry with the given exercise data and user ID.
     * Automatically sets the log date to the current time.
     *
     * @param exercise    The name of the exercise performed.
     * @param weight      The weight lifted in the exercise.
     * @param repetitions The number of repetitions performed.
     * @param userId      The ID of the user who performed the exercise.
     */
    public GymLog(String exercise, double weight, int repetitions, int userId) {
        this.userId = userId;
        this.exercise = exercise;
        this.weight = weight;
        this.repetitions = repetitions;
        this.logDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id && userId == gymLog.userId && Double.compare(weight, gymLog.weight) == 0 && repetitions == gymLog.repetitions && Objects.equals(exercise, gymLog.exercise) && Objects.equals(logDate, gymLog.logDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, exercise, weight, repetitions, logDate);
    }

    /**
     * Returns a formatted string representation of this GymLog entry,
     * including exercise details and timestamp.
     */
    @NonNull
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = logDate.format(dateFormatter);
        String formattedTime = logDate.format(timeFormatter);
        return exercise + '\n' +
                "Weight: " + weight + '\n' +
                "Repetitions: " + repetitions + '\n' +
                formattedDate + '\n' +
                formattedTime + '\n' +
                "=-=-=-=-=-=-=-=-=\n";
    }
}
