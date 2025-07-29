/**
 * @author Stan Permiakov
 * @version 1.0
 * @since 2025-07-28
 */

package com.stanissudo.gymlog.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.stanissudo.gymlog.MainActivity;
import com.stanissudo.gymlog.database.entities.GymLog;
import com.stanissudo.gymlog.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Repository class that serves as a single source of truth for accessing
 * GymLog and User data from the Room database. Provides LiveData for UI observation
 * and performs operations asynchronously on a background thread.
 */
public class GymLogRepository {
    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private LiveData<List<GymLog>> allLogs;
    private static GymLogRepository repository;

    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
    }

    /**
     * Returns a singleton instance of GymLogRepository.
     *
     * @param application The Application context used to initialize the database.
     * @return A singleton GymLogRepository instance.
     */
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

    /**
     * Returns all GymLog entries from the database.
     *
     * @return LiveData list of all GymLogs.
     */
    public LiveData<List<GymLog>> getAllLogs() {

        return gymLogDAO.getAllRecords();
    }

    /**
     * Returns GymLog entries for a specific user.
     *
     * @param userId The ID of the user.
     * @return LiveData list of GymLogs belonging to the specified user.
     */
    public LiveData<List<GymLog>> getUserLogs(int userId) {
        return gymLogDAO.getRecordsById(userId);
    }

    /**
     * Inserts a GymLog into the database on a background thread.
     *
     * @param gymLog The GymLog to insert.
     */
    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
        });
    }

    /**
     * Inserts one or more users into the database on a background thread.
     *
     * @param user One or more User objects to insert.
     */
    public void insertUser(User... user) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username to search for.
     * @return LiveData containing the User, or null if not found.
     */
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByName(username);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId The user ID.
     * @return LiveData containing the User, or null if not found.
     */
    public LiveData<User> getUserNameById(int userId) {
        return userDAO.getUserNameById(userId);
    }
}
