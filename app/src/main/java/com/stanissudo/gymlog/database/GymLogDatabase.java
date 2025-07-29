/**
 * @author Stan Permiakov
 * @version 1.0
 * @since 2025-07-28
 */

package com.stanissudo.gymlog.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.stanissudo.gymlog.MainActivity;
import com.stanissudo.gymlog.database.entities.GymLog;
import com.stanissudo.gymlog.database.entities.User;
import com.stanissudo.gymlog.database.typeConverters.LocalDataTypeConverter;

import org.jspecify.annotations.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * GymLogDatabase is the main access point to the Room database.
 * It defines the database configuration and serves as the app's single
 * source of truth for GymLog and User data persistence.
 * It includes DAOs for accessing GymLog and User tables.
 */
@TypeConverters(LocalDataTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 3, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    /** Table name for user data */
    public static final String USER_TABLE = "userTable";

    /** Table name for gym log entries */
    public static final String GYM_LOG_TABLE = "gymLogTable";
    private static final String DATABASE_NAME = "GymLogDatabase";
    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    /** Fixed thread pool executor for background DB operations */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Gets the singleton instance of the database.
     *
     * @param context Application context
     * @return The GymLogDatabase instance
     */
    static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GymLogDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback triggered when the database is first created.
     * Inserts default admin and test users.
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin", "admin");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser = new User("testuser", "testuser");
                dao.insert(testUser);
            });
        }
    };

    /**
     * Provides access to GymLogDAO.
     * @return GymLogDAO instance
     */
    public abstract GymLogDAO gymLogDAO();

    /**
     * Provides access to UserDAO.
     * @return UserDAO instance
     */
    public abstract UserDAO userDAO();
}
