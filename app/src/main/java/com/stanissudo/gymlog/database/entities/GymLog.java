package com.stanissudo.gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.stanissudo.gymlog.database.GymLogDatabase;

import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String exercise;
    private double weight;
    private int repetitions;
    private LocalDateTime logDate;

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

    @NonNull
    @Override
    public String toString() {
        return exercise + '\n' +
                ", weight=" + weight + '\n' +
                ", repetitions=" + repetitions + '\n' +
                ", logDate=" + logDate.toString() + '\n' +
                "=-=-=-=-=-=-=-=-=\n";
    }
}
