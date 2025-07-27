package com.stanissudo.gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.stanissudo.gymlog.database.GymLogDAO;
import com.stanissudo.gymlog.database.GymLogDatabase;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = GymLogDatabase.gymLogTable)
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exercise;
    private double weight;
    private int repetitions;
    private LocalDate logDate;

    public GymLog(String exercise, double weight, int repetitions) {
        this.exercise = exercise;
        this.weight = weight;
        this.repetitions = repetitions;
        this.logDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id && Double.compare(weight, gymLog.weight) == 0 && repetitions == gymLog.repetitions && Objects.equals(exercise, gymLog.exercise) && Objects.equals(logDate, gymLog.logDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, repetitions, logDate);
    }
}
