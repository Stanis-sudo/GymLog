package com.stanissudo.gymlog.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.stanissudo.gymlog.MainActivity;
import com.stanissudo.gymlog.database.entities.GymLog;
import com.stanissudo.gymlog.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private LiveData<List<GymLog>> allLogs;
    //private LiveData<User> currentUser;
    private static GymLogRepository repository;

    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
        //this.currentUser = this.userDAO.getUserByName();
        //this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    public static GymLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );
        try {
            return future.get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymRepository, thread error.");
        }
        return null;
    }

    public LiveData<List<GymLog>> getAllLogs() {
        return gymLogDAO.getAllRecords();
    }

    //    public ArrayList<GymLog> getAllLogs() {
//        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
//                new Callable<ArrayList<GymLog>>() {
//                    @Override
//                    public ArrayList<GymLog> call() throws Exception {
//                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
//                    }
//                }
//        );
//        try {
//            return future.get();
//
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
//        }
//        return null;
//    }

    public LiveData<List<GymLog>> getUserLogs(int userId) {
        return gymLogDAO.getRecordsById(userId);
    }
    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
        });
    }

    public void insertUser(User... user) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByName(username);
//        Future<User> future = GymLogDatabase.databaseWriteExecutor.submit(
//                new Callable<User>() {
//                    @Override
//                    public User call() throws Exception {
//                        return userDAO.getUserByName(username);
//                    }
//                }
//        );
//        try {
//            return future.get();
//
//        } catch (InterruptedException | ExecutionException e) {
//            Log.d(MainActivity.TAG, "Problem getting user by username.");
//        }
//        return null;
    }
    public LiveData<User> getUserNameById(int userId) {
        return userDAO.getUserNameById(userId);
    }
}
